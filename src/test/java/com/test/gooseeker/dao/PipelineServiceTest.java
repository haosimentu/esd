package com.test.gooseeker.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gooseeker.business.PipelineService;
import com.gooseeker.dao.beans.Summary;

public class PipelineServiceTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testLogin()
	{
		PipelineService service = (PipelineService)context.getBean("pipelineServiceBean");
		Assert.assertNotNull(service);
		List<Summary> summaries = service.queryPipelineSummary();
		Assert.assertNotEquals(0, summaries.size());
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
