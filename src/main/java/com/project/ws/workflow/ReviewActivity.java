package com.project.ws.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.ws.domain.Link;
import com.project.ws.domain.Review;
import com.project.ws.repository.CustomerRepository;
import com.project.ws.repository.ProductRepository;
import com.project.ws.repository.ReviewRepository;
import com.project.ws.repository.VendorRepository;
import com.project.ws.representation.ReviewRepresentation;
import com.project.ws.representation.ReviewRequest;
import com.project.ws.representation.StringRepresentation;

@Component
@Transactional
@Service
public class ReviewActivity {

	private final ReviewRepository reviewRepo;
	private final ProductRepository prodRepo;
	private final VendorRepository vendorRepo;
	private final String baseUrl = "http://localhost:8080";
	
	@Autowired
	Review review;
	
	@Autowired
	ReviewRepresentation reviewRepresentation;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	public ReviewActivity(ReviewRepository reviewRepo, VendorRepository vendorRepo, ProductRepository prodRepo) {
		this.reviewRepo = reviewRepo;
		this.vendorRepo = vendorRepo;
		this.prodRepo = prodRepo;
	}
	
	public Integer addReview(ReviewRequest reviewRequest) {
		Review review = mapRequest(reviewRequest);
		return reviewRepo.addReview(review);
	}
	
	public Double getAvgRatingProduct(Integer productId) {
		return reviewRepo.getAvgRatingProduct(productId);
	}
	
	public Double getAvgRatingVendor(Integer vendorId) {
		return reviewRepo.getAvgRatingVendor(vendorId);
	}
	
	public StringRepresentation deleteReview(Integer reviewId) {
		int noOfDeletedRow = 0;
		StringRepresentation stringRepresentation = new StringRepresentation();
		try {
			noOfDeletedRow = reviewRepo.deleteReview(reviewId);

		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		if (noOfDeletedRow > 0) {
			stringRepresentation.setMessage("Deleted Successfully");
		} else 
			stringRepresentation.setMessage("No rows found to delete");
		return stringRepresentation;
	}
	
	public List<ReviewRepresentation> getProductReviews(Integer productId) {
		List<Review> reviewList = new ArrayList<Review>();
		List<ReviewRepresentation> resultList = new ArrayList<ReviewRepresentation>();
		reviewList = reviewRepo.findByProductId(productId);
		for(Review r:reviewList) {
			resultList.add(mapRepresentation(r));
		}
		return resultList;
	}
	
	public List<ReviewRepresentation> getVendorReviews(Integer vendorId) {
		List<Review> reviewList = new ArrayList<Review>();
		List<ReviewRepresentation> resultList = new ArrayList<ReviewRepresentation>();
		reviewList = reviewRepo.findByVendorId(vendorId);
		for(Review r:reviewList) {
			resultList.add(mapRepresentation(r));
		}
		return resultList;
	}
	
	public ReviewRepresentation mapRepresentation(Review review) {
		reviewRepresentation = new ReviewRepresentation();
		if(review.getProductId() != null)
			reviewRepresentation.setProductName(prodRepo.findOne(review.getProductId()).getName());
		if(review.getVendorId() != null)
			reviewRepresentation.setVendorName(vendorRepo.findOne(review.getVendorId()).getVendorName());
		if(review.getCustId() != null)
			reviewRepresentation.setCustomerName(customerRepository.findOne(review.getCustId()).getCustFirstname());
		reviewRepresentation.setRating(review.getRating());
		reviewRepresentation.setReviewDesc(review.getReviewDesc());
		setLinks(reviewRepresentation);
		return reviewRepresentation;
	}
	
	public Review mapRequest(ReviewRequest reviewRequest) {
		Review review = new Review();
		review.setCustId(reviewRequest.getCustomerId());
		review.setProductId(reviewRequest.getProductId());
		review.setVendorId(reviewRequest.getVendorId());
		if(reviewRequest.getProductId() != null)
			review.setReviewType("product");
		else if(reviewRequest.getVendorId() != null)
			review.setReviewType("vendor");
		review.setReviewDesc(reviewRequest.getReviewDescription());
		review.setRating(reviewRequest.getRating());
		return review;
	}
	
	private void setLinks(ReviewRepresentation reviewRepresentation) {
		Link product = new Link("get", baseUrl + "/product?name=", "search_product");
		reviewRepresentation.setLinks(product);
	}
	
}
