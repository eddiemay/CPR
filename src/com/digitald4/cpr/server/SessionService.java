package com.digitald4.cpr.server;

import java.util.List;

import org.joda.time.DateTime;

import com.digitald4.common.distributed.Function;
import com.digitald4.common.distributed.MultiCoreThreader;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.cpr.proto.CPRProtos.Session;
import com.digitald4.cpr.store.SessionStore;
import com.digitald4.cpr.ui.proto.CPRUIProtos.GetSessionRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.GetTrainningRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ListSessionsRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.SessionUI;

public class SessionService {
	private final MultiCoreThreader threader = new MultiCoreThreader();
	private final SessionStore store;
	private final TrainningService trainningService;
	
	public final Function<SessionUI, Session> converter = new Function<SessionUI, Session>() {
		@Override
		public SessionUI execute(Session session) {
			try {
				return SessionUI.newBuilder()
						.setId(session.getId())
						.setTrainningId(session.getTrainningId())
						.setStartTime(session.getStartTime())
						.setDurationMins(session.getDurationMins())
						.setCost(session.getCost())
						.setLocation(session.getLocation())
						.setTrainning(trainningService.getTrainning(
								GetTrainningRequest.newBuilder()
										.setTrainningId(session.getTrainningId())
										.build()))
						.build();
			} catch (DD4StorageException e) {
				e.printStackTrace();
			}
			return null;
		}
	};
	
	public SessionService(SessionStore store, TrainningService trainningService) {
		this.store = store;
		this.trainningService = trainningService;
	}
	
	public SessionUI getSession(GetSessionRequest request) throws DD4StorageException {
		return converter.execute(store.read(request.getSessionId()));
	}
	
	public List<SessionUI> listSessions(ListSessionsRequest request)
			throws DD4StorageException {
		return threader.parDo(
				store.findSessions(request.getTrainningId(), request.getDateRange(),
						new DateTime(request.getRefDate())),
				converter);
	}
}
