package com.test.gooseeker.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gooseeker.dao.MenuDao;
import com.gooseeker.dao.beans.Menu;

public class MenuDaoTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testAuthorityDao()
	{
		MenuDao dao = (MenuDao) context.getBean("menuDaoBean");
		Assert.assertNotNull(dao);
		List<Menu> menus = dao.findMenuByRole("ROLE_ADMIN");
		Assert.assertNotNull(menus);
		Assert.assertNotEquals(menus.size(),0L);
		menus = dao.findMenuByUserId(2);
		Assert.assertNotNull(menus);
		Assert.assertNotEquals(menus.size(),0L);
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
