package com.gooseeker.util;

import javax.servlet.http.HttpSessionEvent;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

public class ThemeSessionEventPublisher extends HttpSessionEventPublisher {

	@Override
	public void sessionDestroyed(HttpSessionEvent event) 
	{
		super.sessionDestroyed(event);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null)
		{
			return;
		}
		Constants.USER_MAP.remove(authentication.getName());
	}

}
