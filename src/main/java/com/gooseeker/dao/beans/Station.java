package com.gooseeker.dao.beans;

import java.util.Date;

public class Station {
	private long id;
	private String name;
	private long pipelineId;
	private String pipelineName;
//	private int number;
	private Integer number;
	private int state;
	private String address;
	private String subAddress;
	private Date createTime;
	private String desc;
	
	public String getSubAddress() {
		return subAddress;
	}
	public void setSubAddress(String subAddress) {
		this.subAddress = subAddress;
	}
	public String getPipelineName() {
		return pipelineName;
	}
	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPipelineId() {
		return pipelineId;
	}
	public void setPipelineId(long pipelineId) {
		this.pipelineId = pipelineId;
	}
	public Integer getNumber()
    {
        return number;
    }
    public void setNumber(Integer number)
    {
        this.number = number;
    }
    public int getState()
    {
        return state;
    }
    public void setState(int state)
    {
        this.state = state;
    }
    public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
