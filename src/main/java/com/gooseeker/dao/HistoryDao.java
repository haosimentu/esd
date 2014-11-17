package com.gooseeker.dao;

import com.gooseeker.dao.beans.History;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryDao extends Dao{
	private String QUERY_HISTORY_BY_PIPELINE_STATION = "queryHistoryByPipelineAndStation4Page";
	private String QUERY_HISTORY_BY_PIPELINE_STATION_COUNT = "queryHistoryByPipelineAndStation4PageCount";
	private String DELETE_HISTORY_BY_TIME = "deleteHistoryByTime";
	/**
	 * 根据流水线名称和工位名称模糊查询
	 * @param pipelineName
	 * @param stationName
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param length
	 * @return
	 */
	public List<History> queryHistoryByPipelineAndStation4Page(String keyword,String startTime,String endTime,int start,int length )
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("keyword", keyword);
		parameterMap.put("startTime", startTime);
		parameterMap.put("endTime", endTime);
		parameterMap.put("start", start);
		parameterMap.put("length", length);
		
		return getMetacorpora().selectList(QUERY_HISTORY_BY_PIPELINE_STATION, parameterMap);
	}
	
	/**
	 * 根据流水线名称和工位名称模糊查询
	 * @param pipelineName
	 * @param stationName
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param length
	 * @return
	 */
	public int queryHistoryByPipelineAndStation4PageCount(String keyword,String startTime,String endTime)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("keyword", keyword);
		parameterMap.put("startTime", startTime);
		parameterMap.put("endTime", endTime);
		
		return getMetacorpora().selectOne(QUERY_HISTORY_BY_PIPELINE_STATION_COUNT, parameterMap);
	}
	
	public int deleteHistoryByTime(String endTime)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("endTime", endTime);
		
		return getMetacorpora().delete(DELETE_HISTORY_BY_TIME,parameterMap);
	}
	
}
