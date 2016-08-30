com.digitald4.cpr.TrainningService = function(restService) {
	this.restService = restService;
};

com.digitald4.cpr.TrainningService.prototype.restService;

com.digitald4.cpr.TrainningService.prototype.getTrainnings = function(successCallback,
		errorCallback) {
	this.restService.performRequest('trainnings', {}, successCallback, errorCallback);
};

com.digitald4.cpr.TrainningService.prototype.getSessions = function(trainningId, startDate, endDate,
		successCallback, errorCallback) {
	this.restService.performRequest('sessions', {trainning_id: trainningId, start_date: startDate,
			end_date: endDate}, successCallback, errorCallback);
};

