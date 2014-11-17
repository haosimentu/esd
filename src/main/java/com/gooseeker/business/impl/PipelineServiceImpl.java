package com.gooseeker.business.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gooseeker.business.PipelineService;
import com.gooseeker.dao.MonitorDao;
import com.gooseeker.dao.PipelineDao;
import com.gooseeker.dao.beans.Monitor;
import com.gooseeker.dao.beans.Overview;
import com.gooseeker.dao.beans.Pipeline;
import com.gooseeker.dao.beans.Summary;

public class PipelineServiceImpl implements PipelineService {
	private MonitorDao monitorDao;
	private PipelineDao pipelineDao;
	
	@Autowired
	public void setPipelineDao(PipelineDao pipelineDao) {
		this.pipelineDao = pipelineDao;
	}


	@Autowired
	public void setMonitorDao(MonitorDao monitorDao) {
		this.monitorDao = monitorDao;
	}


	@Override
	public List<Summary> queryPipelineSummary() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		Calendar calendarr = Calendar.getInstance(Locale.CHINESE);
		
		String endTime = sdf.format(calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-1);
		String startTime = sdf.format(calendar.getTime());
		
		String eTime = sdf.format(calendarr.getTime());
		calendarr.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)-1);
		String sTime = sdf.format(calendarr.getTime());
		
		List<Monitor> monitors = monitorDao.queryMonitorSummary(startTime, endTime);
		List<Summary> summaries = new ArrayList<Summary>();
		
		
		
		Map<Long, Summary> tempMap = new HashMap<Long, Summary>();
		for(Monitor m : monitors)
		{
			Summary summary = tempMap.get(m.getPipelineId());
			if(null == summary)
			{
				summary = new Summary();
				summary.setPipelineId(m.getPipelineId());
				summary.setPipelineName(m.getPipelineName());
				tempMap.put(m.getPipelineId(),summary);
			}
			
			List<Overview> overviews = summary.getOverviews();
			if(null == overviews)
			{
				overviews = new ArrayList<Overview>();
				summary.setOverviews(overviews);
			}
			overviews.add(new Overview(m.getResult(),m.getDurations()));
			
		}
		
		Iterator<Summary> it = tempMap.values().iterator();
		while(it.hasNext())
		{
			Summary summary = it.next();
			List<Monitor> mList = monitorDao.queryMonitor4Pipeline(summary.getPipelineId(), sTime, eTime);
			summary.setMonitors(mList);
			summaries.add(summary);
		}
		return summaries;
	}


	@Override
	public List<Pipeline> findPipelines4Page(String keyword, int start, int length) {
		return pipelineDao.findPipelines4Page(keyword, start, length);
	}


	@Override
	public int findPipelines4PageCount(String keyword) {
		return pipelineDao.findPipelines4PageCount(keyword);
	}


	@Override
	public int deletePipeline(long id) {
		return pipelineDao.deletePipeline(id);
	}


	@Override
	public Pipeline getPipeline(long id) {
		return pipelineDao.getPipelineById(id);
	}


	@Override
	public int updatePipeline(long id, String name, String number,int stations, String desc) {
		return pipelineDao.updatePipeline(id, name, number,stations, desc);
	}


	@Override
	public long insertPipeline(String name, String number, String desc) {
		return pipelineDao.insertPipeline(name, number, desc);
	}
	
	@Override
	public List<Pipeline> getAllPipelines()
	{
	    return pipelineDao.getAllPipelines();
	}

}
