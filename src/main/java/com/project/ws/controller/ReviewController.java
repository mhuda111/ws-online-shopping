package com.project.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ws.database.domain.Review;
import com.project.ws.database.repository.custom.ReviewRepository;

/**
 * This is customer spring controller which has methods
 * that specify the end points for the customer web service.
 */
@RestController
public class ReviewController {

	@Autowired
    private ReviewRepository reviewRepository;
	@RequestMapping("/review/")
    public List<Review> getReviewByReviewID(HttpServletRequest request) {
		Integer reviewId = Integer.parseInt(request.getParameter("reviewId"));
    	return reviewRepository.getReviewByReviewID(reviewId);
    }

}
