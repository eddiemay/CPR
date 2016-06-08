var ONE_HOUR = 1000 * 60 * 60;
var ONE_DAY = ONE_HOUR * 24;

com.digitald4.cpr.CalCtrl = function($scope, $compile, $mdDialog, trainningService) {
	this.mdDialog = $mdDialog;
	this.trainningService = trainningService;
	
	var eventClicked = function(event, jsEvent, view) {
		this.setSelected(event.session);
	}.bind(this);

  /* Render Tooltip */
 var eventRender = function(event, element, view) { 
   element.attr({'tooltip': event.title,
                'tooltip-append-to-body': true});
   $compile(element)($scope);
 };
 
 var viewRender = function(view, element) {
	 this.refresh(view.intervalStart);
 }.bind(this);
	
	this.uiConfig = {
    calendar:{
      height: 450,
      editable: false,
      header:{
        left: 'title',
        center: '',
        right: 'today prev,next'
      },
      eventClick: eventClicked,
      eventRender: eventRender,
      viewRender: viewRender
    }
  };
	this.eventSources = [this.events];
	// this.refresh();
};

convertToEvent = function(session) {
	return {
		id: session.id,
    title: session.trainning.name,
    start: new Date(session.start_time),
    end: new Date(session.start_time + session.duration_mins * 60000),
    session: session,
    className: ['session']
  };
};

com.digitald4.cpr.CalCtrl.prototype.trainningService;

com.digitald4.cpr.CalCtrl.prototype.sessions = [];
com.digitald4.cpr.CalCtrl.prototype.events = [
    convertToEvent({ id: 100,
		  trainning: {name: 'Test Event'},
		  start_time: new Date().getTime(),
		  duration_mins: 60}),
		convertToEvent({ id: 200,
			trainning: {name: 'Test Event 2'},
		  start_time: new Date().getTime(),
		  duration_mins: 60})
];

com.digitald4.cpr.CalCtrl.prototype.refresh = function(startDate) {
	this.trainningService.getSessions(this.selectedTrainningId, proto.common.DateRangeType.MONTH,
			startDate.toJSON(), this.setSessions.bind(this),
			function(error) {
				notify(error);
	});
};

com.digitald4.cpr.CalCtrl.prototype.setSessions = function(sessions) {
	this.sessions = sessions;
	this.events.length = 0;
	angular.forEach(sessions, function(session) {
		this.events.push(convertToEvent(session));
	}.bind(this));
};

com.digitald4.cpr.CalCtrl.prototype.setSelected = function(session) {
	this.selectedSession = session;
	console.log('Selected session: ' + session.id + " " + session.trainning.name);
	this.showReservationDialog();
};

com.digitald4.cpr.CalCtrl.prototype.closeReservationDialog = function() {
	this.reservationDialogVisible = false;
};

com.digitald4.cpr.CalCtrl.prototype.showReservationDialog = function(ev) {
  this.mdDialog.show({
    controller: DialogController,
    templateUrl: 'html/reservation-dialog.tmpl.html',
    parent: angular.element(document.body),
    targetEvent: ev,
    clickOutsideToClose:true,
    locals: {session: this.selectedSession}
  }).then(function(reservation) {
  }, function() {
  });
};

var student = {};
var reservation = {
		student: []
};

function DialogController($scope, $mdDialog, session, reservationService) {
	reservation.session = session;
	$scope.reservation = reservation;
	$scope.student = student;
	
  $scope.hide = function() {
    $mdDialog.hide();
  };
  
  $scope.cancel = function() {
    $mdDialog.cancel();
  };
  
  $scope.add = function() {
  	if (!student.added) {
	  	student.added = true;
	  	reservation.student.push(student);
  	}
    $scope.student = student = {};
  };
  
  $scope.remove = function(student_) {
  	reservation.student.splice(reservation.student.indexOf(student_), 1);
  	if (student = student_) {
  		$scope.student = student = {};
  	}
  };
  
  $scope.select = function(student_) {
  	$scope.student = student = student_;
  };
  
  $scope.submit = function() {
  	reservationService.createReservation(reservation, function(reservation_) {
  		$scope.reservation = reservation = reservation_;
  		// $mdDialog.hide(reservation);
  	}, function(error) {
  		
  	});
  };
}
