package com.project.ws.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.domain.Review;
import com.project.ws.workflow.ReviewActivity;

@RestController
public class ReviewController {

	@Autowired
	private ReviewActivity reviewActivity;
	
	@ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(HttpServletRequest req, RuntimeException ex) {
		String message = "";
		if(ex.getMessage() != null)
			message = ex.getMessage();
		return "Error: " + message + " in path: " + req.getRequestURI() + ".\n\n Please contact the system administrator ";
    }

	@RequestMapping("/review/add")
	public String addReview(HttpServletRequest request) {
		int reviewAdded = 0;
		try {
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

			reviewAdded = reviewActivity.addReview(review);
		} catch(RuntimeException e) {
			throw new RuntimeException();
		}
		if (reviewAdded > 0) {
			return "Successfully Added Review" ;
		}
		return "Failed";
	}
	

	@RequestMapping("/review/avgReview/product")
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


	@RequestMapping("/review/avgReview/vendor")
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
	
	@RequestMapping(value = "/review/deleteReview/{rId}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteCustomer(@PathVariable String rId) {
		int noOfDeletedRow = 0;
		try {
			int reviewId = Integer.parseInt(rId);
			noOfDeletedRow = reviewActivity.deleteReview(reviewId);

		} catch (RuntimeException e) {
			throw new RuntimeException();
		}
		if (noOfDeletedRow > 0) {
			return "Deleted Successfully";
		}
		return "No rows found to delete";
    }


}
