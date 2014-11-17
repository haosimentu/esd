package com.gooseeker.util;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.gooseeker.dao.beans.Menu;

public class SeekerUser extends User{
	private static final long serialVersionUID = 1L;
	
	private long groupId;
	private long userId;
	private String password;
	private String name;
	private List<Menu> menus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SeekerUser(String account, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,long groupId,long userId) {
		super(account, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.groupId = groupId;
		this.userId = userId;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
}
