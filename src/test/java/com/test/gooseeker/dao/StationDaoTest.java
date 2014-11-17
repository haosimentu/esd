package com.test.gooseeker.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gooseeker.dao.StationDao;
import com.gooseeker.dao.beans.Station;

public class StationDaoTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testAuthorityDao()
	{
		StationDao dao = (StationDao) context.getBean("stationDaoBean");
		Assert.assertNotNull(dao);
		
		List<Station> pipelines = dao.getAllStations();
		Assert.assertNotEquals(0, pipelines.size());
		
		long rowID = dao.insertStation("面板投入",1, "1", "A000001","1", "面板投入01");
		Assert.assertNotEquals(0, rowID);
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
