package com.gooseeker.dao.beans;

import java.util.List;

public class Menu {
	private long id;
	private String name;
	private String url;
	private String role;
	private String desc;
	private List<Menu> subMenus;
	
	public List<Menu> getSubMenus()
    {
        return subMenus;
    }
    public void setSubMenus(List<Menu> subMenus)
    {
        this.subMenus = subMenus;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
