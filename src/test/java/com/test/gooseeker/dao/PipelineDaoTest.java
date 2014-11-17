package com.test.gooseeker.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gooseeker.dao.PipelineDao;
import com.gooseeker.dao.beans.Pipeline;

public class PipelineDaoTest {
	ApplicationContext context;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Assert.assertNotNull(context);
	}
	
	@Test
	public void testAuthorityDao()
	{
		PipelineDao dao = (PipelineDao) context.getBean("pipelineDaoBean");
		Assert.assertNotNull(dao);
		
		List<Pipeline> pipelines = dao.getAllPipelines();
		Assert.assertNotEquals(0, pipelines.size());
		
		long rowID = dao.insertPipeline("line", "A01", "流水线A01-line");
		Assert.assertNotEquals(0, rowID);
	}
	
	
	@After
	public void destroy()
	{
		
	}
}
