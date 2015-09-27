package com.project.ws.database.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import com.project.ws.database.domain.Cart;
import com.project.ws.database.domain.CustomerAddress;
import com.project.ws.database.domain.Order;

@Repository
@EnableAutoConfiguration
public class OrderRepositoryImpl implements OrderCustomRepository {

	private Integer count;
	private Double orderAmount = 0.00;
	private Integer addrId;
	private Integer orderId;

	@Autowired
	private CustomerBillingRepository custRepo;

	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}





	@Override
	@Transactional
	public Integer placeOrder(Integer customerId, Integer billId) {

		List<Cart> list = readCart(customerId);
		for(Cart cart: list) {
			orderAmount = orderAmount + cart.getPrice() * cart.getQuantity();
		}

		custRepo.chargeCard(customerId, billId, orderAmount);

		List<CustomerAddress> addrList = getAddress(customerId);
		System.out.println("for customer" + customerId + " address is ");

		addrId = addrList.get(0).getCustAddrId();

		//insert data in order_details
		String SQL = "INSERT INTO order_details (cust_id, cust_bill_id, cust_addr_id, total_order_amt) VALUES(" +
					customerId + ", " + billId + ", " + addrId + ", " + orderAmount + ")";

		count = em.createNativeQuery(SQL).executeUpdate();

		System.out.println("inserted in to order: " + count + " record");

		orderId = findActiveOrder(customerId);

		//make entries for each product in the order placed in table order_line_item
		for(Cart cart:list) {
			SQL = "insert into order_line_item (order_id, product_id, order_line_quantity, order_line_price) " +
					"values(" + orderId + ", " + cart.getProductId() + ", " + cart.getQuantity() + ", " +
					cart.getPrice() + ")";
			count = em.createNativeQuery(SQL).executeUpdate();
		}

		//delete values from cart table
		SQL = "delete from cart where cust_id = " + customerId;
		count = em.createNativeQuery(SQL).executeUpdate();
		return count;
	}

	public List<Order> getOrderByOrderID(Integer orderId) {
		String SQL = "select o from Order o where order_id = " + orderId;
		TypedQuery<Order> query = em.createQuery(SQL, Order.class);
		List<Order> resultList = query.getResultList();
		return resultList;
	}


	@Override
	public List<Cart> readCart(Integer customerId) {
		String SQL = "select c from Cart c where customerId = " + customerId;
		TypedQuery<Cart> query = em.createQuery(SQL, Cart.class);
		List<Cart> resultList = query.getResultList();
		return resultList;
	}

	public List<CustomerAddress> getAddress(Integer customerId) {
		String SQL = "select c from CustomerAddress c where customerId = " + customerId;
		TypedQuery<CustomerAddress> query = em.createQuery(SQL, CustomerAddress.class);
		List<CustomerAddress> resultList = query.getResultList();
		return resultList;
	}

	@Override
	@Transactional
	public Integer cancelOrder(Integer orderId) {
		String SQL = "delete from order_Line_item where order_id = " + orderId;
		count = em.createNativeQuery(SQL).executeUpdate();

		SQL = "select o from Order o where orderId = " + orderId;
		Order order = em.createQuery(SQL, Order.class).getSingleResult();

		SQL = "delete from order_details where order_id = " + orderId;
		count = em.createNativeQuery(SQL).executeUpdate();

		SQL = "select c.amountCharged from CustomerBillingDetails c where custBillId = " + order.getBillId();
		Double amount = em.createQuery(SQL, Double.class).getSingleResult();

		Double newAmount = amount - order.getOrderAmount();

		SQL="update customer_billing_details set amount_charged = " + newAmount +
				" where cust_id = " + order.getCustomerId() + " and cust_bill_id = " + order.getBillId() ;
		count = em.createNativeQuery(SQL).executeUpdate();
		return count;
	}


	@Override
	public Integer findActiveOrder(Integer customerId) {
		//select the order just created
		String SQL = "select max(o.orderId) from Order o where status_cd = 'ACT' and cust_id = " + customerId;
		Integer orderId = em.createQuery(SQL, Integer.class).getSingleResult();
		return orderId;
	}

	@Override
	public List<Order> getOrderStatus(Integer customerId, Integer orderId) {
		String SQL = "select o from Order o where orderId = " + orderId + " and orderCustomerId = " + customerId;
		List<Order> orderList = em.createQuery(SQL, Order.class).getResultList();
		return orderList;
	}

	@Override
	@Transactional
	public Integer shipOrder(Integer customerId, Integer orderId) {
		String SQL = "update order_details set status_cd = 'SHP' where order_id = " + orderId;
		Integer count = em.createNativeQuery(SQL).executeUpdate();
		return count;
	}
}

