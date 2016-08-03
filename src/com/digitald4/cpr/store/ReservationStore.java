package com.digitald4.cpr.store;

import java.util.List;
import java.util.Random;

import com.digitald4.common.dao.DAO;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.common.proto.DD4UIProtos.ListRequest.QueryParam;
import com.digitald4.common.store.impl.GenericDAOStore;
import com.digitald4.cpr.proto.CPRProtos.Reservation;

public class ReservationStore extends GenericDAOStore<Reservation> {
	
	private final Random random;
	
	public ReservationStore(DAO<Reservation> dao, Random random) {
		super(dao);
		this.random = random;
	}
	
	public Reservation getBy(String email, String confirmationCode) throws DD4StorageException {
		List<Reservation> results = get(
				QueryParam.newBuilder().setColumn("email").setOperan("=").setValue(email).build(),
				QueryParam.newBuilder().setColumn("confirmation_code").setOperan("=").setValue(confirmationCode).build());
		return results.size() > 0 ? results.get(0) : null;
	}
	
	@Override
	public Reservation create(Reservation reservation) throws DD4StorageException {
		return super.create(reservation.toBuilder()
				.setConfirmationCode(createConfirmationCode())
				.build());
	}
	
	private String createConfirmationCode() {
		byte[] bytes = new byte[10];
		random.nextBytes(bytes);
		return bytes.toString();
	}
}
