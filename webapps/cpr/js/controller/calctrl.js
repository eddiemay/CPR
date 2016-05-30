var ONE_HOUR = 1000 * 60 * 60;
var ONE_DAY = ONE_HOUR * 24;

com.digitald4.cpr.CalCtrl = function($scope, $compile, $mdDialog, trainningService, reservationService) {
	this.mdDialog = $mdDialog;
	this.trainningService = trainningService;
	this.reservationService = reservationService;

  /* Render Tooltip */
 var eventRender = function(event, element, view) { 
   element.attr({'tooltip': event.title,
                'tooltip-append-to-body': true});
   $compile(element)($scope);
 };
	
	this.uiConfig = {
    calendar:{
      height: 450,
      editable: true,
      header:{
        left: 'title',
        center: '',
        right: 'today prev,next'
      },
      eventClick: this.selectSession.bind(this),
      eventRender: eventRender
    }
  };
	this.eventSources = [this.events];
	this.refresh();
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
com.digitald4.cpr.CalCtrl.prototype.reservationService;

com.digitald4.cpr.CalCtrl.prototype.sessions = [];
com.digitald4.cpr.CalCtrl.prototype.events = [convertToEvent(
    { id: 100,
		  trainning: {name: 'Test Event'},
		  start_time: new Date().getTime(),
		  duration_mins: 60}),
		convertToEvent({ id: 200,
			trainning: {name: 'Test Event 2'},
		  start_time: new Date().getTime(),
		  duration_mins: 60})
];

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

com.digitald4.cpr.CalCtrl.prototype.setSessions = function(sessions) {
	this.sessions = sessions;
	this.events.length = 0;
	angular.forEach(sessions, function(session) {
		this.events.push(convertToEvent(session));
	}.bind(this));
};

com.digitald4.cpr.CalCtrl.prototype.selectSession = function(event, jsEvent, view) {
	if (event) {
		this.selectedSession = event.session;
		console.log('Selected session: ' + event.session.id + " " + event.session.trainning.name);
		this.showReservationDialog(jsEvent);
	}
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


com.digitald4.cpr.CalCtrl.prototype.showReservationDialog = function(ev) {
  this.mdDialog.show({
    controller: DialogController,
    templateUrl: 'html/reservation-dialog.tmpl.html',
    parent: angular.element(document.body),
    targetEvent: ev,
    clickOutsideToClose:true,
    locals: {session: this.selectedSession}
  }).then(function(answer) {
    console.log('You said the information was "' + answer + '".');
  }, function() {
    console.log('You cancelled the dialog.');
  });
};

var selectedStudent = {};
var students = [];

function DialogController($scope, $mdDialog, session, reservationService) {
	$scope.session = session;
	$scope.student = selectedStudent;
	$scope.students = students;
	this.reservationService = reservationService;
	
  $scope.hide = function() {
    $mdDialog.hide();
  };
  
  $scope.cancel = function() {
    $mdDialog.cancel();
  };
  
  $scope.add = function() {
  	if (!$scope.student.added) {
	  	$scope.student.added = true;
	    $scope.students.push($scope.student);
  	}
    $scope.student = selectedStudent = {};
  };
  
  $scope.remove = function(student) {
  	$scope.students.splice($scope.students.indexOf(student), 1);
  	if ($scope.student = student) {
  		$scope.student = selectedStudent = {};
  	}
  };
  
  $scope.select = function(student) {
  	$scope.student = student;
  };
  
  $scope.submit - function() {
  	this.reservationService.createReservation({
  		session: $scope.session,
  		student: $scope.students
  	});
  };
}
