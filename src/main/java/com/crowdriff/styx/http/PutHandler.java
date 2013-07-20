package com.crowdriff.styx.http;

import com.crowdriff.styx.Storage;
import com.crowdriff.styx.StyxMessage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PutHandler extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		String message = request.getParameter("message");
		String isJson = request.getParameter("json");
		if(null != message) {
			StyxMessage styxMessage = (null != isJson) ? new StyxMessage(message, true) : new StyxMessage(message);
			Storage.I.put(styxMessage);
			response.getWriter().println("{\"status\": \"ok\"}");
		}
		else {
			response.getWriter().println("{\"error\": \"message cannot be null\"}");
		}
	}

}
