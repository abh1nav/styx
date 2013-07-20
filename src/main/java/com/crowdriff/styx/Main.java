package com.crowdriff.styx;

import com.crowdriff.styx.http.StyxHttpServer;

public class Main {

	public static void main(String[] args) throws Exception {
		StyxHttpServer.I.init();
		StyxHttpServer.I.join();
	}

}
