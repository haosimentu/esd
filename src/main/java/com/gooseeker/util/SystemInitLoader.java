package com.gooseeker.util;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContextEvent;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.web.context.ContextLoaderListener;

public class SystemInitLoader extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		String webroot = event.getServletContext().getRealPath("/");
        System.setProperty("webapp.root", webroot);
		// 加载所有资源与权限的关系
		List<ConfigAttribute> userAttrs = new ArrayList<ConfigAttribute>();
		userAttrs.add(new SecurityConfig("ROLE_USER"));
//		userAttrs.add(new SecurityConfig("ROLE_ENGINEER"));
//		userAttrs.add(new SecurityConfig("ROLE_ADMIN"));
		Constants.RESOURCE_MAP.put("/user/**", userAttrs);

		List<ConfigAttribute> adminAttrs = new ArrayList<ConfigAttribute>();
		adminAttrs.add(new SecurityConfig("ROLE_ENGINEER"));
//		adminAttrs.add(new SecurityConfig("ROLE_ADMIN"));
		Constants.RESOURCE_MAP.put("/engineer/**", adminAttrs);

		List<ConfigAttribute> vipAttrs = new ArrayList<ConfigAttribute>();
		vipAttrs.add(new SecurityConfig("ROLE_ADMIN"));
		Constants.RESOURCE_MAP.put("/admin/**", vipAttrs);

//		Executors.newScheduledThreadPool(1).schedule(new Runnable() {
//			public void run() {
//				while (Constants.SYSTEM_ASYN_NORMAL) 
//				{
//					try 
//					{
//						System.out.println("xxxxxxxxxxxxxxxxxxx");
//						Autotag at= Constants.AUTO_TAG_POOL.take();
//						Constants.THREAD_AUTO_TAG_POOL.execute(new AutotagThread(at));
////						System.out.println("ffffffffffffffffffff");
//					} catch (InterruptedException e1) {
//						
//					}
//				}
//			}
//		}, 60, TimeUnit.SECONDS);
//	}

//	class AutotagThread implements Runnable {
//		private Autotag autotag;
//
//		public AutotagThread(Autotag at) {
//			this.autotag = at;
//		}
//
//		@Override
//		public void run() {
//			//String dbName,String tableName,String fieldName,String tag
//			List<Tag> ts = autotag.getDao().queryContentByContent(autotag.getDbName(), autotag.getTableName(),autotag.getFieldName(), autotag.getTagName());
//			for (Tag t : ts) 
//			{
//				try
//				{
//					//String dbName,String tableName,long contentId,long tagnameId,byte manual,long userId
//					autotag.getDao().insertTag(autotag.getDbName(),autotag.getTagTableName(), t.getId(),autotag.getTagnameId(), autotag.getManual(),autotag.getUserId());
//				}
//				catch (Exception e) {
//					System.out.println(e.getMessage());
//				}
//			}
//		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		Constants.RESOURCE_MAP.clear();
	}

}
