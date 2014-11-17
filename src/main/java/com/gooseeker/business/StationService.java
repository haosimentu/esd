package com.gooseeker.business;

import java.util.List;
import java.util.Map;

import com.gooseeker.dao.beans.Station;


public interface StationService
{
	long insertStation(String name,long pipelineId,String number,String address,String subAddress,String desc);
    public Map<Long, List<Station>> queryAllStationInfo();
    
    List<Station> findStations4Page(String keyword,int start,int length);
    int findStations4PageCount(String keyword);
    
    List<Station> listStations4Page(long pipelineId,int start,int length);
    int listStations4PageCount(long pipelineId);
    
    int deleteStation(long id);
    
    Station getStation(long id);
	int updateStation(long id, String name, String number, String pipelineId,String address, String desc);
	
	List<String> findAllAddresses();
}
