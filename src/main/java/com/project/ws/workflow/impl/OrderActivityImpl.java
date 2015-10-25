package com.project.ws.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import com.project.ws.domain.Cart;
import com.project.ws.domain.CustomerAddress;
import com.project.ws.domain.CustomerBillingDetails;
import com.project.ws.domain.Order;
import com.project.ws.domain.OrderLineItem;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.workflow.CustomerAddressActivity;
import com.project.ws.workflow.CustomerBillingActivity;
import com.project.ws.workflow.OrderActivity;
import com.project.ws.workflow.ProductActivity;
import com.project.ws.workflow.custom.OrderCustomActivity;

@Repository
@EnableAutoConfiguration
public class OrderActivityImpl implements OrderCustomActivity {

	private Integer count;
	private Double orderAmount = 0.00;
	private Integer addrId;
	private Integer orderId;
	
	@Autowired
	private CustomerBillingActivity custBillActivity;

	@Autowired
	private CustomerAddressActivity custAddrActivity;

	@Autowired
	private ProductActivity prodActivity;

	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public OrderRepresentation placeOrder(Integer customerId) {

		List<Cart> list = readCart(customerId);
		for(Cart cart: list) {
			orderAmount = orderAmount + cart.getPrice() * cart.getQuantity();
		}

		Integer billId = custBillActivity.getBillId(customerId);

		custBillActivity.chargeCard(customerId, billId, orderAmount);

		List<CustomerAddress> addrList = custAddrActivity.getAddress(customerId);
		
		addrId = addrList.get(0).getCustAddrId();
		System.out.println("for customer" + customerId + " address is " + addrId);

		String SQL = "INSERT INTO order_details (cust_id, cust_bill_id, cust_addr_id, total_order_amt) VALUES(" +
				customerId + ", " + billId + ", " + addrId + ", " + orderAmount + ")";
		try {
			count = em.createNativeQuery(SQL).executeUpdate();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.updateOrderLineItem(customerId);
		orderId = findActiveOrder(customerId);
		Order newOrder = this.findOrder(orderId);
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		orderRepresentation.setOrderId(newOrder.getOrderId());
		orderRepresentation.setOrderAmount(newOrder.getOrderAmount());
		orderRepresentation.setOrderDate(newOrder.getOrderDate());
		orderRepresentation.setOrderStatus(newOrder.getOrderStatus());
		orderRepresentation.setOrderShipMethod(newOrder.getOrderShipMethod());
		
		SQL = "select ol from OrderLineItem ol where order_id = " + newOrder.getOrderId();
		TypedQuery<OrderLineItem> subQuery = em.createQuery(SQL, OrderLineItem.class);
		List<OrderLineItem> subResultList = subQuery.getResultList();
		for(OrderLineItem ol: subResultList) {
			orderRepresentation.setProducts(ol);
		}
		return orderRepresentation;
	}
	
	public boolean updateOrderLineItem(Integer customerId) {
		orderId = findActiveOrder(customerId);
		List<Cart> list = readCart(customerId);
		String SQL = "";
		
		for(Cart cart:list) {
			prodActivity.updateProductQuantity(cart.getProductId(), cart.getQuantity(), "subtract");
			System.out.println("adding in order_line_item");
			SQL = "insert into order_line_item (order_id, product_id, order_line_quantity, order_line_price) " +
					"values(" + orderId + ", " + cart.getProductId() + ", " + cart.getQuantity() + ", " +
					cart.getPrice() + ")";
			System.out.println(SQL);
			em.createNativeQuery(SQL).executeUpdate();
		}
		
		SQL = "delete from cart where cust_id = " + customerId;
		em.createNativeQuery(SQL).executeUpdate();
		return true;
	}

	@Override
	public List<OrderRepresentation> findAllOrders(Integer customerId) {
		String SQL = "select o from Order o where cust_id = " + customerId;
		TypedQuery<Order> query = em.createQuery(SQL, Order.class);
		List<Order> resultList = query.getResultList();
		
		List<OrderRepresentation> orderList = new ArrayList<OrderRepresentation>();
		for(Order o: resultList) {

			OrderRepresentation orderRepresentation = new OrderRepresentation();
			orderRepresentation.setOrderId(o.getOrderId());
			orderRepresentation.setOrderDate(o.getOrderDate());
			orderRepresentation.setOrderDeliveryDate(o.getOrderDeliveryDate());
			orderRepresentation.setOrderStatus(o.getOrderStatus());
			orderRepresentation.setOrderAmount(o.getOrderAmount());
			orderRepresentation.setOrderShipMethod(o.getOrderShipMethod());
			
			SQL = "select ol from OrderLineItem ol where order_id = " + o.getOrderId();
			TypedQuery<OrderLineItem> subQuery = em.createQuery(SQL, OrderLineItem.class);
			List<OrderLineItem> subResultList = subQuery.getResultList();
			for(OrderLineItem ol: subResultList) {
				orderRepresentation.setProducts(ol);
			}
			
			orderList.add(orderRepresentation);
		}
		return orderList;
	}


	@Override
	public List<Cart> readCart(Integer customerId) {
		String SQL = "select c from Cart c where customerId = " + customerId;
		TypedQuery<Cart> query = em.createQuery(SQL, Cart.class);
		List<Cart> resultList = query.getResultList();
		return resultList;
	}

	@Override
	@Transactional
	public OrderRepresentation cancelOrder(Integer orderId) {
		Order newOrder = this.findOrder(orderId);
		if(!newOrder.getOrderStatus().equals("ACT")) return null;
		
		String SQL = "select l from OrderLineItem l where order_id = " + orderId;
		List<OrderLineItem> orderList= em.createQuery(SQL, OrderLineItem.class).getResultList();

		for(OrderLineItem item:orderList) {
			prodActivity.updateProductQuantity(item.getProductId(), item.getOrderLineQuantity(), "add");
		}

		try {
			SQL = "select o from Order o where orderId = " + orderId;
			Order order = em.createQuery(SQL, Order.class).getSingleResult();
	
			SQL = "update order_details set status_cd = 'CAN' where order_id = " + orderId;
			count = em.createNativeQuery(SQL).executeUpdate();
	
			SQL = "select c.amountCharged from CustomerBillingDetails c where custBillId = " + order.getBillId();
			Double amount = em.createQuery(SQL, Double.class).getSingleResult();
	
			Double newAmount = amount - order.getOrderAmount();
	
			SQL="update customer_billing_details set amount_charged = " + newAmount +
					" where cust_id = " + order.getCustomerId() + " and cust_bill_id = " + order.getBillId() ;
			count = em.createNativeQuery(SQL).executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		OrderRepresentation orderRepresentation = new OrderRepresentation();
		orderRepresentation.setOrderId(newOrder.getOrderId());
		orderRepresentation.setOrderAmount(newOrder.getOrderAmount());
		orderRepresentation.setOrderDate(newOrder.getOrderDate());
		orderRepresentation.setOrderStatus(newOrder.getOrderStatus());
		orderRepresentation.setOrderShipMethod(newOrder.getOrderShipMethod());
		
		SQL = "select ol from OrderLineItem ol where order_id = " + newOrder.getOrderId();
		TypedQuery<OrderLineItem> subQuery = em.createQuery(SQL, OrderLineItem.class);
		List<OrderLineItem> subResultList = subQuery.getResultList();
		for(OrderLineItem ol: subResultList) {
			orderRepresentation.setProducts(ol);
		}
		return orderRepresentation;
	}

	@Override
	public Integer findActiveOrder(Integer customerId) {
		//select the order just created
		String SQL = "select max(o.orderId) from Order o where status_cd = 'ACT' and cust_id = " + customerId;
		Integer orderId = em.createQuery(SQL, Integer.class).getSingleResult();
		return orderId;
	}

	@Override
	public List<OrderRepresentation> findAllActiveOrders(Integer customerId) {
		String SQL = "select o from Order o where status_cd = 'ACT' and cust_id = " + customerId;
		TypedQuery<Order> query = em.createQuery(SQL, Order.class);
		List<Order> resultList = query.getResultList();
		
		List<OrderRepresentation> orderList = new ArrayList<OrderRepresentation>();
		for(Order o: resultList) {
			OrderRepresentation orderRepresentation = new OrderRepresentation();
			orderRepresentation.setOrderId(o.getOrderId());
			orderRepresentation.setOrderDate(o.getOrderDate());
			orderRepresentation.setOrderDeliveryDate(o.getOrderDeliveryDate());
			orderRepresentation.setOrderStatus(o.getOrderStatus());
			orderRepresentation.setOrderAmount(o.getOrderAmount());
			orderRepresentation.setOrderShipMethod(o.getOrderShipMethod());
			
			SQL = "select ol from OrderLineItem ol where order_id = " + o.getOrderId();
			TypedQuery<OrderLineItem> subQuery = em.createQuery(SQL, OrderLineItem.class);
			List<OrderLineItem> subResultList = subQuery.getResultList();
			for(OrderLineItem ol: subResultList) {
				orderRepresentation.setProducts(ol);
			}
			orderList.add(orderRepresentation);
		}
		return orderList;
	}

	@Override
	public Order findOrder(Integer orderId) {
		String SQL = "select o from Order o where orderId = " + orderId;
		Order orderInfo = em.createQuery(SQL, Order.class).getSingleResult();
		return orderInfo;
	}


	@Override
	@Transactional
	public Integer shipOrder(Integer customerId, Integer orderId) {
		String SQL = "update order_details set status_cd = 'SHP' where order_id = " + orderId;
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1)
			System.out.println("order shipped successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}

	@Override
	@Transactional
	public Integer updateOrderStatus(Integer orderId, String status) {
		String SQL = "update order_details set status_cd = '" + status + "' where order_id = " + orderId;
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		if (count == 1)
			System.out.println("order status updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
}

