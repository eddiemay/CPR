package com.digital4.cpr.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.digital4.cpr.store.TrainningSessionStore;
import com.digitald4.common.dao.sql.DAOProtoSQLImpl;
import com.digitald4.common.servlet.ParentServlet;
import com.digitald4.cpr.proto.CPRProtos.TrainningSession;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ListSessionsRequest;

public class ServiceServlet extends ParentServlet {
	public enum ACTIONS {listSessions};
	private TrainningSessionService trainningSessionService;

	public void init() throws ServletException {
		super.init();
		TrainningSessionStore trainningSessionStore = new TrainningSessionStore(
				new DAOProtoSQLImpl<>(TrainningSession.getDefaultInstance(), getDBConnector()));
		trainningSessionService = new TrainningSessionService(trainningSessionStore);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		try {
			JSONObject json = new JSONObject();
			try {
				if (!checkLoginAutoRedirect(request, response)) return;
				String action = request.getParameter("action");
				switch (ACTIONS.valueOf(action)) {
					case listSessions: json.put("data", trainningSessionService.listSessions(
							transformRequest(ListSessionsRequest.getDefaultInstance(), request)));
					break;
				}
				json.put("valid", true);
			} catch (Exception e) {
				json.put("valid", false).put("error", e.getMessage()).put("stackTrace", formatStackTrace(e));
				e.printStackTrace();
			} finally {
				response.setContentType("application/json");
				response.setHeader("Cache-Control", "no-cache, must-revalidate");
				response.getWriter().println(json);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
