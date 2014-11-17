package com.gooseeker.util;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class ThemeAccessDecisionManager implements AccessDecisionManager{

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null) 
		{
			return;
		}
		
		//configAttributes资源需要的权限,security.xml中配置的资源权限
		Iterator<ConfigAttribute> it = configAttributes.iterator();
		while(it.hasNext())
		{
			ConfigAttribute configAttribute = it.next();
			String needPermission = configAttribute.getAttribute();
			//System.out.println("needPermission is " + needPermission);
			//authentication记录用户所拥有的权限
			for(GrantedAuthority ga : authentication.getAuthorities())
			{
				if(needPermission.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		
		throw new AccessDeniedException("access deny ");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
