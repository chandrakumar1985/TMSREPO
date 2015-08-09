'use strict';

exampleApp.controller('MonthlyTMSController', ['$scope','$rootScope',  'MonthlyTMSService', function MonthlyTMSController ($scope,$rootScope, MonthlyTMSService) {
	$scope.tab = 'addweektms';
	$scope.month = 0;
    $scope.monthOptions = [{monthno:0, monthname:'January'},{monthno:1, monthname:'February'},{monthno:2, monthname:'March'},{monthno:3, monthname:'April'},{monthno:4, monthname:'May'},{monthno:5, monthname:'June'},{monthno:6, monthname:'July'},{monthno:7, monthname:'August'},{monthno:8, monthname:'September'},{monthno:9, monthname:'October'},{monthno:10, monthname:'Novemeber'},{monthno:11, monthname:'December'}];

    $scope.setTab = function (tabId) {
    	$scope.tab = tabId;
    };

    $scope.isSet = function (tabId) {
        return $scope.tab === tabId;
    };
    
    $scope.change = function()
    {
    	var aPromise = MonthlyTMSService.getCalendar($scope.month, 2015, 1, $rootScope.user.name);
    	aPromise.then(function(object){
    		$scope.calendar = object;
    	}, function errorCallback(error) {
    		showAlert("error", error);
    	});	
    };
	//$scope.calendar = MonthlyTMSService.get({month: 0, year:0, date:0, username:$rootScope.user.name});
	var aPromise = MonthlyTMSService.getCalendar(0, 0, 0, $rootScope.user.name);
	aPromise.then(function(object){
		$scope.calendar = object;
		$scope.month = object.monthNo;
	}, function errorCallback(error) {
		showAlert("error", error);
	});
	
	
	
	 
}]);
