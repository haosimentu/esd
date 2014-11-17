package com.gooseeker.business;

import java.util.Date;
import java.util.List;
import com.gooseeker.dao.beans.Monitor;

public interface MonitorService {
	List<Monitor> queryMonitor4Page(Long pipelineId, Long stationId, String startTime,String endTime,int start,int length);
	long queryMonitor4PageCount(Long pipelineId, Long stationId, String startTime,String endTime);
	long insertMonitorData(long pipelineId,String pipelineName,long stationId,String stationName,Date monitorTime,int result,String value);
	List<Monitor> queryLastMonitor4Pipeline(long pipelineId);
	List<Monitor> queryMonitorSummary(String startTime,String endTime);
	
	int deleteMonitor(String endTime);
	
	int insertMonitor4Address(String address,String subAddress,String result,String value);
}
