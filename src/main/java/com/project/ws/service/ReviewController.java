package com.project.ws.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.workflow.ReviewActivity;

/**
 * This is customer spring controller which has methods
 * that specify the end points for the customer web service.
 */
@RestController
public class ReviewController {

	@Autowired
	private ReviewActivity reviewRepository;

	/*
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
	*/

	@RequestMapping("/review/avgReview/product")
	public double getAvgRatingProduct(HttpServletRequest request) {
		int productId = Integer.parseInt(request.getParameter("productId"));
		return  reviewRepository.getAvgRatingProduct(productId);
    }


	@RequestMapping("/review/avgReview/vendor")
	public double getAvgRatingVendor(HttpServletRequest request) {
		int vendorId = Integer.parseInt(request.getParameter("vendorId"));
		return reviewRepository.getAvgRatingVendor(vendorId);
    }




}
