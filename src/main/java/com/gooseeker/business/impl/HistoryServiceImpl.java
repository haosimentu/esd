package com.gooseeker.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gooseeker.business.HistoryService;
import com.gooseeker.dao.HistoryDao;
import com.gooseeker.dao.beans.History;

public class HistoryServiceImpl implements HistoryService {
	private HistoryDao historyDao;
	
	@Autowired
	public void setHistoryDao(HistoryDao historyDao) {
		this.historyDao = historyDao;
	}

	@Override
	public List<History> queryHistoryByName4Page(String keyword, String startTime, String endTime, int start,
			int length) {
		return historyDao.queryHistoryByPipelineAndStation4Page(keyword, startTime, endTime, start, length);
	}

	@Override
	public int queryHistoryByName4PageCount(String keyword, String startTime, String endTime) {
		return historyDao.queryHistoryByPipelineAndStation4PageCount(keyword, startTime, endTime);
	}

	@Override
	public int deleteHistory(String endTime) {
		return historyDao.deleteHistoryByTime(endTime);
	}
}
