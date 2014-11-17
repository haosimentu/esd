package com.gooseeker.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gooseeker.business.UserService;
import com.gooseeker.dao.beans.Authority;
import com.gooseeker.dao.beans.Menu;
import com.gooseeker.dao.beans.Userr;

public class UserDetailServiceImpl implements UserDetailsService {
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException
	{
		UserDetails user = Constants.USER_MAP.get(account);
		if(user == null)
		{
			Userr msUser = userService.findUserByAccount(account);
			if(msUser == null)
			{
				throw new UsernameNotFoundException("User name:" + account + " can't be found");
			}
			
			String password = msUser.getPassword();
			boolean enabled = msUser.isEnabled();
			boolean locked = msUser.isLocked();
			Calendar expiration = Calendar.getInstance();
			expiration.setTime(msUser.getExpiration());
			Calendar current = new GregorianCalendar();
			boolean notExpired = current.before(expiration) ? true : false;
			List<Authority> authorities = msUser.getAuthorities();
			List<SimpleGrantedAuthority> sgas = new ArrayList<SimpleGrantedAuthority>();
			for(Authority a : authorities)
			{
				SimpleGrantedAuthority sga = new SimpleGrantedAuthority(a.getRole());
				sgas.add(sga);
			}
			user = new SeekerUser(account, password, enabled, notExpired, notExpired, !locked, sgas,msUser.getGroup(),msUser.getId());
			
			List<Menu> menus = userService.findMenuByUserId(msUser.getId());
			((SeekerUser)user).setMenus(menus);
			((SeekerUser)user).setName(msUser.getName());
			Constants.USER_MAP.put(account, user);
		}
		else
		{
			if(user.getPassword() == null)
			{
				Constants.USER_MAP.remove(account);
				return loadUserByUsername(account);
			}
		}
		
		return user;
		
	}

}
