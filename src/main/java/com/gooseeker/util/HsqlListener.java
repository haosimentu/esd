package com.gooseeker.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl.AclFormatException;

public class HsqlListener implements ServletContextListener {
	private Server server = null;
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if(null != server)
		{
			server.stop();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Server server = new Server();
		HsqlProperties properties = new HsqlProperties();
		properties.setProperty("server.database.0", "file:"+System.getProperty("webapp.root")+File.separator+"esddb");
		properties.setProperty("server.dbname.0", "esddb");
		properties.setProperty("server.port", "9101");
		properties.setProperty("server.daemon", "true");
		properties.setProperty("server.remote_open", "true");
		try 
		{
			server.setProperties(properties);
			server.setSilent(true);
			server.setTrace(true);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AclFormatException e) {
			e.printStackTrace();
		}
	}

}
