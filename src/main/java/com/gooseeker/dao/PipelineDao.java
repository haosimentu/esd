package com.gooseeker.dao;

import com.gooseeker.dao.beans.Pipeline;
import com.gooseeker.util.DBSeekerException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineDao extends Dao{
	private String LIST = "listPipeline";
	private String INSERT_PIPELINE = "insertPipeline";
	private String FIND_PIPELINE_4_PAGE = "findPipelines4Page";
	private String FIND_PIPELINE_4_PAGE_COUNT = "findPipelines4PageCount";
	private String DELETE_PIPELINE = "deletePipeline";
	private String GET_PIPELINE_BY_ID = "getPipelineById";
	private String UPDATE_PIPELINE = "updatePipeline";
	private String UPDATE_PIPELINE_STATIONS = "updatePipelineStations";
	
	public List<Pipeline> getAllPipelines()
	{
		return getMetacorpora().selectList(LIST);
	}
	
	public long insertPipeline(String name,String number,String desc)
	{
		long id = 0L;
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("name", name);
		parameterMap.put("number", number);
		parameterMap.put("stations", 0);
		parameterMap.put("desc", desc);
		
		int result = getMetacorpora().insert(INSERT_PIPELINE, parameterMap);
		if(result > 0)
		{
			id = Long.parseLong(String.valueOf(parameterMap.get("ID")));
		}
		return id;
	}
	/**
	 * 根据流水线名称或者编号查询
	 * @param keyword
	 * @param start
	 * @param length
	 * @return
	 */
	public List<Pipeline> findPipelines4Page(String keyword,int start,int length)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("keyword", keyword);
		parameterMap.put("start", start);
		parameterMap.put("length", length);
		
		return getMetacorpora().selectList(FIND_PIPELINE_4_PAGE,parameterMap);
	}
	
	/**
	 * 根据流水线名称或者编号查询
	 * @param keyword
	 * @param start
	 * @param length
	 * @return
	 */
	public int findPipelines4PageCount(String keyword)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("keyword", keyword);
		
		return getMetacorpora().selectOne(FIND_PIPELINE_4_PAGE_COUNT,parameterMap);
	}
	/**
	 * 根据ID删除流水线，更新状态为删除
	 * @param id
	 * @return
	 */
	public int deletePipeline(long id) throws DBSeekerException{
		try 
		{
			int result = getMetacorpora().delete(DELETE_PIPELINE,id);
			return result;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public Pipeline getPipelineById(long id)
	{
		return getMetacorpora().selectOne(GET_PIPELINE_BY_ID,id);
	}
	
	public int updatePipeline(long id,String name,String number,int stations,String desc)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("id", id);
		parameterMap.put("name", name);
		parameterMap.put("number", number);
		parameterMap.put("stations", stations);
		parameterMap.put("desc", desc);
		
		return getMetacorpora().update(UPDATE_PIPELINE, parameterMap);
	}
	/**
	 * 
	 * @param stationID
	 * @param num
	 * @return
	 */
	public int updatePipelineStations(long stationId,int num)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("stationId", stationId);
		parameterMap.put("num", num);
		
		return getMetacorpora().update(UPDATE_PIPELINE_STATIONS, parameterMap);
	}
}
