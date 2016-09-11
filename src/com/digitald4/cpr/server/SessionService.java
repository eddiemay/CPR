package com.digitald4.cpr.server;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.digitald4.common.storage.DAOStore;

import com.digitald4.common.distributed.Function;
import com.digitald4.common.proto.DD4UIProtos.ListRequest.QueryParam;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.cpr.proto.CPRProtos.Session;
import com.digitald4.cpr.proto.CPRUIProtos.GetSessionRequest;
import com.digitald4.cpr.proto.CPRUIProtos.GetTrainningRequest;
import com.digitald4.cpr.proto.CPRUIProtos.ListSessionsRequest;
import com.digitald4.cpr.proto.CPRUIProtos.SessionUI;

public class SessionService {
	private final DAOStore<Session> store;
	private final TrainningService trainningService;
	
	public final Function<Session, SessionUI> converter = new Function<Session, SessionUI>() {
		@Override
		public SessionUI apply(Session session) {
			try {
				return SessionUI.newBuilder()
						.setId(session.getId())
						.setTrainningId(session.getTrainningId())
						.setStartTime(session.getStartTime())
						.setDurationMins(session.getDurationMins())
						.setCost(session.getCost())
						.setLocation(session.getLocation())
						.setTrainning(trainningService.get(
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
	
	public SessionService(DAOStore<Session> store, TrainningService trainningService) {
		this.store = store;
		this.trainningService = trainningService;
	}
	
	public SessionUI get(GetSessionRequest request) throws DD4StorageException {
		return converter.apply(store.get(request.getSessionId()));
	}
	
	public List<SessionUI> list(ListSessionsRequest request)
			throws DD4StorageException {

		List<QueryParam> params = new ArrayList<>();
		if (request.hasTrainningId()) {
			params.add(QueryParam.newBuilder().setColumn("trainning_id")
					.setOperan("=").setValue(String.valueOf(request.getTrainningId())).build());
		}
		params.add(QueryParam.newBuilder().setColumn("start_time")
				.setOperan(">=").setValue(String.valueOf(request.getStartDate())).build());
		params.add(QueryParam.newBuilder().setColumn("start_time")
				.setOperan("<").setValue(String.valueOf(request.getEndDate())).build());
		return store.get(params).stream().map(converter).collect(Collectors.toList());
	}
}
