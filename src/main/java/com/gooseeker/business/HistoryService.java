package com.gooseeker.business;

import java.util.List;
import com.gooseeker.dao.beans.History;

public interface HistoryService {
	
	List<History> queryHistoryByName4Page(String keyword, String startTime,String endTime,int start,int length);
	int queryHistoryByName4PageCount(String keyword, String startTime,String endTime);
	
	int deleteHistory(String endTime);
}
