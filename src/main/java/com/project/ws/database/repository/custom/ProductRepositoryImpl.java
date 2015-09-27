package com.project.ws.database.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.project.ws.database.domain.Product;

public class ProductRepositoryImpl implements ProductCustomRepository {

	/**
	 * This EntityManager attribute is used to create the database queries 
	 */
	@PersistenceContext 
	private EntityManager em;
	
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public List<Product> readByProductName(String productName) {
		String SQL = "SELECT p FROM Product p WHERE product_name LIKE '%" + productName + "%'";
		TypedQuery<Product> query = em.createQuery(SQL, Product.class);
		//query.setParameter("searchString", "%" + productName + "%");
		List<Product> resultList = query.getResultList();
		return resultList;
	}
	
	
	@Override
	public List<Product> getProductsWithQuantityLessThan(Integer quantity) {
		String SQL = "SELECT p FROM Product p WHERE product_quantity <= " + quantity;
		TypedQuery<Product> query = em.createQuery(SQL, Product.class);
		List<Product> resultList = query.getResultList();
		return resultList;
	}
	
	@Override
	@Transactional
	public Integer deleteProduct(String productName) {
		String SQL = "DELETE from Product p where product_name like '%" + productName + "%'";
		Integer count = em.createQuery(SQL).executeUpdate();
		if (count == 1) 
			System.out.println("product deleted successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Integer updateProductQuantity(Integer productId, Integer quantity, String operation) {
		String SQL = "select quantity from Product where productId = " + productId;
		Query query = em.createQuery(SQL);
		Integer oldQuantity = query.executeUpdate();
		Integer newQuantity;
		if(operation == "add") 
			newQuantity = oldQuantity + quantity;
		else
			newQuantity = oldQuantity - quantity;
		
		SQL = "UPDATE Product SET quantity = " + newQuantity + " where productId = " + productId;
		query = em.createQuery(SQL);
		Integer count = query.executeUpdate();
		if (count == 1) 
			System.out.println("quantity updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Integer buyProduct(Integer customerId, Integer productId, Double productPrice) {
		String SQL = "INSERT INTO cart (cust_id, product_id, price) VALUES (" + customerId + ", " + productId + ", " + productPrice + ")";
		Query query = em.createNativeQuery(SQL);
		Integer count = query.executeUpdate();
		if (count == 1) 
			System.out.println("cart inserted successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Integer addProduct(Product product) {
		String SQL = "INSERT INTO product (product_name, product_description, product_type, product_quantity, product_price) VALUES ('" + 
				product.getName() + "', '" + product.getDescription() + "', '" + product.getType() + "', " + 
				product.getQuantity() + ", " + product.getPrice() + ")";
		Query query = em.createNativeQuery(SQL);
		Integer count = query.executeUpdate();
		if (count == 1) 
			System.out.println("product inserted successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
	@Override
	@Transactional
	public Integer updateProductPrice(Integer productId, Double price) {
		String SQL = "UPDATE Product SET price = " + price + " where productId = " + productId ;
		Query query = em.createQuery(SQL);
		Integer count = query.executeUpdate();
		if (count == 1) 
			System.out.println("prduct price updated successfully");
		else
			System.out.println("ERROR!!! Check logs/database");
		return count;
	}
	
}
