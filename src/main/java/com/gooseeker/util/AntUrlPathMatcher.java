package com.gooseeker.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class AntUrlPathMatcher implements UrlMatcher {
	private boolean requiresLowerCaseUrl;
	private PathMatcher pathMatcher;
	
	public AntUrlPathMatcher()
	{
		this(true);
	}
	
	public AntUrlPathMatcher(boolean requiresLowerCaseUrl)
	{
		this.pathMatcher = new AntPathMatcher();
		this.requiresLowerCaseUrl = requiresLowerCaseUrl;
	}
	
	@Override
	public Object compile(String path) {
		if (this.requiresLowerCaseUrl)
		{
			return path.toLowerCase();
		}
		return path;
	}

	@Override
	public boolean pathMatchesUrl(Object path, String url) {
		if (("/**".equals(path)) || ("**".equals(path))) {
			return true;
		}
		return this.pathMatcher.match((String)path, url);
	}

	@Override
	public String getUniversalMatchPattern() {
		return"/**";
	}

	@Override
	public boolean requiresLowerCaseUrl() {
		return this.requiresLowerCaseUrl;
	}

}
