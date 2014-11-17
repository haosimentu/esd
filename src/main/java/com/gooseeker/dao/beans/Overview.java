package com.gooseeker.dao.beans;

public class Overview {
	private int result;
	private int durations;
	public Overview(int result,int durations)
	{
		this.result = result;
		this.durations = durations;
	}
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getDurations() {
		return durations;
	}
	public void setDurations(int durations) {
		this.durations = durations;
	}
	
	
}
