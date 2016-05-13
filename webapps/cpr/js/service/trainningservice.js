com.digitald4.cpr.TrainningService = function(RestService) {
	this.restService = RestService;
};

com.digitald4.cpr.TrainningService.prototype.restService;

com.digitald4.cpr.TrainningService.prototype.getTrainnings = function(successCallback,
		errorCallback) {
	this.restService.performRequest('trainnings', {}, successCallback, errorCallback);
};

com.digitald4.cpr.TrainningService.prototype.getSessions = function(trainningId, dateRangeType,
		refDate, successCallback, errorCallback) {
	this.restService.performRequest('sessions', {trainning_id: trainningId, date_range: dateRangeType,
			ref_date: refDate}, successCallback, errorCallback);
};

// Car Insurance Policy 971610883
