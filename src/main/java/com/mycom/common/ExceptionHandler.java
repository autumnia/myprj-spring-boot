package com.mycom.common;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(SQLException.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, SQLException exception){
		ModelAndView mv = new ModelAndView("/errors/error");
		mv.addObject("exception", exception);

		log.error("defaultExceptionHandler", exception);

		return mv;
	}


	@org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, NullPointerException exception){
		ModelAndView mv = new ModelAndView("/errors/error");
		mv.addObject("exception", exception);

		log.error("defaultExceptionHandler", exception);

		return mv;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(IOException.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, IOException exception){
		ModelAndView mv = new ModelAndView("/errors/error");
		mv.addObject("exception", exception);

		log.error("defaultExceptionHandler", exception);

		return mv;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception){
		ModelAndView mv = new ModelAndView("/errors/error");
		mv.addObject("exception", exception);
		
		log.error("defaultExceptionHandler", exception);
		
		return mv;
	}
}


