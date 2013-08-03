package com.crowdriff.styx.http;

import com.crowdriff.styx.http.handlers.DefaultHandler;
import com.crowdriff.styx.http.handlers.GetHandler;
import com.crowdriff.styx.http.handlers.PutHandler;
import com.crowdriff.styx.http.handlers.SizeHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum StyxHttpServer {

	I;

	private static final Logger logger = LoggerFactory.getLogger(StyxHttpServer.class);
	private Server server;
	private static final int PORT = 8080;

	public void init() throws Exception {
		this.server = new org.eclipse.jetty.server.Server(PORT);
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(GetHandler.class, "/get/");
		handler.addServletWithMapping(PutHandler.class, "/put/");
		handler.addServletWithMapping(SizeHandler.class, "/size/");
		handler.addServletWithMapping(DefaultHandler.class, "/*");
		server.setHandler(handler);
		server.start();
		logger.info("StyxHttpServer up on port " + String.valueOf(PORT));
	}

	public void join() throws Exception {
		this.server.join();
	}

}
