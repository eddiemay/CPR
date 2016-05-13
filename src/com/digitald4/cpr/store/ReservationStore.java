package com.digitald4.cpr.store;

import java.util.List;

import com.digitald4.common.dao.DAO;
import com.digitald4.common.dao.QueryParam;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.common.store.impl.GenericDAOStore;
import com.digitald4.cpr.proto.CPRProtos.Reservation;

public class ReservationStore extends GenericDAOStore<Reservation> {
	
	public ReservationStore(DAO<Reservation> dao) {
		super(dao);
	}
	
	public Reservation getBy(String email, String confirmationCode)
			throws DD4StorageException {
		List<Reservation> results = query(new QueryParam("email", "=", email),
				new QueryParam("confirmation_code", "=", confirmationCode));
		return results.size() > 0 ? results.get(0) : null;
	}
}
