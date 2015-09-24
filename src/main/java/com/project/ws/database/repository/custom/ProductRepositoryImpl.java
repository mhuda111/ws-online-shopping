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
		Integer deleteCount = em.createQuery(SQL).executeUpdate();
		return deleteCount;
	}
	
	@Override
	@Transactional
	public Integer updateProductName(String oldProductName, String newProductName) {
		String SQL = "UPDATE Product SET product_name = '" + newProductName + "' where product_name = '" + oldProductName + "'" ;
		Query query = em.createQuery(SQL);
		Integer updateCount = query.executeUpdate();
		return updateCount;
	}
	
	@Override
	@Transactional
	public Integer buyProduct(Integer customerId, Integer productId, Double productPrice) {
		String SQL = "INSERT INTO cart (cust_id, product_id, price) VALUES (" + customerId + ", " + productId + ", " + productPrice + ")";
		Query query = em.createNativeQuery(SQL);
		Integer insertCount = query.executeUpdate();
		return insertCount;
	}
}
