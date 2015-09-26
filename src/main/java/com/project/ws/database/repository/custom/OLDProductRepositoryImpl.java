package com.project.ws.database.repository.custom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.project.ws.database.domain.Product;


@Repository
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class OLDProductRepositoryImpl {
	
	@Autowired
	JdbcTemplate jdbcTemplateObject;
	
	public void addProduct(String name, String description, String type, Integer quantity, Double price) {
		String SQL = "insert into product(product_name, product_description, product_type, product_quantity, product_price) values(?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, name, description, type, quantity, price);
		System.out.println("product added");
		return;
	}
	
	public List<Product> searchProduct(String productName) {
		String SQL = "select product_name, product_description, product_type, product_quantity, product_price from product where product_name like '%" + productName + "%'";
		List<Product> list = jdbcTemplateObject.query(SQL, new RowMapper<Product>() {
			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				Product product = new Product();
				product.setName(rs.getString("product_name"));
				product.setDescription(rs.getString("product_description"));
				product.setType(rs.getString("product_type"));
				product.setQuantity(rs.getInt("product_quantity"));
				product.setPrice(rs.getDouble("product_price"));
				return product;
			}
		}
				);
		return list;
	}
	
	public void loadCart(Integer productId, Integer customerId, Integer quantity, Double price) {
		String SQL = "insert into cart (customer_id, product_id, quantity, price) values (?, ?, ?, ?)";  
		jdbcTemplateObject.update( SQL, customerId, productId, quantity, price);
		System.out.println("Created Record for cust & prod=" + customerId + ", " + productId + ", Q=" + quantity );
		return;
	}
	


}
