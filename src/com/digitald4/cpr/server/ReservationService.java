package com.digitald4.cpr.server;

import com.digitald4.common.exception.DD4StorageException;
import com.digitald4.cpr.proto.CPRProtos.Reservation;
import com.digitald4.cpr.proto.CPRProtos.Reservation.Student;
import com.digitald4.cpr.storage.ReservationStore;
import com.digitald4.cpr.proto.CPRUIProtos.CreateReservationRequest;
import com.digitald4.cpr.proto.CPRUIProtos.GetReservationRequest;
import com.digitald4.cpr.proto.CPRUIProtos.GetSessionRequest;
import com.digitald4.cpr.proto.CPRUIProtos.ReservationUI;
import com.digitald4.cpr.proto.CPRUIProtos.ReservationUI.StudentUI;

import java.util.function.Function;

public class ReservationService {
	private final ReservationStore store;
	private final SessionService sessionService;
	
	public final Function<Student, StudentUI> studentConverter =
			new Function<Student, StudentUI>() {
		@Override
		public StudentUI apply(Student student) {
			return StudentUI.newBuilder()
					.setName(student.getName())
					.setEmail(student.getEmail())
					.setResult(student.getResult())
					.build();
		}
	};
	
	public final Function<Reservation, ReservationUI> converter =
			new Function<Reservation, ReservationUI>() {
		@Override
		public ReservationUI apply(Reservation reservation) {
			if (reservation != null) {
				try {
					ReservationUI.Builder builder = ReservationUI.newBuilder()
							.setId(reservation.getId())
							.setSession(sessionService.get(
									GetSessionRequest.newBuilder()
									.setSessionId(reservation.getSessionId())
									.build()))
							.setContactEmail(reservation.getContactEmail())
							.setConfirmationCode(reservation.getConfirmationCode())
							.setPaymentStatus(reservation.getPaymentStatus())
							.setPaymentConfirmationCode(reservation.getPaymentConfirmationCode());
					for (Student student : reservation.getStudentList()) {
						builder.addStudent(studentConverter.apply(student));
					}
					return builder.build();
				} catch (DD4StorageException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	};
	
	public final Function<StudentUI, Student> reverseStudentConverter =
			new Function<StudentUI, Student>() {
		@Override
		public Student apply(StudentUI student) {
			return Student.newBuilder()
					.setName(student.getName())
					.setEmail(student.getEmail())
					.setResult(student.getResult())
					.build();
		}
	};
	
	public final Function<ReservationUI, Reservation> reverseConverter =
			new Function<ReservationUI, Reservation>() {
		@Override
		public Reservation apply(ReservationUI reservation) {
			if (reservation == null) {
				return null;
			}
			Reservation.Builder builder = Reservation.newBuilder()
					.setId(reservation.getId())
					.setSessionId(reservation.getSession().getId())
					.setContactEmail(reservation.getContactEmail())
					.setConfirmationCode(reservation.getConfirmationCode())
					.setPaymentStatus(reservation.getPaymentStatus())
					.setPaymentConfirmationCode(reservation.getPaymentConfirmationCode());
			for (StudentUI student : reservation.getStudentList()) {
				builder.addStudent(reverseStudentConverter.apply(student));
			}
			return builder.build();
		}
	};
	
	public ReservationService(ReservationStore store, SessionService sessionService) {
		this.store = store;
		this.sessionService = sessionService;
	}
	
	public ReservationUI create(CreateReservationRequest request)
			throws DD4StorageException {
		return converter.apply(store.create(reverseConverter.apply(request.getReservation())));
	}
	
	public ReservationUI get(GetReservationRequest request) throws DD4StorageException {
		return converter.apply(store.getBy(request.getEmail(), request.getConfirmationCode()));
	}
}
