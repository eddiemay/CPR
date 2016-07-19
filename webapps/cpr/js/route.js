com.digitald4.cpr.router = function($routeProvider) {
	$routeProvider
		.when('/', {
				controller: 'CalCtrl',
				controllerAs: 'calCtrl',
				templateUrl: 'html/calview.html'
		}).when('/reservation', {
				controller: 'ReservationCtrl',
				controllerAs: 'reservationCtrl',
				templateUrl: 'html/reservation.html'
		}).otherwise({ redirectTo: '/'});
};
