com.digitald4.cpr.module = angular.module('cpr', ['DD4Common', 'ngMaterial', 'ngRoute', 'ui.calendar']);

com.digitald4.cpr.module.service('trainningService', com.digitald4.cpr.TrainningService);
com.digitald4.cpr.module.service('reservationService', com.digitald4.cpr.ReservationService);

com.digitald4.cpr.module.controller('CalCtrl', com.digitald4.cpr.CalCtrl)
		.config(function($mdThemingProvider) {
		  // Configure a dark theme with primary foreground yellow
		  $mdThemingProvider.theme('docs-dark', 'default')
		    .primaryPalette('yellow')
		    .dark();
		});
