package com.gooseeker.dao;

import com.gooseeker.dao.beans.Monitor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorDao extends Dao{
	private String LIST = "listMonitor";
	private String INSERT_MONITOR = "insertMonitor";
	private String QUERY_MONITOR_WITH_PAGE = "queryMonitor4Page";
	private String QUERY_MONITOR_WITH_PAGE_COUNT = "queryMonitor4PageCount";
	private String QUERY_MONITOR_SUMMARY = "queryMonitorSummary";
	private String QUERY_MONITOR_4_PIPELINE = "queryMonitor4Pipeline";
	private String DELETE_MONITOR_BY_TIME = "deleteMonitorByTime";
	private String INSERT_MONITOR_4_ADDRESS = "insertMonitor4Address";
	public List<Monitor> getAllMonitors()
	{
		return getMetacorpora().selectList(LIST);
	}
	/**
	 * 
	 * @param stationId
	 * @param startTime
	 * @param endTime
	 * @param result
	 * @return
	 */
	public long insertMonitor(long pipelineId,String pipelineName,long stationId,String stationName,Date monitorTime,int result,String value)
	{
		long id = 0L;
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("pipelineId", pipelineId);
		parameterMap.put("pipelineName", pipelineName);
		parameterMap.put("stationId", stationId);
		parameterMap.put("stationName", stationName);
		parameterMap.put("monitorTime", monitorTime);
		parameterMap.put("result", result);
		parameterMap.put("value", value);
		
		int rt = getMetacorpora().insert(INSERT_MONITOR, parameterMap);
		if(rt > 0)
		{
			id = Long.parseLong(String.valueOf(parameterMap.get("ID")));
		}
		return id;
	}
	/**
	 * 根据设备地址和传感器地址，插入监控数据
	 * @param address
	 * @param subAddress
	 * @param result
	 * @param value
	 * @return
	 */
	public int insertMonitor4Address(String address,String subAddress,String result,String value)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("address", address);
		parameterMap.put("subAddress", subAddress);
		parameterMap.put("result", result);
		parameterMap.put("value", value);
		
		return getMetacorpora().insert(INSERT_MONITOR_4_ADDRESS, parameterMap);
	}
	/**
	 * 
	 * @param pipelineId
	 * @param stationId
	 * @param startTime 2014-11-03 18:44:50
	 * @param endTime 2014-11-03 18:44:50
	 * @param start 
	 * @param length
	 * @return
	 */
	public List<Monitor> queryMonitor4Page(Long pipelineId, Long stationId, String startTime,String endTime,int start,int length) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("pipelineId", pipelineId);
		parameterMap.put("stationId", stationId);
		parameterMap.put("startTime", startTime);
		parameterMap.put("endTime", endTime);
		parameterMap.put("start", start);
		parameterMap.put("length", length);
		
		return getMetacorpora().selectList(QUERY_MONITOR_WITH_PAGE, parameterMap);
	}
	/**
	 * 
	 * @param pipelineId
	 * @param stationId
	 * @param startTime 2014-11-03 18:44:50
	 * @param endTime 2014-11-03 18:44:50
	 * @return
	 */
	public long queryMonitor4PageCount(Long pipelineId, Long stationId, String startTime,String endTime) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("pipelineId", pipelineId);
		parameterMap.put("stationId", stationId);
		parameterMap.put("startTime", startTime);
		parameterMap.put("endTime", endTime);
		
		return getMetacorpora().selectOne(QUERY_MONITOR_WITH_PAGE_COUNT, parameterMap);
	}
	/**
	 * 根据传入的开始时间和结束时间获取该时间断的概略信息
	 * @param startTime 2014-11-03 18:44:50
	 * @param endTime 2014-11-03 18:44:50
	 * @return
	 */
	public List<Monitor> queryMonitorSummary(String startTime,String endTime)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("startTime", startTime);
		parameterMap.put("endTime", endTime);
		
		return getMetacorpora().selectList(QUERY_MONITOR_SUMMARY, parameterMap);
	}
	
	/**
	 * 根据流水线和时间断查询监控数据
	 * @param startTime 2014-11-03 18:44:50
	 * @param endTime 2014-11-03 18:44:50
	 * @return
	 */
	public List<Monitor> queryMonitor4Pipeline(long pipelineId,String startTime,String endTime)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("pipelineId", pipelineId);
		parameterMap.put("startTime", startTime);
		parameterMap.put("endTime", endTime);
		
		return getMetacorpora().selectList(QUERY_MONITOR_4_PIPELINE, parameterMap);
	}
	/**
	 * 删除endTime之前的数据
	 * @param endTime
	 * @return
	 */
	public int deleteMonitorByTime(String endTime) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("endTime", endTime);
		
		return getMetacorpora().delete(DELETE_MONITOR_BY_TIME,parameterMap);
	}
}
