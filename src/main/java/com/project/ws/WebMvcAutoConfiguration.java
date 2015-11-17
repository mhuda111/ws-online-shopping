package com.project.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration

public class WebMvcAutoConfiguration extends WebMvcConfigurerAdapter {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/static/", "classpath:/static/css/", "classpath:/static/js/" };
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	    if (!registry.hasMappingForPattern("/webjars/**")) {
//	        registry.addResourceHandler("/webjars/**").addResourceLocations(
//	        		CLASSPATH_RESOURCE_LOCATIONS);
//	    }
	    if (!registry.hasMappingForPattern("/static/**")) {
	        registry.addResourceHandler("/static/**").addResourceLocations(
	        		CLASSPATH_RESOURCE_LOCATIONS);
	    }
	}
}
