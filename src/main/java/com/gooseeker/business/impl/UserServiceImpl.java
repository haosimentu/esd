package com.gooseeker.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gooseeker.business.UserService;
import com.gooseeker.dao.AuthorityDao;
import com.gooseeker.dao.MenuDao;
import com.gooseeker.dao.UserDao;
import com.gooseeker.dao.beans.Menu;
import com.gooseeker.dao.beans.Userr;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao;
	private MenuDao menuDao;
	private AuthorityDao authorityDao;
	@Autowired
	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Autowired
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public boolean userLogin(String userName, String password) {
		List<Userr> userList = userDao.getAllUsers();
		return userList.size() >= 1;
	}

	@Override
	public Userr findUserByAccount(String account) {
		return userDao.findUserByAccount(account);
	}

	@Override
	public List<Menu> findMenuByUserId(long userId) {
		return menuDao.findMenuByUserId(userId);
	}
	@Override
	public long insertUser(String name, String account, String password,
			String email, String mobile) {
		return userDao.insertUserinfo(name, account, password, email, mobile);
	}
	@Override
	public long insertUserWithRole(String name, String account,
			String password, String email, String mobile, List<String> roles) {
		long userId = userDao.insertUserinfo(name, account, password, email, mobile);
		for(String role : roles)
		{
			long id = authorityDao.insertAuthority(userId, role);
			if(id <= 0)
			{
				System.out.println("error ... ");
			}
		}
		return userId;
	}
	@Override
	public List<Userr> listAllUsers() {
		return userDao.getAllUsers();
	}
	@Override
	public List<Userr> findUsers4Page(String keyword,int start, int length) {
		return userDao.findUsers4Page(keyword,start, length);
	}
	@Override
	public int findUsers4PageCount(String keyword) {
		return userDao.findUsers4PageCount(keyword);
	}
	@Override
	public int deleteUserById(long id) {
		return userDao.deleteUser(id);
	}
	@Override
	public int updateUserById(long id, String name, String password,String email, String mobile) {
		return userDao.updateUser(id, name, password, email, mobile);
	}
	@Override
	public int updateUserWithRole(long id, String name, String password,
			String email, String mobile, List<String> roles) {
		int rows = userDao.updateUser(id, name, password, email, mobile);
		authorityDao.deleteAuthorityByUserId(id);
		for(String role : roles)
		{
			authorityDao.insertAuthority(id, role);
		}
		return rows;
	}
	@Override
	public Userr getUserById(long userId) {
		return userDao.getUserById(userId);
	}
}
