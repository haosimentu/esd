package com.gooseeker.dao.beans;

public class Authority {
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ENTPR = "ROLE_ENTPR";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_VIP = "ROLE_VIP";
	
	private long id;
	private long userId;
	private String role;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
