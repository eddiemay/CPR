com.digitald4.cpr.module = angular.module('cpr', ['ngMaterial', 'ngRoute', 'ui.calendar']);

// com.digitald4.cpr.module.config(com.digitald4.cpr.router);

com.digitald4.cpr.module.service('restService', com.digitald4.common.HttpConnector);
com.digitald4.cpr.module.service('trainningService', com.digitald4.cpr.TrainningService);
com.digitald4.cpr.module.service('reservationService', com.digitald4.cpr.ReservationService);

com.digitald4.cpr.module.controller('CalCtrl', com.digitald4.cpr.CalCtrl)
		.config(function($mdThemingProvider) {
		  // Configure a dark theme with primary foreground yellow
		  $mdThemingProvider.theme('docs-dark', 'default')
		    .primaryPalette('yellow')
		    .dark();
		});
// com.digitald4.cpr.module.controller('ReservationCtrl', com.digitald4.cpr.TemplatesCtrl);

com.digitald4.cpr.module.directive('onChange', function() {
  return function(scope, element, attrs) {
  	var stValue = element.val();
  	element.bind('blur', function() {
  		if (stValue != element.val()) {
  			scope.$apply(attrs.onChange);
  			stValue = element.val();
  		}
  	});
  };
});
