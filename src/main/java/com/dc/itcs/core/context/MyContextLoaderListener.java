package com.dc.itcs.core.context;

import javax.servlet.ServletContextEvent;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.dc.flamingo.tools.quartz.SingleQuartzManager;

public class MyContextLoaderListener extends ContextLoaderListener {
	private static final Logger log = LoggerFactory.getLogger(MyContextLoaderListener.class);

	public MyContextLoaderListener() {
	}

	public MyContextLoaderListener(WebApplicationContext context) {
		super(context);
	}

	/**
	 * Initialize the root web application context.
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		initWebApplicationContext(event.getServletContext());
		log.info("=======项目启动=====");
		try {
			SingleQuartzManager.startAll();
		} catch (SchedulerException e) {
			log.error("=======定时任务初始化失败=====");
		}
	}

	/**
	 * Close the root web application context.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}

}
