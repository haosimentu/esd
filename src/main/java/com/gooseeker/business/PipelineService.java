package com.gooseeker.business;

import java.util.List;

import com.gooseeker.dao.beans.Pipeline;
import com.gooseeker.dao.beans.Summary;

public interface PipelineService {
	List<Summary> queryPipelineSummary();
	
	List<Pipeline> findPipelines4Page(String keyword,int start,int length);
	int findPipelines4PageCount(String keyword);
	
	int deletePipeline(long id);
	
	List<Pipeline> getAllPipelines();
	Pipeline getPipeline(long id);
	
	long insertPipeline(String name,String number,String desc);
	
	int updatePipeline(long id,String name,String number,int stations,String desc);
}
