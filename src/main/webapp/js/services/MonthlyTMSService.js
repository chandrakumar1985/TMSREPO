'use strict';
services.factory('MonthlyTMSService',['$resource',  function ($resource) {
	
	

    return {
    	getCalendar: function(month, year, date, username) {
    	//'rest/project/:id', {id: '@id'}
		//return $resource('rest/project/listproject/:id', {id: '@id'});
    	var projectReportList =  $resource('rest/tms/currentmonthcalendar/:month/:year/:date/:username',{month:month,year:year, date:date, username:username }, {
            query: {
                isArray: false,
                method: 'GET'
            }
        });;
    	
    	var aPromise = projectReportList.query().$promise.then(function(object){
    		//massage data in a format acceptable to the directive
    		var returnObj = object;
    		
    		return returnObj;
    	});
    	return aPromise;
    }

    };
    
    //return {};
}]);
