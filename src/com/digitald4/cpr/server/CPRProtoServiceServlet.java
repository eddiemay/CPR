package com.digitald4.cpr.server;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.digitald4.common.dao.sql.DAOProtoSQLImpl;
import com.digitald4.common.jdbc.DBConnector;
import com.digitald4.common.server.ServiceServlet;
import com.digitald4.common.store.impl.GenericDAOStore;
import com.digitald4.cpr.dao.ReservationDualReadDAO;
import com.digitald4.cpr.proto.CPRProtos.Reservation;
import com.digitald4.cpr.proto.CPRProtos.Reservation.Student;
import com.digitald4.cpr.proto.CPRProtos.Trainning;
import com.digitald4.cpr.proto.CPRProtos.TrainningSession;
import com.digitald4.cpr.store.ReservationStore;
import com.digitald4.cpr.store.TrainningSessionStore;
import com.digitald4.cpr.ui.proto.CPRUIProtos.CreateReservationRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.GetReservationRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.GetSessionRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.GetTrainningRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ListSessionsRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ListTrainningsRequest;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;

@WebServlet(name = "CPR Proto Service Servlet", urlPatterns = {"/proto/*"}) 
public class CPRProtoServiceServlet extends ServiceServlet {
	public enum ACTIONS {TRAINNING, TRAINNINGS, SESSION, SESSIONS, CREATE_RESERVATION, RESERVATION};
	private TrainningService trainningService;
	private TrainningSessionService trainningSessionService;
	private ReservationService reservationService;

	public void init() throws ServletException {
		super.init();
		DBConnector dbConnector = getDBConnector();
		
		GenericDAOStore<Trainning> trainningStore = new GenericDAOStore<>(
				new DAOProtoSQLImpl<>(Trainning.getDefaultInstance(), dbConnector));
		trainningService = new TrainningService(trainningStore);
		
		TrainningSessionStore trainningSessionStore = new TrainningSessionStore(
				new DAOProtoSQLImpl<>(TrainningSession.getDefaultInstance(), dbConnector));
		trainningSessionService = new TrainningSessionService(trainningSessionStore, trainningService);
		
		ReservationStore reservationStore = new ReservationStore(new ReservationDualReadDAO(
				new DAOProtoSQLImpl<>(Reservation.getDefaultInstance(), dbConnector),
				new DAOProtoSQLImpl<>(Student.getDefaultInstance(), dbConnector)));
		reservationService = new ReservationService(reservationStore);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		try {
			JSONObject json = new JSONObject();
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			try {
				String action = request.getRequestURL().toString();
				action = action.substring(action.lastIndexOf("/") + 1).toUpperCase();
				switch (ACTIONS.valueOf(action)) {
					case TRAINNING: json.put("data", convertToJSON(trainningService.getTrainning(
							transformRequest(GetTrainningRequest.getDefaultInstance(), request))));
					break;
					case TRAINNINGS: json.put("data", convertToJSON(trainningService.listTrainnings(
							transformRequest(ListTrainningsRequest.getDefaultInstance(), request))));
					break;
					case SESSION: response.getWriter().println(trainningSessionService.getSession(
							transformRequest(GetSessionRequest.getDefaultInstance(), request)));
					break;
					case SESSIONS: json.put("data", convertToJSON(trainningSessionService.listSessions(
							transformRequest(ListSessionsRequest.getDefaultInstance(), request))));
					break;
					case CREATE_RESERVATION:
						json.put("data", convertToJSON(reservationService.createReservation(
								transformRequest(CreateReservationRequest.getDefaultInstance(), request))));
					break;
					case RESERVATION: json.put("data", convertToJSON(reservationService.getReservation(
							transformRequest(GetReservationRequest.getDefaultInstance(), request))));
					break;
				}
				json.put("valid", true);
			} catch (Exception e) {
				json.put("valid", false)
						.put("error", e.getMessage())
						.put("stackTrace", formatStackTrace(e))
						.put("requestParams", "" + request.getParameterMap().keySet())
						.put("queryString", request.getQueryString());
				e.printStackTrace();
			} finally {
				response.getWriter().println(json);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException {
		process(request, response);
	}
	
	public JSONArray convertToJSON(List<? extends Message> items) throws JSONException {
		JSONArray array = new JSONArray();
		for (Message item : items) {
			array.put(convertToJSON(item));
		}
		return array;
	}
	
	public JSONObject convertToJSON(Message item) throws JSONException {
		return new JSONObject(JsonFormat.printToString(item));
	}
}
