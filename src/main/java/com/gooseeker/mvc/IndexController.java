package com.gooseeker.mvc;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gooseeker.util.Constants;
import com.gooseeker.util.SeekerUser;

@Controller
public class IndexController 
{
	@RequestMapping("/index.html")
	public ModelAndView index(){
//		// 打标签使用的java
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SeekerUser user = (SeekerUser)Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			return new ModelAndView("login");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("menus", user.getMenus());
		
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:user/monitor.html");
		return view;
	}
	
	@RequestMapping("/login.html")
	public ModelAndView login() throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = Constants.USER_MAP.get(authentication.getName());
		if(user == null)
		{
			//只允许一个用户登录
			ModelAndView view = new ModelAndView("login");
			return view;
		}
		else
		{
			ModelAndView view = new ModelAndView();
			view.setViewName("redirect:index.html");
			return view;
		}
	}
	
}
