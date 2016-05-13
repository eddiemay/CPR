com.digitald4.cpr.module = angular.module('cpr', ['ngMaterial', 'ngRoute']);

// com.digitald4.cpr.module.config(com.digitald4.cpr.router);

com.digitald4.cpr.module.service('RestService', com.digitald4.common.HttpConnector);
com.digitald4.cpr.module.service('TrainningService', com.digitald4.cpr.TrainningService);
com.digitald4.cpr.module.service('ReservationService', com.digitald4.cpr.ReservationService);

com.digitald4.cpr.module.controller('CalCtrl', com.digitald4.cpr.CalCtrl);
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
