'use strict';

services.factory('ProjectReportService',['$resource',  function ($resource) {
	
	

    return {
    	getProjectReport: function(month,year, date, projectid) {
        	//'rest/project/:id', {id: '@id'}
        	var projectReportList =  $resource('rest/report/getprojectreport/:month/:year/:date/:projectid', {month: month, year:year,date:date, projectid:projectid}, {
                query: {
                    isArray: true,
                    method: 'GET'
                }
            });;
        	
        	var aPromise = projectReportList.query().$promise.then(function(object){
        		//massage data in a format acceptable to the directive
        		var list = [];
        		for (var i = 0; i < object.length; i++){
        			
        			list.push(object[i]);
        		}
        		return list;
        	});
        	return aPromise;
        },

	getAllProject: function() {
    	//'rest/project/:id', {id: '@id'}
		//return $resource('rest/project/listproject/:id', {id: '@id'});
    	var projectReportList =  $resource('rest/project/listproject', {
            query: {
                isArray: true,
                method: 'GET'
            }
        });;
    	
    	var aPromise = projectReportList.query().$promise.then(function(object){
    		//massage data in a format acceptable to the directive
    		var list = [];
    		for (var i = 0; i < object.length; i++){
    			
    			var o = {"id":object[i].id, "projectName":object[i].projectName};
    			list.push(o);
    		}
    		return list;
    	});
    	return aPromise;
    }

    };
    
    //return {};
}]);
