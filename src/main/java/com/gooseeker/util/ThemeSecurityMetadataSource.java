package com.gooseeker.util;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
/**
 * 处理资源与权限的对应关系
 * 在初始化时，应该取到所有资源及其对应角色的定义
 * @author ysite
 *
 */
public class ThemeSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		
		FilterInvocation fi = (FilterInvocation)object;
//		HttpServletRequest request = fi.getHttpRequest();
		String url = fi.getRequestUrl();
//		String uri = request.getRequestURI();
//		int idx = uri.indexOf("/", 1);
//		String key = uri.substring(0,idx);
		Iterator<String> ite = Constants.RESOURCE_MAP.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (urlMatcher.pathMatchesUrl(resURL, url)) {
				return Constants.RESOURCE_MAP.get(resURL);
			}
		}
		
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}
