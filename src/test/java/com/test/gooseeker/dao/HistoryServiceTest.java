package com.test.gooseeker.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.gooseeker.business.HistoryService;
import com.gooseeker.dao.beans.History;

public class HistoryServiceTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testHistory()
	{
		HistoryService service = (HistoryService)context.getBean("historyServiceBean");
		Assert.assertNotNull(service);
		List<History> monitors = service.queryHistoryByName4Page("卡位", null, null, 1, 10);
		Assert.assertNotEquals(monitors.size(), 0);
		long count = service.queryHistoryByName4PageCount("卡位", null, null);
		Assert.assertNotEquals(count, 0);
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
