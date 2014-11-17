package com.gooseeker.business;

import java.util.List;

import com.gooseeker.dao.beans.Menu;
import com.gooseeker.dao.beans.Userr;

public interface UserService {
	boolean userLogin(String userName,String password);
	List<Userr> listAllUsers();
	
	List<Userr> findUsers4Page(String keyword,int start,int length);
	int findUsers4PageCount(String keyword);
	Userr findUserByAccount(String account);
	
	List<Menu> findMenuByUserId(long userId);
	
	long insertUser(String name,String account,String password,String email,String mobile);
	long insertUserWithRole(String name,String account,String password,String email,String mobile,List<String> roles);
	
	int deleteUserById(long id);
	int updateUserById(long id,String name,String password,String email,String mobile);
	int updateUserWithRole(long id,String name,String password,String email,String mobile,List<String> roles);
	Userr getUserById(long userId);
}
