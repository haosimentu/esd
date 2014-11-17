package com.gooseeker.dao.beans;

import java.util.Date;

public class Monitor {
	private long id;
	private long pipelineId;
	private String pipelineName;
	private long stationId;
	private String stationName;
	private Date monitorTime;
	private int result;
	private int duration;
	
	private int durations;//归类后的值
	
	public int getDurations() {
		return durations;
	}
	public void setDurations(int durations) {
		this.durations = durations;
	}
	public String getPipelineName() {
		return pipelineName;
	}
	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStationId() {
		return stationId;
	}
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Date getMonitorTime() {
		return monitorTime;
	}
	public void setMonitorTime(Date monitorTime) {
		this.monitorTime = monitorTime;
	}
	public long getPipelineId() {
		return pipelineId;
	}
	public void setPipelineId(long pipelineId) {
		this.pipelineId = pipelineId;
	}
}
