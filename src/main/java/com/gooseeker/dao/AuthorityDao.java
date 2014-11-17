package com.gooseeker.dao;

import com.gooseeker.dao.beans.Authority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorityDao extends Dao{
	private String LIST = "listAuthority";
	private String FIND_AUTHORITY_USERID = "findAuthoritiesByUserId";
	private String INSERT_AUTHORITY = "insertAuthority";
	private String DELETE_AUTHORITY_BY_USERID = "deleteAuthorityByUserId";
	
	public List<Authority> getAllAuthorities()
	{
		return getMetacorpora().selectList(LIST);
	}
	
	public List<Authority> findAuthoritiesByUserId(long userId)
	{
		return getMetacorpora().selectList(FIND_AUTHORITY_USERID,userId);
	}
	/**
	 * 
	 * @param userId
	 * @param role
	 * @return
	 */
	public long insertAuthority(long userId,String role)
	{
		long id = 0L;
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("userId", userId);
		parameterMap.put("role", role);
		
		int result = getMetacorpora().insert(INSERT_AUTHORITY, parameterMap);
		if(result > 0)
		{
			id = Long.parseLong(String.valueOf(parameterMap.get("ID")));
		}
		return id;
	}

	public int deleteAuthorityByUserId(long userId) {
		return getMetacorpora().delete(DELETE_AUTHORITY_BY_USERID,userId);
	}
}
