package com.gooseeker.business.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.gooseeker.business.MonitorService;
import com.gooseeker.dao.MonitorDao;
import com.gooseeker.dao.beans.Monitor;

public class MonitorServiceImpl implements MonitorService {
	private MonitorDao monitorDao;
	
	@Autowired
	public void setMonitorDao(MonitorDao monitorDao) {
		this.monitorDao = monitorDao;
	}

	@Override
	public List<Monitor> queryMonitor4Page(Long pipelineId, Long stationId,
			String startTime, String endTime, int start, int length) {
		return monitorDao.queryMonitor4Page(pipelineId, stationId, startTime, endTime, start, length);
	}

	@Override
	public long queryMonitor4PageCount(Long pipelineId, Long stationId,
			String startTime, String endTime) {
		return monitorDao.queryMonitor4PageCount(pipelineId, stationId, startTime, endTime);
	}
	
	public long insertMonitorData(long pipelineId,String pipelineName,long stationId,String stationName,Date monitorTime,int result,String value)
	{
		return monitorDao.insertMonitor(pipelineId, pipelineName, stationId, stationName, monitorTime, result,value);
	}

	@Override
	public List<Monitor> queryLastMonitor4Pipeline(long pipelineId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		String endTime = sdf.format(calendar.getTime());
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)-1);
		String startTime = sdf.format(calendar.getTime());
		
		return monitorDao.queryMonitor4Pipeline(pipelineId, startTime, endTime);
	}

	@Override
	public List<Monitor> queryMonitorSummary(String startTime, String endTime) {
		return monitorDao.queryMonitorSummary(startTime, endTime);
	}

	@Override
	public int deleteMonitor(String endTime) {
		return monitorDao.deleteMonitorByTime(endTime);
	}

	@Override
	public int insertMonitor4Address(String address, String subAddress,String result, String value) {
		return monitorDao.insertMonitor4Address(address, subAddress, result, value);
	}

}
