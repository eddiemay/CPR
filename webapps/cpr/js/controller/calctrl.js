var ONE_HOUR = 1000 * 60 * 60;
var ONE_DAY = ONE_HOUR * 24;

com.digitald4.cpr.CalCtrl = function($scope, $filter, TrainningService, ReservationService) {
	this.scope = $scope;
	this.dateFilter = $filter('date');
	this.trainningService = TrainningService;
	this.reservationService = ReservationService;
	this.refresh();
};

com.digitald4.cpr.CalCtrl.prototype.scope;

com.digitald4.cpr.CalCtrl.prototype.trainningService;
com.digitald4.cpr.CalCtrl.prototype.reservationService;

com.digitald4.cpr.CalCtrl.prototype.refresh = function() {
	this.trainningService.getSessions(this.selectedTrainningId, proto.common.DateRangeType.MONTH,
			this.getStartDate().toJSON(), this.setSessions.bind(this),
			function(error) {
				notify(error);
	});
};

com.digitald4.cpr.CalCtrl.prototype.getStartDate = function() {
	return new Date();
};

convertToEvent = function(session) {
	return {
    title: 'Open Session',
    start: new Date(session.start_time),
    end: new Date(session.startTime + session.duration_mins * 60000),
    className: ['session']
  };
};

com.digitald4.cpr.CalCtrl.prototype.setSessions = function(sessions) {
	this.sessions = sessions;
	var events = [];
	var protoSessions = [];
	angular.forEach(sessions, function(session) {
		events.push(convertToEvent(session));
		protoSessions.push(new proto.cpr.TrainningSessionUI(session));
	});
	this.events = events;
	this.scope.sessions = sessions;
	this.scope.protoSessions = protoSessions; // TODO(eddiemay) Remove this and next line once we get the allince working.
	this.scope.events = events;
	
	// this.scope.$apply();
};

com.digitald4.cpr.CalCtrl.prototype.selectSession = function(session) {
	this.session = session;
	this.reservationDialogVisible = true;
};

com.digitald4.cpr.CalCtrl.prototype.closeReservationDialog = function() {
	this.reservationDialogVisible = false;
};

/* com.digitald4.cpr.CalCtrl.prototype.updateBill = function(property) {
	var scope = this.scope;
	var tis = this;
	this.billUpdateError = undefined;
	this.billService.updateBill(this.eBill, property, this.sharedData.getSelectedPortfolioId(),
			com.digitald4.cpr.DisplayWindow.CAL_MONTH, this.billsSuccessCallback, function(error) {
		tis.billUpdateError = error;
		scope.$apply();
	});
}; */
