package com.gooseeker.util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetails;

public final class Constants {
	//host url ,etc:www.yahoo.com,regxlib.com/Default.aspx 
	public static final String HOST_REGEX = "[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
	
	//用户缓存
	public static final Map<String, UserDetails> USER_MAP = new ConcurrentHashMap<String, UserDetails>(100);
	//资源url-role缓存
	public static final Map<String, Collection<ConfigAttribute>> RESOURCE_MAP = new ConcurrentHashMap<String, Collection<ConfigAttribute>>();
	
	public static final int PAGE_NUM = 6;
	
	public static final String HOST_CODE = "host";
	public static final String NAME_CODE = "name";
	
	public static final String DEFAULT_TAG_COMMA_HALF = ";";
	public static final String DEFAULT_TAG_COMMA_FULL = "；";
	
	public static final int INSERT_DB_ERROR = -1001;
	public static final int UPDATE_DB_ERROR = -1002;
	public static final int DELETE_DB_ERROR = -1003;
	public static final int CREATE_DB_ERROR = -1004;
	
	public static final ExecutorService THREAD_AUTO_TAG_POOL = Executors.newCachedThreadPool();
	public final static String DEFAULT_SPLIT_WORD = "\b";
	public final static String COMMON_SPLIT_WORD = "_";
	public final static String EXPORT_FILE_SUFFIX = ".xlsx";
	public final static int EXPORT_RECORD_NUM = 100000;

	public static final String CHINESE_SPLIT = "[。|！|？|：|；|，|.|!|?|:|,|\\s+]";
}
