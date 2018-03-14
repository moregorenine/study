package me.w4springrain.springmvc.home.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import me.w4springrain.springmvc.web.service.AbstractDispatcherServlet;

public class HomeControllerTest extends AbstractDispatcherServlet {

	private static final Logger logger = LoggerFactory.getLogger(HomeControllerTest.class);
	
	@Test
	public void home() throws ServletException, IOException {
		ModelAndView mav = setConfig("/spring/appServlet/servlet-context.xml")
				.setClasses(HomeController.class)
				.initRequest("/home/main.do", RequestMethod.GET)
				.addParameter("name", "Spring")
				.runService()
				.getModelAndView();
		logger.info("mav is {}.", mav);
		assertThat(mav.getViewName(), is("home"));
	}

}
