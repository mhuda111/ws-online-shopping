package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.ws.domain.Cart;
import com.project.ws.domain.CustomerAddress;
import com.project.ws.domain.Order;
import com.project.ws.domain.OrderLineItem;
import com.project.ws.domain.Vendor;
import com.project.ws.repository.CartRepository;
import com.project.ws.repository.CustomerAddressRepository;
import com.project.ws.repository.CustomerBillingRepository;
import com.project.ws.repository.OrderLineItemRepository;
import com.project.ws.repository.OrderRepository;
import com.project.ws.repository.ProductRepository;
import com.project.ws.repository.VendorRepository;
import com.project.ws.representation.OrderRepresentation;

public class OrderActivity {

	Integer orderId, addrId;
	Integer count;
	Double orderAmount = 0.00;
	
	@Autowired
	OrderRepresentation orderRepresentation;
	
	private final OrderRepository orderRepo;
	
	private final CartRepository cartRepo;
	
	private final CustomerBillingRepository billRepo;
	
	private final CustomerAddressRepository addrRepo;
	
	private final ProductRepository prodRepo;
	
	private final OrderLineItemRepository orderLineRepo;
	
	private final VendorRepository vendorRepo;
	
	
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
	
	public OrderRepresentation placeOrder(Integer customerId) {
		
		List<Cart> cartList = cartRepo.findByCustomerId(customerId);
		
		for(Cart cart: cartList) {
			orderAmount = orderAmount + cart.getPrice() * cart.getQuantity();
		}
		Integer billId = billRepo.getBillId(customerId, "VISA");

		billRepo.updateAmount(customerId, billId, orderAmount, "Debit");

		List<CustomerAddress> addrList = addrRepo.getAddress(customerId);
		
		Integer addrId = addrList.get(0).getCustAddrId();
		System.out.println("for customer" + customerId + " address is " + addrId);
		
		Order order = new Order();
		order.setBillId(billId);
		order.setCustomerId(customerId);
		order.setOrderAmount(orderAmount);
		order.setOrderStatus("ACT");
		order.setAddrId(addrId);
		
		Order newOrder = orderRepo.addOrder(order);		
		orderLineRepo.addOrderLine(customerId, newOrder.getOrderId());

		return mapRepresentation(order);
	}

	public OrderRepresentation cancelOrder(Integer orderId) {
		List<OrderLineItem> orderList = orderLineRepo.findByOrderId(orderId);
		for(OrderLineItem item:orderList) {
			prodRepo.updateProductQuantity(item.getProductId(), item.getOrderLineQuantity(), "add");
		}
		orderRepo.updateOrderStatus(orderId, "CAN");
		Order order = orderRepo.findOrder(orderId);
		billRepo.updateAmount(order.getCustomerId(), order.getBillId(), order.getOrderAmount(), "credit");
		return mapRepresentation(order);
	}
	
	public OrderRepresentation shipOrder(Integer orderId) {
		Integer vendorId;
		VendorActivity vendorActivity = new VendorActivity(vendorRepo);
		List<OrderLineItem> orderList = orderLineRepo.findByOrderId(orderId);
		for(OrderLineItem item:orderList) {
			vendorId = prodRepo.findByProductId(item.getProductId()).getVendor();
			vendorActivity.settleAccount(vendorId, item.getOrderLinePrice(), "credit");
		}
		
		orderRepo.updateOrderStatus(orderId, "SHP");
		Order order = orderRepo.findOne(orderId);
		return mapRepresentation(order);
	}
	
	public OrderRepresentation mapRepresentation(Order order) {
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		orderRepresentation.setOrderId(order.getOrderId());
		orderRepresentation.setOrderAmount(order.getOrderAmount());
		orderRepresentation.setOrderDate(order.getOrderDate());
		orderRepresentation.setOrderStatus(order.getOrderStatus());
		orderRepresentation.setOrderShipMethod(order.getOrderShipMethod());
		
		List<OrderLineItem> subResultList = orderLineRepo.findByOrderId(order.getOrderId());
		for(OrderLineItem ol: subResultList) {
			orderRepresentation.setProducts(ol);
		}
		return orderRepresentation;
	}
	
	
}
