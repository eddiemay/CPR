package com.digitald4.cpr.server;

import java.util.List;

import com.digitald4.common.distributed.Function;
import com.digitald4.common.distributed.MultiCoreThreader;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.common.storage.GenericDAOStore;
import com.digitald4.cpr.proto.CPRProtos.Trainning;
import com.digitald4.cpr.ui.proto.CPRUIProtos.GetTrainningRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ListTrainningsRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.TrainningUI;

public class TrainningService {
	private final GenericDAOStore<Trainning> store;
	private final MultiCoreThreader threader = new MultiCoreThreader();
	
	public final Function<TrainningUI, Trainning> converter =
			new Function<TrainningUI, Trainning>() {
		@Override
		public TrainningUI execute(Trainning trainning) {
			return TrainningUI.newBuilder()
					.setId(trainning.getId())
					.setName(trainning.getName())
					.setDescription(trainning.getDescription())
					.setDurationMins(trainning.getDurationMins())
					.build();
		}
	};
	
	public TrainningService(GenericDAOStore<Trainning> store) {
		this.store = store;
	}
	
	public TrainningUI get(GetTrainningRequest request) throws DD4StorageException {
		return converter.execute(store.get(request.getTrainningId()));
	}
	
	public List<TrainningUI> list(ListTrainningsRequest request)
			throws DD4StorageException {
		return threader.parDo(store.getAll(), converter);
	}
}
