package com.digital4.cpr.server;

import java.util.Collection;

import org.joda.time.DateTime;

import com.digital4.cpr.store.TrainningSessionStore;
import com.digitald4.common.distributed.Function;
import com.digitald4.common.distributed.MultiCoreThreader;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.cpr.proto.CPRProtos.TrainningSession;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ListSessionsRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.TrainningSessionUI;

public class TrainningSessionService {
	private final TrainningSessionStore store;
	private final MultiCoreThreader threader = new MultiCoreThreader();
	
	private Function<TrainningSessionUI, TrainningSession> converter =
			new Function<TrainningSessionUI, TrainningSession>() {
		@Override
		public TrainningSessionUI execute(TrainningSession session) {
			return TrainningSessionUI.newBuilder()
					.setId(session.getId())
					.setTrainningId(session.getTrainningId())
					.setStartTimeMillis(session.getStartTimeMillis())
					.setDurationMillis(session.getDurationMillis())
					.setCost(session.getCost())
					.setLocation(session.getLocation())
					.build();
		}
	};
	
	public TrainningSessionService(TrainningSessionStore store) {
		this.store = store;
	}
	
	public Collection<TrainningSessionUI> listSessions(ListSessionsRequest request)
			throws DD4StorageException {
		return threader.parDo(
				store.findSessions(request.getDateRange(), new DateTime(request.getRefDate())),
				converter);
	}
}
