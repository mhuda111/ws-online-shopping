package com.project.ws.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice
class GlobalControllerExceptionHandler {
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({CustomerNotFoundException.class, NumberFormatException.class})
    public ModelAndView handleCustomerNotFoundException(HttpServletRequest request, CustomerNotFoundException ex) {
		String message = "";
		if(ex.getMessage() != null)
			message = ex.getMessage();
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please correct the parameter passed.";
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", error);
		mav.setViewName("globalerror");
		return mav;
    }

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ModelAndView handleMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
		String message = "The method is not allowed to access this resource";
//		if(ex.getMessage() != null)
//			message = ex.getMessage();
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please correct the request.";
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", error);
		mav.setViewName("globalerror");
		return mav;
    }
	
	@ExceptionHandler({org.springframework.http.converter.HttpMessageNotWritableException.class, org.springframework.beans.ConversionNotSupportedException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleServerErrorException(HttpServletRequest request, Exception ex) {
		String message = "There is an internal server error.";
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please contact the system administrator.";
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", error);
		mav.setViewName("globalerror");
		return mav;
    }	
	
	@ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class, org.springframework.web.client.HttpClientErrorException.class, org.springframework.web.bind.MissingServletRequestParameterException.class, org.springframework.web.bind.ServletRequestBindingException.class, org.springframework.web.bind.MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBadRequestException(HttpServletRequest request, Exception ex) {
		String message = "The request issued by the client is malformed. ";
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please rectify and submit again.";
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", error);
		mav.setViewName("globalerror");
		return mav;
    }

	@ExceptionHandler({org.springframework.web.HttpMediaTypeNotSupportedException.class})
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ModelAndView handleMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
		String message = "The media type requested is not supported. ";
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please rectify and submit again.";
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", error);
		mav.setViewName("globalerror");
		return mav;
    }
	
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({PageNotFoundException.class})
	public ModelAndView handlePageNotFoundException(HttpServletRequest request, PageNotFoundException ex) {
		String message = "The page being accessessed does not exist.";
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please rectify and submit again.";
		ModelAndView mav = new ModelAndView();
		mav.addObject("errorMessage", error);
		mav.setViewName("globalerror");
		return mav;
	}
	
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	@ExceptionHandler({})
//	public ModelAndView handleNumberFormatException(HttpServletRequest request, NumberFormatException ex) {
//		String message = "The page being accessessed does not exist.";
//		ex.printStackTrace();
//		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please rectify and submit again.";
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("errorMessage", error);
//		mav.setViewName("globalerror");
//		return mav;
//	}
}
