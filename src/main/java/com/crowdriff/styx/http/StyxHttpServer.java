package com.crowdriff.styx.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public enum StyxHttpServer {

	I;

	Server server;

	public void init() throws Exception {
		this.server = new org.eclipse.jetty.server.Server(8080);
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(GetHandler.class, "/get/");
		handler.addServletWithMapping(PutHandler.class, "/put/");
		handler.addServletWithMapping(DefaultHandler.class, "/*");
		server.setHandler(handler);
		server.start();
	}

	public void join() throws Exception {
		this.server.join();
	}

}
