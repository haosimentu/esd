package com.gooseeker.job;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.gooseeker.business.HistoryService;
import com.gooseeker.business.MonitorService;
import com.gooseeker.util.Commons;

/**
 * 系统清理任务job，每天执行
 * @author ysite
 *
 */
public class CleanJob {
	private MonitorService monitorService;
	private HistoryService historyService;
	
	@Autowired
	public void setMonitorService(MonitorService monitorService) {
		this.monitorService = monitorService;
	}

	@Autowired
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}


	public void execute() {
		cleanMonitor();
		cleanHistory();
	}
	
	private void cleanMonitor()
	{
		String endTime = Commons.dateFormat(new Date(), "yyyy-MM-dd 00:00:00");
		int result = monitorService.deleteMonitor(endTime);
		if(result > 0)
		{
			System.out.println("监控数据清除完成");
		}
		else
		{
			System.out.println("监控数据清除失败");
		}
	}
	
	private void cleanHistory()
	{
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-6);
		String endTime = Commons.dateFormat(calendar.getTime(), "yyyy-MM-dd 00:00:00");
		int result = historyService.deleteHistory(endTime);
		if(result > 0)
		{
			System.out.println("历史数据清除完成");
		}
		else
		{
			System.out.println("历史数据清除失败");
		}
	}
	
	public static void main(String[] args)
	{
		new CleanJob().cleanMonitor();
	}
}
