package com.test.gooseeker.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gooseeker.dao.AuthorityDao;
import com.gooseeker.dao.beans.Authority;

public class AuthorityDaoTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testAuthorityDao()
	{
		AuthorityDao dao = (AuthorityDao) context.getBean("authorityDaoBean");
		Assert.assertNotNull(dao);
		List<Authority> authorities = dao.findAuthoritiesByUserId(1);
		Assert.assertNotNull(authorities);
		Assert.assertNotEquals(authorities.size(),0L);
		long rowId = dao.insertAuthority(1L, "ROLE_ENGINEER");
		Assert.assertNotEquals(0, rowId);
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
