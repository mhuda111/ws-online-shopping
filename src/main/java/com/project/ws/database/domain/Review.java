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
	private Integer reviewId;

	@Column(name="review_desc")
	private char reviewDesc;

	@Column(name="review_type")
	private char reviewType;

	@Column(name="cust_id")
	private Integer custId;

	@Column(name="product_id")
	private Integer productId;

	@Column(name="vendor_id")
	private Integer vendorId;

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public char getReviewDesc() {
		return reviewDesc;
	}

	public void setReviewDesc(char reviewDesc) {
		this.reviewDesc = reviewDesc;
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
