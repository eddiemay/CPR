package com.digitald4.cpr.server;

import java.util.List;
import java.util.stream.Collectors;

import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.common.storage.DAOStore;
import com.digitald4.cpr.proto.CPRProtos.Trainning;
import com.digitald4.cpr.proto.CPRUIProtos.GetTrainningRequest;
import com.digitald4.cpr.proto.CPRUIProtos.ListTrainningsRequest;
import com.digitald4.cpr.proto.CPRUIProtos.TrainningUI;

import java.util.function.Function;

public class TrainningService {
	private final DAOStore<Trainning> store;

	public final Function<Trainning, TrainningUI> converter =
			new Function<Trainning, TrainningUI>() {
		@Override
		public TrainningUI apply(Trainning trainning) {
			return TrainningUI.newBuilder()
					.setId(trainning.getId())
					.setName(trainning.getName())
					.setDescription(trainning.getDescription())
					.setDurationMins(trainning.getDurationMins())
					.build();
		}
	};
	
	public TrainningService(DAOStore<Trainning> store) {
		this.store = store;
	}
	
	public TrainningUI get(GetTrainningRequest request) throws DD4StorageException {
		return converter.apply(store.get(request.getTrainningId()));
	}
	
	public List<TrainningUI> list(ListTrainningsRequest request)
			throws DD4StorageException {
		return store.getAll().stream().map(converter).collect(Collectors.toList());
	}
}
