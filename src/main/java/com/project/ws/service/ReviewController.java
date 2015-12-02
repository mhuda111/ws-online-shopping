package com.project.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.domain.Review;
import com.project.ws.representation.ReviewRepresentation;
import com.project.ws.representation.ReviewRequest;
import com.project.ws.representation.StringRepresentation;
import com.project.ws.workflow.ReviewActivity;

@RestController
public class ReviewController {

	@Autowired
	private ReviewActivity reviewActivity;

	/*
	 * POST to add new Review
	 */
	@RequestMapping(value = "/review/add", method=RequestMethod.POST)
	public List<ReviewRepresentation> addReview(@RequestBody ReviewRequest request) {
		System.out.println("in review controller");
		List<ReviewRepresentation> reviewRepresentations = new ArrayList<ReviewRepresentation>();
		int reviewAdded = 0;		
		reviewAdded = reviewActivity.addReview(request);
		reviewRepresentations = reviewActivity.getProductReviews(request.getProductId());
		System.out.println(reviewRepresentations.get(0));
		return reviewRepresentations;
	}
	
	/*
	 * GET to retrieve reviews by productId
	 */
	@RequestMapping(value="/review/view", method=RequestMethod.GET, params="productId")
	public List<ReviewRepresentation> getProductReviews(HttpServletRequest request) {
		int productId = Integer.parseInt(request.getParameter("productId"));
		List<ReviewRepresentation> reviewRepresentations = reviewActivity.getProductReviews(productId);
		return reviewRepresentations;
    }
	
	/*
	 * GET to retrieve average rating by productId
	 */
	@RequestMapping(value="/review/avgReview/product", method=RequestMethod.GET, params="productId")
	public double getAvgRatingProduct(HttpServletRequest request) {
		double avgRating = 0.00;
		try {
			int productId = Integer.parseInt(request.getParameter("productId"));
			avgRating = reviewActivity.getAvgRatingProduct(productId);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return avgRating;
    }

	/*
	 * GET to retrieve average rating by vendorId
	 */
	@RequestMapping(value="/review/avgReview/vendor", method=RequestMethod.GET, params="vendorId")
	public double getAvgRatingVendor(HttpServletRequest request) {
		double avgRating = 0.00;
		try {
			int vendorId = Integer.parseInt(request.getParameter("vendorId"));
			avgRating = reviewActivity.getAvgRatingVendor(vendorId);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		return avgRating;
    }
	
	/*
	 * DELETE to delete a review by reviewId
	 */
	@RequestMapping(value = "/review/deleteReview/{rId}", method = RequestMethod.DELETE, params="reviewId")
    public @ResponseBody StringRepresentation deleteReview(@PathVariable String rId) {
		int reviewId = Integer.parseInt(rId);
		return reviewActivity.deleteReview(reviewId);
    }


}
