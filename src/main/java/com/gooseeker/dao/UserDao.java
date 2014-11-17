package com.gooseeker.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gooseeker.dao.beans.Userr;

public class UserDao extends Dao{
	private String GET= "getUser";
	private String LIST = "listUser";
	private String FIND_USER_ACCOUNT = "findUserByAccount";
	private String UPDATE_USERINFO = "updateUserinfo";
	private String INSERT_USERINFO = "insertUserinfo";
	private String FIND_USERS_4_PAGE = "findUsers4Page";
	private String FIND_USERS_4_PAGE_COUNT = "findUsers4PageCount";
	private String DELETE_USER = "deleteUser";
	
	public Userr getUserById(long userId) {
		return getMetacorpora().selectOne(GET, userId);
	}

	public List<Userr> getAllUsers() {
		return getMetacorpora().selectList(LIST);
	}
	
	public Userr findUserByAccount(String account)
	{
		return getMetacorpora().selectOne(FIND_USER_ACCOUNT, account);
	}
	/**
	 * 更新user信息，只能修改name、password、email、mobile
	 * @param name
	 * @param password
	 * @param email
	 * @param mobile
	 * @return
	 */
	public int updateUser(long id,String name,String password,String email,String mobile)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("id", id);
		parameterMap.put("name", name);
		parameterMap.put("password", password);
		parameterMap.put("email", email);
		parameterMap.put("mobile", mobile);
		
		return getMetacorpora().update(UPDATE_USERINFO, parameterMap);
	}
	/**
	 * 插入user表数据
	 * @param name
	 * @param account
	 * @param password
	 * @param email
	 * @param mobile
	 * @return
	 */
	public long insertUserinfo(String name,String account,String password,String email,String mobile)
	{
		long id = 0L;
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("name", name);
		parameterMap.put("account", account);
		parameterMap.put("password", password);
		parameterMap.put("email", email);
		parameterMap.put("mobile", mobile);
		int result = getMetacorpora().insert(INSERT_USERINFO, parameterMap);
		if(result > 0)
		{
			id = Long.parseLong(String.valueOf(parameterMap.get("ID")));
		}
		return id;
	}
	/**
	 * 分页查询用户
	 * @param start
	 * @param length
	 * @return
	 */
	public List<Userr> findUsers4Page(String keyword,int start,int length)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("keyword", keyword);
		parameterMap.put("start", start);
		parameterMap.put("length", length);
		
		return getMetacorpora().selectList(FIND_USERS_4_PAGE, parameterMap);
	}
	
	public int findUsers4PageCount(String keyword)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("keyword", keyword);
		return getMetacorpora().selectOne(FIND_USERS_4_PAGE_COUNT,parameterMap);
	}
	
	public int deleteUser(long id)
	{
		return getMetacorpora().delete(DELETE_USER,id);
	}
}
