package com.digitald4.cpr.store;

import com.digitald4.common.dao.DAO;
import com.digitald4.common.dao.QueryParam;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.common.proto.DD4UIProtos.DateRangeType;
import com.digitald4.common.store.impl.GenericDAOStore;
import com.digitald4.cpr.proto.CPRProtos.TrainningSession;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class TrainningSessionStore extends GenericDAOStore<TrainningSession> {
	
	public TrainningSessionStore(DAO<TrainningSession> dao) {
		super(dao);
	}
	
	public List<TrainningSession> findSessions(int trainningId, DateRangeType rangeType,
			DateTime refDate) throws DD4StorageException {
		refDate = refDate.minusMillis(refDate.getMillisOfDay());
		DateTime startDate = null, endDate = null;
		switch (rangeType) {
			case DAY:
				startDate = refDate;
				// Set End Date to the next day
				endDate = startDate.plusDays(1);
				break;
			case WEEK:
				// Set start to first day of week
				startDate = refDate.minusDays(refDate.getDayOfWeek());
				// Set End Date to the first day of the next week
				endDate = startDate.plusDays(7);
				break;
			case MONTH:
				// Set start to first day of month
				startDate = refDate.minusDays(refDate.getDayOfMonth() - 1);
				// Set End Date to the first day of the next month
				endDate = startDate.plusMonths(1);
				break;
			case YEAR:
				// Set start to first day of year
				startDate = refDate.minusDays(refDate.getDayOfYear() - 1);
				// Set End Date to the first day of the next year
				endDate = startDate.plusYears(1);
				break;
			case UNSPECIFIED:
				throw new DD4StorageException("Unimplemented date range type: " + rangeType);
		}
		List<QueryParam> params = new ArrayList<>();
		if (trainningId > 0) {
			params.add(new QueryParam("trainning_id", "=", trainningId));
		}
		if (startDate != null) {
			params.add(new QueryParam("start_time", ">=", startDate.getMillis()));
			params.add(new QueryParam("start_time", "<", endDate.getMillis()));
		}
		return query(params);
	}
}
