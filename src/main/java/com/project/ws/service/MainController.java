package com.project.ws.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/")
public class MainController {

	@RequestMapping("/")
	String index() {
		return "index";
	}
}
