package com.test.gooseeker.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gooseeker.dao.UserDao;
import com.gooseeker.dao.beans.Userr;

public class UserDaoTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testUserDao()
	{
		UserDao userDao = (UserDao) context.getBean("userDaoBean");
		Assert.assertNotNull(userDao);
		Userr u = userDao.getUserById(1);
		Assert.assertNotNull(u);
//		Assert.assertEquals(u.getName(), "wwgm");
		List<Userr> users = userDao.getAllUsers();
		Assert.assertNotNull(users);
		
		int result = userDao.updateUser(1L, "wangguangming", null,null,null);
		Assert.assertNotEquals(0, result);
//		long rowId = userDao.insertUserinfo("haosimentu", "wgm", "wgm", null, null);
//		Assert.assertNotEquals(rowId, 0);
		
//		Userr userr = userDao.findUserByAccount("admin");
//		Assert.assertNotNull(userr);
//		int result = userDao.deleteUser(12L);
//		Assert.assertNotEquals(0, result);
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
