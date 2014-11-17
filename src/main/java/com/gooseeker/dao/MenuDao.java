package com.gooseeker.dao;

import com.gooseeker.dao.beans.Menu;

import java.util.List;

public class MenuDao extends Dao{
	private String LIST = "listMenu";
	private String FIND_MENU_BY_ROLE = "findMenuByRole";
	private String FIND_MENU_BY_USERID = "findMenuByUserId";
	
	public List<Menu> getAllAuthorities()
	{
		return getMetacorpora().selectList(LIST);
	}
	
	public List<Menu> findMenuByRole(String role)
	{
		return getMetacorpora().selectList(FIND_MENU_BY_ROLE,role);
	}
	
	public List<Menu> findMenuByUserId(long userId)
	{
		return getMetacorpora().selectList(FIND_MENU_BY_USERID,userId);
	}
}
