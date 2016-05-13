package com.digitald4.cpr.server;

import com.digitald4.common.distributed.Function;
import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.cpr.proto.CPRProtos.Reservation;
import com.digitald4.cpr.proto.CPRProtos.Reservation.Student;
import com.digitald4.cpr.store.ReservationStore;
import com.digitald4.cpr.ui.proto.CPRUIProtos.CreateReservationRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.GetReservationRequest;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ReservationUI;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ReservationUI.StudentUI;

public class ReservationService {
	private final ReservationStore store;
	
	public final Function<StudentUI, Student> studentConverter =
			new Function<StudentUI, Student>() {
		@Override
		public StudentUI execute(Student student) {
			return StudentUI.newBuilder()
					.setId(student.getId())
					.setName(student.getName())
					.setEmail(student.getEmail())
					.setResult(student.getResult())
					.build();
		}
	};
	
	public final Function<ReservationUI, Reservation> converter =
			new Function<ReservationUI, Reservation>() {
		@Override
		public ReservationUI execute(Reservation reservation) {
			if (reservation == null) {
				return null;
			}
			ReservationUI.Builder builder = ReservationUI.newBuilder()
					.setId(reservation.getId())
					.setSessionId(reservation.getSessionId())
					.setContactEmail(reservation.getContactEmail())
					.setConfirmationCode(reservation.getConfirmationCode())
					.setPaymentStatus(reservation.getPaymentStatus())
					.setPaymentConfirmationCode(reservation.getPaymentConfirmationCode());
			for (Student student : reservation.getStudentList()) {
				builder.addStudent(studentConverter.execute(student));
			}
			return builder.build();
		}
	};
	
	public final Function<Student, StudentUI> reverseStudentConverter =
			new Function<Student, StudentUI>() {
		@Override
		public Student execute(StudentUI student) {
			return Student.newBuilder()
					.setId(student.getId())
					.setName(student.getName())
					.setEmail(student.getEmail())
					.setResult(student.getResult())
					.build();
		}
	};
	
	public final Function<Reservation, ReservationUI> reverseConverter =
			new Function<Reservation, ReservationUI>() {
		@Override
		public Reservation execute(ReservationUI reservation) {
			if (reservation == null) {
				return null;
			}
			Reservation.Builder builder = Reservation.newBuilder()
					.setId(reservation.getId())
					.setSessionId(reservation.getSessionId())
					.setContactEmail(reservation.getContactEmail())
					.setConfirmationCode(reservation.getConfirmationCode())
					.setPaymentStatus(reservation.getPaymentStatus())
					.setPaymentConfirmationCode(reservation.getPaymentConfirmationCode());
			for (StudentUI student : reservation.getStudentList()) {
				builder.addStudent(reverseStudentConverter.execute(student));
			}
			return builder.build();
		}
	};
	
	public ReservationService(ReservationStore store) {
		this.store = store;
	}
	
	public ReservationUI createReservation(CreateReservationRequest request)
			throws DD4StorageException {
		return converter.execute(store.create(reverseConverter.execute(request.getReservation())));
	}
	
	public ReservationUI getReservation(GetReservationRequest request) throws DD4StorageException {
		return converter.execute(store.getBy(request.getEmail(), request.getConfirmationCode()));
	}
}
