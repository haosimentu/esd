package com.gooseeker.dao.beans;

import java.util.List;

public class Summary {
	private long pipelineId;
	private String pipelineName;
	private List<Overview> overviews;
	private List<Monitor> monitors;

	public long getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(long pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public List<Monitor> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<Monitor> monitors) {
		this.monitors = monitors;
	}

	public List<Overview> getOverviews() {
		return overviews;
	}

	public void setOverviews(List<Overview> overviews) {
		this.overviews = overviews;
	}
}
