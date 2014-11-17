package com.test.gooseeker.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gooseeker.business.UserService;

public class UserServiceTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testUserService()
	{
		UserService s = (UserService)context.getBean("userServiceBean");
		Assert.assertNotNull(s);
		Assert.assertTrue(s.userLogin("wwgm", "aaaaaa"));
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE_ENGINEER");
		roles.add("ROLE_USER");
		long userId = s.insertUserWithRole("liwenchao", "lwc", "lwc", "liwenchao@123.com", "12388888888", roles);
		Assert.assertNotEquals(0, userId);
		List<String> newRoles = new ArrayList<String>();
		newRoles.add("ROLE_ENGINEER");
		newRoles.add("ROLE_ADMIN");
		int result = s.updateUserWithRole(userId, "liwenchaooooo", null, null, null, newRoles);
		Assert.assertNotEquals(0, result);
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
