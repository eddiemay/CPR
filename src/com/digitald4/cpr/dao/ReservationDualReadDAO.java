package com.digitald4.cpr.dao;

import java.util.ArrayList;
import java.util.List;

import com.digitald4.common.dao.DAO;
import com.digitald4.common.dao.QueryParam;
import com.digitald4.common.distributed.Function;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.cpr.proto.CPRProtos.Reservation;
import com.digitald4.cpr.proto.CPRProtos.Reservation.Student;

public class ReservationDualReadDAO implements DAO<Reservation> {

	private final DAO<Reservation> reservationDAO;
	private final DAO<Student> studentDAO;
	
	public ReservationDualReadDAO(DAO<Reservation> reservationDAO, DAO<Student> studentDAO) {
		this.reservationDAO = reservationDAO;
		this.studentDAO = studentDAO;
	}
	
	@Override
	public Reservation create(Reservation reservation) throws DD4StorageException {
		Reservation.Builder builder =
				reservationDAO.create(reservation.toBuilder().clearStudent().build()).toBuilder();
		for (Student student : reservation.getStudentList()) {
			builder.addStudent(
					studentDAO.create(student.toBuilder().setReservationId(builder.getId()).build()));
		}
		return builder.build();
	}

	@Override
	public Reservation read(int id) throws DD4StorageException {
		return addStudents(reservationDAO.read(id));
	}

	@Override
	public Reservation update(int id, Function<Reservation, Reservation> updater)
			throws DD4StorageException {
		return addStudents(reservationDAO.update(id, updater));
	}

	@Override
	public void delete(int id) throws DD4StorageException {
		reservationDAO.delete(id);
	}

	@Override
	public List<Reservation> query(QueryParam... params) throws DD4StorageException {
		List<Reservation> results = new ArrayList<>();
		for (Reservation reservation : reservationDAO.query(params)) {
			results.add(addStudents(reservation));
		}
		return results;
	}

	@Override
	public List<Reservation> query(List<QueryParam> params) throws DD4StorageException {
		List<Reservation> results = new ArrayList<>();
		for (Reservation reservation : reservationDAO.query(params)) {
			results.add(addStudents(reservation));
		}
		return results;
	}

	@Override
	public List<Reservation> getAll() throws DD4StorageException {
		List<Reservation> results = new ArrayList<>();
		for (Reservation reservation : reservationDAO.getAll()) {
			results.add(addStudents(reservation));
		}
		return results;
	}
	
	private Reservation addStudents(Reservation reservation) throws DD4StorageException {
		if (reservation == null) {
			return null;
		}
		Reservation.Builder builder = reservation.toBuilder();
		for (Student student : studentDAO.query(new QueryParam("reservation_id", "=", reservation.getId()))) {
			builder.addStudent(student);
		}
		return builder.build();
	}
}
