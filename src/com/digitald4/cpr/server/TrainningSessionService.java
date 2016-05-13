package com.digitald4.cpr.server;

import java.util.List;

import org.joda.time.DateTime;

import com.digitald4.common.distributed.Function;
import com.digitald4.common.distributed.MultiCoreThreader;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.cpr.proto.CPRProtos.TrainningSession;
import com.digitald4.cpr.store.TrainningSessionStore;
import com.digitald4.cpr.ui.proto.CPRUIProtos.GetSessionRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.GetTrainningRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ListSessionsRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.TrainningSessionUI;

public class TrainningSessionService {
	private final MultiCoreThreader threader = new MultiCoreThreader();
	private final TrainningSessionStore store;
	private final TrainningService trainningService;
	
	public final Function<TrainningSessionUI, TrainningSession> converter =
			new Function<TrainningSessionUI, TrainningSession>() {
		@Override
		public TrainningSessionUI execute(TrainningSession session) {
			try {
				return TrainningSessionUI.newBuilder()
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
	
	public TrainningSessionService(TrainningSessionStore store, TrainningService trainningService) {
		this.store = store;
		this.trainningService = trainningService;
	}
	
	public TrainningSessionUI getSession(GetSessionRequest request) throws DD4StorageException {
		return converter.execute(store.read(request.getSessionId()));
	}
	
	public List<TrainningSessionUI> listSessions(ListSessionsRequest request)
			throws DD4StorageException {
		return threader.parDo(
				store.findSessions(request.getTrainningId(), request.getDateRange(),
						new DateTime(request.getRefDate())),
				converter);
	}
}
