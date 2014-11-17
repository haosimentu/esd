package com.gooseeker.dao;

import com.gooseeker.dao.beans.Station;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationDao extends Dao{
	private String LIST = "listStation";
	private String INSERT_STATION = "insertStation";
	private String FIND_STATIONS_4_PAGE = "findStations4Page";
	private String FIND_STATIONS_4_PAGE_COUNT = "findStations4PageCount";
	private String DELETE_STATION = "deleteStation";
	private String GET_STATION_BY_ID = "getStationById";
	private String UPDATE_STATION = "updateStation";
	private String LIST_STATION_4_PAGE = "listStation4Page";
	private String LIST_STATION_4_PAGE_COUNT = "listStation4PageCount";
	private String FIND_ALL_ADDRESS = "findAllAddress";
	
	public List<Station> getAllStations()
	{
		return getMetacorpora().selectList(LIST);
	}
	//NAME,PIPELINEID,NUMBER,ADDRESS,CREATETIME,DESCC
	public long insertStation(String name,long pipelineId,String number,String address,String subAddress,String desc)
	{
		long id = 0L;
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("name", name);
		parameterMap.put("pipelineId", pipelineId);
		parameterMap.put("number", number);
		parameterMap.put("address", address);
		parameterMap.put("subAddress", subAddress);
		parameterMap.put("desc", desc);
		
		int result = getMetacorpora().insert(INSERT_STATION, parameterMap);
		if(result > 0)
		{
			id = Long.parseLong(String.valueOf(parameterMap.get("ID")));
		}
		return id;
	}
	
	public List<Station> findStations4Page(String keyword,int start,int length)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("keyword", keyword);
		parameterMap.put("start", start);
		parameterMap.put("length", length);
		
		return getMetacorpora().selectList(FIND_STATIONS_4_PAGE,parameterMap);
	}
	
	public int findStations4PageCount(String keyword)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("keyword", keyword);
		return getMetacorpora().selectOne(FIND_STATIONS_4_PAGE_COUNT,parameterMap);
	}
	
	public int deleteStation(long id)
	{
		return getMetacorpora().delete(DELETE_STATION,id);
	}
	
	public Station getStationById(long id) {
		return getMetacorpora().selectOne(GET_STATION_BY_ID,id);
	}
	
	public int updateStation(long id, String name, String number, String pipelineId, String address, String desc)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("id", id);
		parameterMap.put("name", name);
		parameterMap.put("number", number);
		parameterMap.put("pipelineId", pipelineId);
		parameterMap.put("address", address);
		parameterMap.put("desc", desc);
		
		return getMetacorpora().update(UPDATE_STATION,parameterMap);
	}
	/**
	 * 根据pipelineId查询工位
	 * @param pipelineId
	 * @param start
	 * @param length
	 * @return
	 */
	public List<Station> listStation4Page(long pipelineId, int start, int length) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("pipelineId", pipelineId);
		parameterMap.put("start", start);
		parameterMap.put("length", length);
		
		return getMetacorpora().selectList(LIST_STATION_4_PAGE,parameterMap);
	}
	
	/**
	 * 根据pipelineId查询工位
	 * @param pipelineId
	 * @param start
	 * @param length
	 * @return
	 */
	public int listStation4PageCount(long pipelineId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("pipelineId", pipelineId);
		
		return getMetacorpora().selectOne(LIST_STATION_4_PAGE_COUNT,parameterMap);
	}
	/**
	 * 获取所有设备地址
	 * @return
	 */
	public List<String> findAllAddress()
	{
		return getMetacorpora().selectList(FIND_ALL_ADDRESS);
	}
}
