package com.project.ws.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.Review;
import com.project.ws.database.domain.Vendor;
import com.project.ws.database.repository.review.ReviewRepository;
import com.project.ws.database.repository.vendor.VendorRepository;

/**
 * This is customer spring controller which has methods
 * that specify the end points for the customer web service.
 */
@RestController
public class ReviewController {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@RequestMapping("/review/add")
	public String addReview(HttpServletRequest request) {
		String reviewDesc = request.getParameter("reviewDesc");
		String reviewType = request.getParameter("reviewType");
		int custId = Integer.parseInt(request.getParameter("custId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		Double rating = Double.parseDouble(request.getParameter("rating"));
		
		Review review = new Review();
		
		review.setReviewDesc(reviewDesc);
		review.setReviewType(reviewType);
		review.setCustId(custId);
		review.setProductId(productId);
		review.setRating(rating);
		
		
		int reviewAdded = reviewRepository.addReview(review);
		if (reviewAdded > 0) {
			return "Successfully Added Review" ;
		}
		return "Failed";
	}

}
