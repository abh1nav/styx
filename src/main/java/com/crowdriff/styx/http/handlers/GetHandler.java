package com.crowdriff.styx.http.handlers;

import com.crowdriff.styx.Storage;
import com.crowdriff.styx.StyxMessage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GetHandler extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter writer = response.getWriter();
		StyxMessage styxMessage = Storage.I.get();
		writer.println((null != styxMessage) ? styxMessage.message : "{\n}");
	}

}