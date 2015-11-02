package com.project.ws.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.project.ws.domain.Review;
import com.project.ws.workflow.ReviewActivity;

@RestController
public class ReviewController implements ErrorController {

	private static final String ERRORPATH = "/error";
	private static final String errorString = "You have received this page in ERROR. ";
	
	private static final Map<Object, String> errorMessages = ImmutableMap.<Object, String>builder()
			.put(HttpServletResponse.SC_NOT_FOUND, "The requested resource does not exist")
			.put(HttpServletResponse.SC_BAD_REQUEST, "The URI entered is incorrect. Please rectify and submit again")
			.put(HttpServletResponse.SC_GATEWAY_TIMEOUT, "Server Error. Please try later")
			.put(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Please contact the System Administrator")
			.put(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "This method is not allowed to access the resource. Please rectify your request")
			.put("Default", "Please contact the System Administrator")
			.build();

	@Autowired
	private ReviewActivity reviewActivity;
	
    @Override
    public String getErrorPath() {
        return ERRORPATH;
    }
    
	@RequestMapping(ERRORPATH)
	public @ResponseBody String error(HttpServletRequest request, HttpServletResponse response) {
		return errorString + errorMessages.get(response.getStatus());
    }

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


		int reviewAdded = reviewActivity.addReview(review);
		if (reviewAdded > 0) {
			return "Successfully Added Review" ;
		}
		return "Failed";
	}
	

	@RequestMapping("/review/avgReview/product")
	public double getAvgRatingProduct(HttpServletRequest request) {
		int productId = Integer.parseInt(request.getParameter("productId"));
		return  reviewActivity.getAvgRatingProduct(productId);
    }


	@RequestMapping("/review/avgReview/vendor")
	public double getAvgRatingVendor(HttpServletRequest request) {
		int vendorId = Integer.parseInt(request.getParameter("vendorId"));
		return reviewActivity.getAvgRatingVendor(vendorId);
    }
	
	@RequestMapping(value = "/review/deleteReview/{rId}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteCustomer(@PathVariable String rId) {
		int reviewId = Integer.parseInt(rId);
		try {
			int noOfDeletedRow = reviewActivity.deleteReview(reviewId);
			if (noOfDeletedRow > 0) {
				return "Deleted Successfully";
			}
		} catch (Exception ex) {
			return "ERROR!";
		}
		return "No rows found to delete";
    }


}
