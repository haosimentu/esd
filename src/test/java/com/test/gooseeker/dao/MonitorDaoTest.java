package com.test.gooseeker.dao;

import java.util.Date;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gooseeker.dao.MonitorDao;

public class MonitorDaoTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testAuthorityDao()
	{
		MonitorDao dao = (MonitorDao) context.getBean("monitorDaoBean");
		Assert.assertNotNull(dao);
		
//		List<Monitor> monitors = dao.getAllMonitors();
//		Assert.assertNotEquals(0, monitors.size());
		long rowID = dao.insertMonitor(1,"line",5,"station",new Date(), 1,"0|555");
		Assert.assertNotEquals(0, rowID);
		
//		List<Monitor> monitors = dao.queryMonitor4Page(1L, null, null,null, 0, 5);
//		Assert.assertNotEquals(0, monitors.size());
//		
//		long count = dao.queryMonitor4PageCount(null, null, null,null);
//		Assert.assertNotEquals(0, count);
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY,18);
//		String startTime = sdf.format(calendar.getTime());
//		calendar.set(Calendar.HOUR_OF_DAY, 19);
//		String endTime = sdf.format(calendar.getTime());
//		monitors = dao.queryMonitorSummary(startTime, endTime);
//		Assert.assertNotEquals(0, monitors.size());
////		SimpleDateFormat sdFormat = new SimpleDateFormat("YYYY-MM-DD HH:MI:SS");
////		sdFormat.parse(endTime.toGMTString());
//		monitors = dao.queryMonitor4Pipeline(1,startTime, endTime);
//		Assert.assertNotEquals(0, monitors.size());
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
