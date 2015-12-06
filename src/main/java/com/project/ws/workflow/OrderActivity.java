package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Cart;
import com.project.ws.domain.Customer;
import com.project.ws.domain.CustomerAddress;
import com.project.ws.domain.Link;
import com.project.ws.domain.Order;
import com.project.ws.domain.OrderLineItem;
import com.project.ws.domain.Product;
import com.project.ws.domain.Vendor;
import com.project.ws.repository.CartRepository;
import com.project.ws.repository.CustomerAddressRepository;
import com.project.ws.repository.CustomerBillingRepository;
import com.project.ws.repository.OrderLineItemRepository;
import com.project.ws.repository.OrderRepository;
import com.project.ws.repository.ProductRepository;
import com.project.ws.repository.VendorRepository;
import com.project.ws.representation.OrderLineRepresentation;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.representation.StringRepresentation;

@Transactional
@Service
@Component
public class OrderActivity {

	Integer orderId, addrId;
	Integer count;
	Double orderAmount = 0.00;
	
	Calendar calendar = Calendar.getInstance();
	
	static Map<String, String> statusMap = new HashMap<String, String>(); 
	static {
		statusMap.put("ACT", "Order is being processed");
		statusMap.put("SHP", "Order has been shipped");
		statusMap.put("CAN", "Order has been cancelled");
		statusMap.put("DEL", "Order has been delivered");
	};
	
	@Autowired
	OrderRepresentation orderRepresentation;
	
	private final OrderRepository orderRepo;
	
	private final CartRepository cartRepo;
	
	private final CustomerBillingRepository billRepo;
	
	private final CustomerAddressRepository addrRepo;
	
	private final ProductRepository prodRepo;
	
	private final OrderLineItemRepository orderLineRepo;
	
	private final VendorRepository vendorRepo;
	
	private final String baseUrl = "http://localhost:8080";
	private final String mediaType = "application/json";
	
	
	@Autowired
	public OrderActivity(VendorRepository vendorRepo, OrderRepository orderRepo, CustomerBillingRepository billRepo, CustomerAddressRepository addrRepo, ProductRepository prodRepo, CartRepository cartRepo, OrderLineItemRepository orderLineRepo) {
		this.vendorRepo = vendorRepo;
		this.orderRepo = orderRepo;
		this.billRepo = billRepo;
		this.addrRepo = addrRepo;
		this.prodRepo = prodRepo;
		this.cartRepo = cartRepo;
		this.orderLineRepo = orderLineRepo;
	}
	
	public StringRepresentation placeOrder(Integer customerId) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		Integer billId = 0;
		try {
			List<Cart> cartList = cartRepo.findByCustomerId(customerId);
			for(Cart cart: cartList) {
				orderAmount = orderAmount + cart.getPrice() * cart.getQuantity();
				billId = cart.getCardId();
			}
			//Integer billId = billRepo.getBillId(customerId, "VISA");
			billRepo.updateAmount(customerId, billId, orderAmount, "Debit");
	
			List<CustomerAddress> addrList = addrRepo.getAddress(customerId);
			
			Integer addrId = addrList.get(0).getCustAddrId();
			
			Order order = new Order();
			order.setBillId(billId);
			order.setCustomerId(customerId);
			order.setOrderAmount(orderAmount);
			order.setOrderStatus("ACT");
			order.setAddrId(addrId);
			Integer count = orderRepo.addOrder(order);
			System.out.println("Order inserted : " + count);
			
			Order newOrder = orderRepo.findOne(orderRepo.findLatestOrder(customerId));
			orderLineRepo.addOrderLine(customerId, newOrder.getOrderId());
			cartRepo.deleteCart(customerId);
			stringRepresentation.setMessage("Order Placed Successfully");
			setLinks(stringRepresentation, customerId, newOrder.getOrderId());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return stringRepresentation;
	}
	

	public OrderRepresentation findOneOrder(Integer orderId) {
		Order order = new Order();
		order = orderRepo.findOne(orderId);
		return mapRepresentation(order, false);
	}
	
