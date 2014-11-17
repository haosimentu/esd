package com.test.gooseeker.dao;

import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gooseeker.business.MonitorService;

public class MonitorServiceTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testMonitor()
	{
		MonitorService service = (MonitorService)context.getBean("monitorServiceBean");
		Assert.assertNotNull(service);
		long rowId = service.insertMonitorData(2, "line2", 1, "site1", new Date(), 0,"0|403");
		Assert.assertNotEquals(0, rowId);
		rowId = service.insertMonitorData(2, "line2", 2, "site2", new Date(), 1,"0|443");
		Assert.assertNotEquals(0, rowId);
		rowId = service.insertMonitorData(1, "line1", 1, "site1", new Date(), 1,"0|493");
		Assert.assertNotEquals(0, rowId);
		
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
