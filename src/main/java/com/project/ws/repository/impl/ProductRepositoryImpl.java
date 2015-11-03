package com.project.ws.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.project.ws.domain.Cart;
import com.project.ws.domain.Product;
import com.project.ws.repository.custom.ProductCustomRepository;
import com.project.ws.representation.CartRepresentation;
import com.project.ws.representation.OrderRepresentation;
import com.project.ws.representation.ProductRepresentation;

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
		String SQL = "SELECT p FROM Product p WHERE lower(product_name) LIKE '%" + productName.toLowerCase() + "%'";
		TypedQuery<Product> query = em.createQuery(SQL, Product.class);
		//query.setParameter("searchString", "%" + productName + "%");
		List<Product> resultList = query.getResultList();
		return resultList;
	}


	@Override
	public Boolean getProductAvailability(Integer productId, Integer quantity) {
		System.out.println("-------" + productId);
		String SQL = "SELECT p FROM Product p WHERE product_id = " + productId;
		TypedQuery<Product> query = em.createQuery(SQL, Product.class);
		Product productInfo = query.getSingleResult();
		System.out.println("-------" + productInfo.toString());
		if(productInfo.getQuantity() >= quantity)
			return true;
		else
			return false;
	}

	@Override
	@Transactional
	public Integer deleteProduct(Integer productId) {
		String SQL = "DELETE from Product p where product_id =" + productId;
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
//		System.out.println(SQL);
		Query query = em.createQuery(SQL);
		Integer oldQuantity = (Integer) query.getSingleResult();
		Integer newQuantity;
		if(operation.equals( "add"))
			newQuantity = oldQuantity + quantity;
		else
			newQuantity = oldQuantity - quantity;

		SQL = "UPDATE Product SET quantity = " + newQuantity + " where productId = " + productId;
		System.out.println(SQL);
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
	public Integer addProduct(Product product) {
		Integer count = 0;
		String message = "";
		String SQL = "INSERT INTO product (product_name, product_description, product_type, product_quantity, product_price,vendor_id) VALUES ('" +
				product.getName() + "', '" + product.getDescription() + "', '" + product.getType() + "', " +
				product.getQuantity() + ", " + product.getPrice() + ", " + product.getVendorId()+ ")";
		System.out.println(SQL);
		try {
			Query query = em.createNativeQuery(SQL);
			count = query.executeUpdate();
		} catch(Exception e) {
			message = e.getMessage();
		}
		
		if (count == 1)
			System.out.println("Product Added Successfully");

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
