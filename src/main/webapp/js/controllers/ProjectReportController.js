'use strict';

exampleApp.controller('ProjectReportController', ['$scope','$rootScope',  'ProjectReportService', function ProjectReportController ($scope,$rootScope, ProjectReportService) {
	$scope.year=2015;
	$scope.month=4;
	$scope.date=1;
	$scope.projectid= "";
	var username = $rootScope.user.name;
	$scope.projectReport = [];
	
	$scope.reportOptions =['By Month', 'By Year', 'By Date'];
	$scope.monthOptions = [{id:1, label:'January'}, {id:2, label:'February'}, {id:3, label:'March'}, {id:4, label:'April'},
	{id:5, label:'May'}, {id:6, label:'June'},{id:7, label:'July'}, {id:8, label:'August'},
	,{id:9, label:'Septemeber'}, {id:10, label:'October'}, {id:11, label:'November'}, {id:12, label:'December'}];
	$scope.yearOptions = [{id:'2014',label:'2014'},{id:'2015',label:'2015'}];
    
	
	
	var aPromise = ProjectReportService.getAllProject();
	aPromise.then(function(object){
		$scope.projectOptions = object;
	}, function errorCallback(error) {
		showAlert("error", error);
	});	
	
	$scope.displayReport = function() {
		var aPromise1 = ProjectReportService.getProjectReport($scope.month,$scope.year, $scope.date, $scope.projectid);
		aPromise1.then(function(object){
			$scope.projectReport = object;
		}, function errorCallback(error) {
			showAlert("error", error);
		});
	};
	
	 
}]);
