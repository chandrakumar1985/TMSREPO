'use strict';

services.factory('ReportingService',['$resource',  function ($resource) {
	
	

    return {
        getVisitFrequencyList: function(month,year, date, username) {
        	//'rest/project/:id', {id: '@id'}
        	var baseVisitFrequencyList =  $resource('rest/tms/getbillingchartreport/:month/:year/:date/:username', {month: month, year:year,date:date, username:username}, {
                query: {
                    isArray: true,
                    method: 'GET'
                }
            });;
        	
        	var aPromise = baseVisitFrequencyList.query().$promise.then(function(object){
        		//massage data in a format acceptable to the directive
        		var list = [];
        		for (var i = 0; i < object.length; i++){
        			//var visitDate = object[i].visitDate;
        			//visitDate = massageDate(visitDate); //if necessary
        			//var numOfVisits = object[i].numberOfVisits;
        			var o = {"key":object[i].key, "value":object[i].count};
        			list.push(o);
        		}
        		
        		//Sort by date
        		list.sort(function(e1, e2) {
        			var date1 = new XDate(e1.key);
        			var date2 = new XDate(e2.key);
        			return date1 - date2; 
        		});
        		return list;
        	});
        	return aPromise;
        }
    };
}]);
