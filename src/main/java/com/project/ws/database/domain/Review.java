package com.project.ws.database.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is sample business object that contains customer information with address
 * which we may expose to others.
 *
 */
@Entity
@Table(name="review")
public class Review {

	@Id
	@Column(name="review_id")
	private Integer reviewID;

	@Column(name="review_desc")
	private char review_desc;

	@Column(name="cust_id")
	private Integer custId;

	@Column(name="product_id")
	private Integer productId;

	@Column(name="vendor_id")
	private Integer vendorId;

	public Review() {

	}

	public Integer getReviewID() {
		return reviewID;
	}

	public void setReviewID(Integer reviewID) {
		this.reviewID = reviewID;
	}

	public char getReview_desc() {
		return review_desc;
	}

	public void setReview_desc(char review_desc) {
		this.review_desc = review_desc;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}





}