	public StringRepresentation cancelOrder(Integer orderId) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		VendorActivity vendorActivity = new VendorActivity(vendorRepo);
		Integer vendorId;
		List<OrderLineItem> orderList = orderLineRepo.findByOrderId(orderId);
		for(OrderLineItem item:orderList) {
			prodRepo.updateProductQuantity(item.getProductId(), item.getOrderLineQuantity(), "add");
			vendorId = prodRepo.findOne(item.getProductId()).getVendorId();
			vendorActivity.settleAccount(vendorId, item.getOrderLinePrice(), "debit");
		}
		orderRepo.updateOrderStatus(orderId, "CAN");
		Order order = orderRepo.findOrder(orderId);
		billRepo.updateAmount(order.getCustomerId(), order.getBillId(), order.getOrderAmount(), "credit");
		stringRepresentation.setMessage("Order has been cancelled");
		setLinks(stringRepresentation, order.getCustomerId(), order.getOrderId());
		return stringRepresentation;
	}
	
	public OrderRepresentation shipOrder(Integer orderId) {
		Order order = new Order();
		try {
			Integer vendorId;
			VendorActivity vendorActivity = new VendorActivity(vendorRepo);
			List<OrderLineItem> orderList = orderLineRepo.findByOrderId(orderId);
			for(OrderLineItem item:orderList) {
				vendorId = prodRepo.findByProductId(item.getProductId()).getVendorId();
				vendorActivity.settleAccount(vendorId, item.getOrderLinePrice(), "credit");
			}
			orderRepo.updateOrderStatus(orderId, "SHP");
			order = orderRepo.findOne(orderId);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return mapRepresentation(order, true);
	}
	
	public List<OrderRepresentation> allOrders(Integer customerId, String subset) {
		List<Order> orderList = new ArrayList<Order>();
		if(subset.equals("all"))
			orderList = orderRepo.findAllOrders(customerId);
		else if(subset.equals("active"))
			orderList = orderRepo.findAllActiveOrders(customerId);
		List<OrderRepresentation> resultList = new ArrayList<OrderRepresentation>();
		for(Order o:orderList) {
			resultList.add(mapRepresentation(o, false));
		}
		return resultList;
	}
	
	public OrderRepresentation checkOrderStatus(Integer orderId) {
		Order order = new Order();
		order = orderRepo.findOne(orderId);
		return mapRepresentation(order, false);
	}
	
	public Boolean validateOrder(Integer orderId) {
		Order o = orderRepo.findOne(orderId);
		if(o == null)
			return false;
		else 
			return true;
	}
	
	public List<OrderRepresentation> findAllOrdersForVendor(Integer vendorId, String orderStatus) {
		List<Order> orderList = new ArrayList<Order>();
		orderList = orderRepo.findAllOrdersForVendor(vendorId, orderStatus);
		List<OrderRepresentation> resultList = new ArrayList<OrderRepresentation>();
		for(Order o:orderList) {
			resultList.add(mapRepresentation(o, true));
		}
		return resultList;
	}
	
	public OrderRepresentation mapRepresentation(Order order, boolean showLinksForVendor) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		orderRepresentation.setOrderId(order.getOrderId());
		orderRepresentation.setOrderAmount(order.getOrderAmount());
		orderRepresentation.setOrderDate(order.getOrderDate());
		orderRepresentation.setOrderStatus(statusMap.get(order.getOrderStatus()));
		orderRepresentation.setOrderShipMethod(order.getOrderShipMethod());
		calendar.setTime(order.getOrderDate());
		calendar.add(Calendar.DATE, 5);
		orderRepresentation.setOrderDeliveryDate(calendar.getTime());
		List<OrderLineItem> subResultList = orderLineRepo.findByOrderId(order.getOrderId());
		List<OrderLineRepresentation> orderLineReprList = new ArrayList<OrderLineRepresentation>();
		for(OrderLineItem ol: subResultList) {
			orderLineReprList.add(mapOrderLineRepresentation(ol));
		}
		orderRepresentation.setLineItems(orderLineReprList);
		if (showLinksForVendor) {
			setVendorLinks(orderRepresentation);
		} else {
			setCustomerLinks(orderRepresentation);
		}
		return orderRepresentation;
	}
	
	public OrderLineRepresentation mapOrderLineRepresentation(OrderLineItem ol) {
		OrderLineRepresentation orderLineRepresentation = new OrderLineRepresentation();
		orderLineRepresentation.setOrderId(ol.getOrderId());
		orderLineRepresentation.setOrderLinePrice(ol.getOrderLinePrice());
		orderLineRepresentation.setOrderLineQuantity(ol.getOrderLineQuantity());
		orderLineRepresentation.setProductId(ol.getProductId());
		Product product = prodRepo.findByProductId(ol.getProductId());
		orderLineRepresentation.setProductName(product.getName());
		return orderLineRepresentation;
	}
	

	private void setLinks(StringRepresentation stringRepresentation, Integer customerId, Integer orderId) {
		Link viewOrders = new Link("get", baseUrl + "/order/activeOrders?customerId=" + customerId, "viewOrders", mediaType);
		Link orderStatus = new Link("get", baseUrl + "/order/checkOrderStatus?orderId=" + orderRepresentation.getOrderId(), "checkStatus", mediaType);
		Link cancelOrder = new Link("put", baseUrl + "/order/cancelOrder?orderId=" + orderId, "cancelOrder", mediaType);
		Link viewOrderDetails = new Link("get", baseUrl + "/order/view?orderId=" + orderRepresentation.getOrderId(), "viewOrderDetails", mediaType);
		stringRepresentation.setLinks(viewOrders, cancelOrder, orderStatus, viewOrderDetails);
	}

	private void setCustomerLinks(OrderRepresentation orderRepresentation) {
		Link cancelOrder = new Link("put", baseUrl + "/order/cancelOrder?orderId=" + orderRepresentation.getOrderId(), "cancelOrder", mediaType);
		Link orderStatus = new Link("get", baseUrl + "/order/checkOrderStatus?orderId=" + orderRepresentation.getOrderId(), "checkStatus", mediaType);
		Link viewOrderDetails = new Link("get", baseUrl + "/order/view?orderId=" + orderRepresentation.getOrderId(), "viewOrderDetails", mediaType);
		orderRepresentation.setLinks(orderStatus, viewOrderDetails, cancelOrder);
	}
	
	private void setVendorLinks(OrderRepresentation orderRepresentation) {
		Link shipOrder = new Link("put", baseUrl + "/order/ship?orderId=" + orderRepresentation.getOrderId(), "shipOrder", mediaType);
		Link viewOrderDetails = new Link("get", baseUrl + "/order/view?orderId=" + orderRepresentation.getOrderId(), "viewOrderDetails", mediaType);
		orderRepresentation.setLinks(shipOrder, viewOrderDetails);
	}
	
}
