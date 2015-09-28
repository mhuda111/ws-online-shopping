package com.project.ws.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	@Column(name="review_id")
	private Integer reviewId;

	@Column(name="review_desc")
	private String reviewDesc;

	@Column(name="review_type")
	private String reviewType;

	@Column(name="cust_id")
	private Integer custId;

	@Column(name="product_id")
	private Integer productId;

	@Column(name="vendor_id")
	private Integer vendorId;

	@Column(name="rating")
	private Double rating;
	
	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewDesc() {
		return reviewDesc;
	}

	public void setReviewDesc(String reviewDesc) {
		this.reviewDesc = reviewDesc;
	}

	public String getReviewType() {
		return reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
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
	
	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

}
