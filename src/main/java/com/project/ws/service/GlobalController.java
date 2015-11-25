package com.project.ws.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.project.ws.representation.StringRepresentation;

@EnableWebMvc
@ControllerAdvice
class GlobalController {
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({CustomerNotFoundException.class, NumberFormatException.class})
    public @ResponseBody StringRepresentation handleCustomerNotFoundException(HttpServletRequest request, CustomerNotFoundException ex) {
//		String message = "";
//		if(ex.getMessage() != null)
//			message = ex.getMessage();
//		ex.printStackTrace();
//		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please correct the parameter passed.";
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("errorMessage", error);
//		mav.setViewName("globalerror");
//		return mav;
		StringRepresentation stringRepresentation = new StringRepresentation();
		String message = "";
		if(ex.getMessage() != null)
			message = ex.getMessage();
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please correct the parameter passed.";
		stringRepresentation.setMessage(error);
		return stringRepresentation;
    }

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public @ResponseBody StringRepresentation handleMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		String message = "The method is not allowed to access this resource";
//		if(ex.getMessage() != null)
//			message = ex.getMessage();
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please correct the request.";
		stringRepresentation.setMessage(error);
		return stringRepresentation;
    }
	
	@ExceptionHandler({Exception.class,
		org.springframework.http.converter.HttpMessageNotWritableException.class, 
		org.springframework.beans.ConversionNotSupportedException.class
		})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody StringRepresentation handleServerErrorException(HttpServletRequest request, Exception ex) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		String message = "There is an internal server error.";
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please contact the system administrator.";
		stringRepresentation.setMessage(error);
		return stringRepresentation;
    }	
	
	@ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class, 
		org.springframework.web.client.HttpClientErrorException.class, 
		org.springframework.web.bind.MissingServletRequestParameterException.class, 
		org.springframework.web.bind.ServletRequestBindingException.class, 
		org.springframework.web.bind.MethodArgumentNotValidException.class, 
		org.springframework.web.bind.UnsatisfiedServletRequestParameterException.class 
		})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody StringRepresentation handleBadRequestException(HttpServletRequest request, Exception ex) {
		System.out.println("in here for bad request");
		StringRepresentation stringRepresentation = new StringRepresentation();
		String message = "The request issued by the client is malformed. ";
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please rectify and submit again.";
		stringRepresentation.setMessage(error);
		System.out.println("error is " + error);
		return stringRepresentation;
    }

	@ExceptionHandler({org.springframework.web.HttpMediaTypeNotSupportedException.class})
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public @ResponseBody StringRepresentation handleMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException ex) {
		StringRepresentation stringRepresentation = new StringRepresentation();
		String message = "The media type requested is not supported. ";
		ex.printStackTrace();
		String error = "Error: " + message + " : " + request.getRequestURI() + ". Please rectify and submit again.";
		stringRepresentation.setMessage(error);
		return stringRepresentation;
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
