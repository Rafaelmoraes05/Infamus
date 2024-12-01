package com.devcaotics.infamus.controllers.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionUtils {
	
	@Autowired
	private HttpSession session;

	public void removerMsg() {
		
		//((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		session.removeAttribute("msg");
		
	}
	
}
