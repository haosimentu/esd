package com.gooseeker.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.gooseeker.business.StationService;

public class TaskJob {
	private StationService stationService;
	
	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

	public void execute() {

		List<String> addresses = stationService.findAllAddresses();
		
		for(String address : addresses)
		{
			//发指令到串口
		}
	
	}
}
