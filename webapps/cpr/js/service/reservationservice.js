com.digitald4.cpr.ReservationService = function(restService) {
	this.restService = restService;
};

com.digitald4.cpr.ReservationService.prototype.restService;

com.digitald4.cpr.ReservationService.prototype.getReservations = function(email, confirmationCode,
		successCallback, errorCallback) {
	this.restService.performRequest('reservation', {email: email, confirmation_code: confirmationCode},
			successCallback, errorCallback);
};

com.digitald4.cpr.ReservationService.prototype.createReservation = function(reservation,
		successCallback, errorCallback) {
	this.restService.performRequest('sessions', {reservation: reservation},
			successCallback, errorCallback);
};
