angular.module('exampleApp', ['ngRoute', 'ngCookies', 'exampleApp.services','checklist-model','dropdown-multiselect'])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/create', {
				templateUrl: 'partials/create.html',
				controller: CreateController
			});
			
			$routeProvider.when('/createUser', {
				templateUrl: 'partials/createUser.html',
				controller: CreateUserController
			});
			$routeProvider.when('/edit/:id', {
				templateUrl: 'partials/edit.html',
				controller: EditController
			});
			
			$routeProvider.when('/editproject/:id', {
				templateUrl: 'partials/editproject.html',
				controller: EditProjectController
			});
			
			$routeProvider.when('/addproject', {
				templateUrl: 'partials/addproject.html',
				controller: AddProjectController
			});

			$routeProvider.when('/addtms', {
				templateUrl: 'partials/addtms.html',
				controller: AddTMSController
			});
			$routeProvider.when('/addweektms', {
				templateUrl: 'partials/addweektms.html',
				controller: AddWeekTMSController
			});
			$routeProvider.when('/monthlytms', {
				templateUrl: 'partials/monthlytms.html',
				controller: MonthlyTMSController
			});
			$routeProvider.when('/approvetms', {
				templateUrl: 'partials/approvetms.html',
				controller: ApproveTMSController
			});
			
			$routeProvider.when('/login', {
				templateUrl: 'partials/login.html',
				controller: LoginController
			});
			$routeProvider.when('/user',{
				templateUrl: 'partials/user.html',
				controller: IndexController
			});
			$routeProvider.when('/project',{
				templateUrl: 'partials/project.html',
				controller: ProjectController
			});
			
			$routeProvider.otherwise({
				templateUrl: 'partials/index.html',
				controller: IndexController
			});
			
			$locationProvider.hashPrefix('!');
			
			/* Register error provider that shows message on failed requests or redirects to login page on
			 * unauthenticated requests */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
			        return {
			        	'responseError': function(rejection) {
			        		var status = rejection.status;
			        		var config = rejection.config;
			        		var method = config.method;
			        		var url = config.url;
			      
			        		if (status == 401) {
			        			$location.path( "/login" );
			        		} else {
			        			$rootScope.error = method + " on " + url + " failed with status " + status;
			        		}
			              
			        		return $q.reject(rejection);
			        	}
			        };
			    }
		    );
		    
		    /* Registers auth token interceptor, auth token is either passed by header or by query parameter
		     * as soon as there is an authenticated user */
		    $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
		        return {
		        	'request': function(config) {
		        		var isRestCall = config.url.indexOf('rest') == 0;
		        		if (isRestCall && angular.isDefined($rootScope.authToken)) {
		        			var authToken = $rootScope.authToken;
		        			if (exampleAppConfig.useAuthTokenHeader) {
		        				config.headers['X-Auth-Token'] = authToken;
		        			} else {
		        				config.url = config.url + "?token=" + authToken;
		        			}
		        		}
		        		return config || $q.when(config);
		        	}
		        };
		    }
	    );
		   
		} ]
		
	).run(function($rootScope, $location, $cookieStore, UserService) {
		
		/* Reset error when a new view is loaded */
		$rootScope.$on('$viewContentLoaded', function() {
			delete $rootScope.error;
		});
		
		$rootScope.hasRole = function(role) {
			
			if ($rootScope.user === undefined) {
				return false;
			}
			
			if ($rootScope.user.roles[role] === undefined) {
				return false;
			}
			
			return $rootScope.user.roles[role];
		};
		
		$rootScope.logout = function() {
			delete $rootScope.user;
			delete $rootScope.authToken;
			$cookieStore.remove('authToken');
			$location.path("/login");
		};
		
		 /* Try getting valid user from cookie or go to login page */
		var originalPath = $location.path();
		$location.path("/login");
		var authToken = $cookieStore.get('authToken');
		if (authToken !== undefined) {
			$rootScope.authToken = authToken;
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path(originalPath);
			});
		}
		
		$rootScope.initialized = true;
	});


function IndexController($scope, ListUserService) {
	
	$scope.userEntries = ListUserService.query();
	
	$scope.deleteEntry = function(userEntry) {
		userEntry.$remove(function() {
			$scope.userEntries = ListUserService.query();
		});
	};
};


function ProjectController($scope, ProjectService) {
	
	$scope.projectEntries = ProjectService.query();
	
	$scope.deleteEntry = function(projectEntry) {
		projectEntry.$remove(function() {
			$scope.projectEntries = ProjectService.query();
		});
	};
};



function EditController($scope, $routeParams, $location, UserEditService) {

	$scope.user = UserEditService.get({id: $routeParams.id});
	$scope.error = false;
	$scope.roless = ['user', 'admin'];
	$scope.save = function() {
		if ($scope.userForm.$valid)
			{
			//alert($scope.user.roles);
			//for(key in $scope.user)
			 // {
			    //alert(key + ':' + $scope.user[key]);
			 // }
		$scope.user.$save(function() {
			$location.path('/');
		});
			}
		else
			$scope.error = true;
	};
};



function EditProjectController($scope, $routeParams, $location, ProjectEditService, ProjectActivityService, ListUserService) {

	
	
		
	$scope.projectResources = ListUserService.query();
	$scope.projectActivities = ProjectActivityService.query();
	$scope.project = ProjectEditService.get({id: $routeParams.id});
	$scope.error = false;
	//$scope.projectResources= ['hanuman', 'hanu','bhanu','chandra','gyan','user','admin'];
	//$scope.projectActivities = ['Leave','Gravitant','Idle','Lucasware','EP'];
	//$scope.roless = ['user', 'admin'];
	$scope.save = function() {
		if ($scope.projectForm.$valid)
			{
			//alert($scope.user.roles);
			//for(key in $scope.user)
			 // {
			    //alert(key + ':' + $scope.user[key]);
			 // }
		$scope.project.$save(function() {
			$location.path('/project');
		});
			}
		else
			$scope.error = true;
	};
};


function CreateController($scope, $location, UserEditService) {
	
	$scope.newsEntry = new UserEditService();
	
	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};

function CreateUserController($scope, $location, NewUserService) {
	
	$scope.user = new NewUserService();
	$scope.user.roles= [];
	$scope.error = false;
	$scope.roless = ['user', 'admin'];
	$scope.save = function() {
		if ($scope.userForm.$valid)
			{
		$scope.user.$save(function() {
			$location.path('/');
		});
			}
		else
			$scope.error = true;
	};
};

function AddProjectController($scope, $location, NewProjectService, ProjectActivityService, ListUserService) {
	
	$scope.project = new NewProjectService();
	
	$scope.projectResources = ListUserService.query();
	$scope.projectActivities = ProjectActivityService.query();
	
	//need to retrieve these values from rest services.
	//
	$scope.project.projectResources = [];
	$scope.project.projectActivities = [];
	$scope.error = false;
	//$scope.roless = ['user', 'admin'];
	$scope.save = function() {
		if ($scope.projectForm.$valid)
			{
		$scope.project.$save(function() {
			$location.path('/project');
		});
			}
		else
			$scope.error = true;
	};
};



function MonthlyTMSController($scope,$rootScope, $location, MonthlyTMSService) {
	
	var date = new Date();
   //month,:year,:date,:username
	//();
	$scope.calendar = MonthlyTMSService.get({month: date.getMonth(), year:date.getFullYear(), date:date.getDate(), username:$rootScope.user.name});
	
	
	};
function ApproveTMSController($scope,$rootScope, $location,$routeParams, TMSApprovalService, TMSApprovalUpdateService) {
	
	$scope.tms = new TMSApprovalUpdateService();
	$scope.userTMS = TMSApprovalService.query({approverName: $rootScope.user.name});
	$scope.error = false;
	//$scope.roless = ['user', 'admin'];
	$scope.save = function(method, projectTMS) {	
				
			if(method == 'approved')
			{
				projectTMS.submitted = false;
				$scope.tms.submitted = false;
				projectTMS.approved= true;
				$scope.tms.approved = true;
				projectTMS.rejected= false;
				$scope.tms.rejected = false;
			}
			else if(method == 'rejected')
			{
				projectTMS.submitted = false;
				$scope.tms.submitted = false;
				projectTMS.rejected= true;
				$scope.tms.rejected = true;
				projectTMS.approved= false;
				$scope.tms.approved= false;
			}
			/////
			$scope.tms.projectName= projectTMS.projectName;
			$scope.tms.activityName= projectTMS.activityName;
			$scope.tms.noOfHours= projectTMS.noOfHours;
			$scope.tms.userRemarks= projectTMS.userRemarks;
			$scope.tms.approverRemarks= projectTMS.approverRemarks;
			$scope.tms.id= projectTMS.id;
			$scope.tms.userTmsId= projectTMS.userTmsId;
		$scope.tms.$save(function() {
			delete projectTMS;
			$location.path('/approvetms');
		});
			
	};
		
};

function AddTMSController($scope,$rootScope,$q, $location,$routeParams,TMSRetrievalByDateService,  NewTMSService, ProjectActivityService, ProjectBasedOnUserService) {
	
	$scope.tms = new NewTMSService();
	$scope.projectOptions = [];
	 //$scope.projects = [];
	$scope.projectOptions=  ProjectBasedOnUserService.query({user: $rootScope.user.id});
	
	$scope.projectActivitiesDrpDN = [];
	$scope.projectActivities = ProjectActivityService.query();
	
	//**this code is for edit to pre populate the date and display on UI
	
	
	//
	$scope.userTMSEdit = TMSRetrievalByDateService.query({month:$routeParams.month,year:$routeParams.year,date:$routeParams.day,username:$rootScope.user.name });
	var delayedValue = function($scope, deferred, value) {
	    setTimeout(function() {
	        $scope.$apply(function () {
	            deferred.resolve(value);
	            if(value.length > 0)
	    		{
	    	for(var i=0; i <value.length; i++)
	    		{
	    		$scope.userTMS = [];
	    		$scope.userTMS.push({id:value[i].id,userTmsId:value[i].userTmsId,projectName:value[i].projectName,activityName:value[i].activityName, noOfHours:value[i].noOfHours, projectOptions:$scope.projectOptions, projectActivitiesDrpDN:[], tmsDate:$routeParams.day+"/"+$routeParams.month+"/"+$routeParams.year});
	    		$scope.change($scope.userTMS[i]);
	    		}
	    		}
	    	else
	    		{
	    	$scope.userTMS = [{projectName:null,activityName:null, noOfHours:null, projectOptions:$scope.projectOptions, projectActivitiesDrpDN:[], tmsDate:$routeParams.day+"/"+$routeParams.month+"/"+$routeParams.year}];
	    		}
	        });
	    }, 1000);
	    return deferred.promise;
	};
	
	var deferred = $q.defer();
	  $scope.userTMSEdit = delayedValue($scope, deferred, $scope.userTMSEdit);
	
	
	
	
	$scope.error = false;
	//$scope.roless = ['user', 'admin'];
	
	$scope.save = function(method) {
		$scope.tms.month=$routeParams.month;
		$scope.tms.year=$routeParams.year;
		$scope.tms.day=$routeParams.day;
		$scope.tms.tmsDate=  $scope.tms.day + "/" + $scope.tms.month +"/"+$scope.tms.year;
		if(method == 'save')
		{
			$scope.tms.saved= true;
		}
		else if(method == 'submit')
		{
			$scope.tms.submitted= true;
		}
		for(var i=0; i <$scope.userTMS.length; i++)
		{
			
			delete $scope.userTMS[i]['projectOptions'];
			delete $scope.userTMS[i]['projectActivitiesDrpDN'];
			if(method == 'save')
			{
				$scope.userTMS[i].saved = true;
			}
			else if(method == 'submit')
			{
				$scope.userTMS[i].submitted = true;
			}
			$scope.tms.id=$scope.userTMS[i].userTmsId;
			
		}
		$scope.tms.projectTMS=$scope.userTMS;
		
		$scope.tms.userName=$rootScope.user.name;
		$scope.tms.$save(function() {
			$location.path('/monthlytms');
		});
			
	};
	
	
	
	$scope.addRow = function() {
		$scope.userTMS.push({projectName:null,activityName:null, noOfHours:null, projectOptions:$scope.projectOptions, projectActivitiesDrpDN:[]});
	};
	
	$scope.change = function(usertms) {
		//alert($scope.tms.projectName);
		 //console.log(person);
		$scope.projectActivitiesDrpDN = [];
		usertms.projectActivitiesDrpDN = [];
		var temp = [];
		var index = 0;
		for(var i=0; i <$scope.userTMS.length; i++)
			{
			 if($scope.userTMS[i].projectName == usertms.projectName)
				 {
				 for(var j=0; j<$scope.userTMS[i].projectOptions.length; j++ )
					 {
					 if($scope.userTMS[i].projectOptions[j].projectName == usertms.projectName)
						 {
						 temp =  $scope.userTMS[i].projectOptions[j].projectActivities;
						 $scope.userTMS[i].approverName = $scope.userTMS[i].projectOptions[j].projectOwner;
						 }
					 }
				 index = i;
				 }
			}
		
		
		
		for(var i=0; i<temp.length; i++){ 
			
			//$scope.selected_items.push($scope.pre_selected[i].id);
			for(var j=0; j<$scope.projectActivities.length; j++){ 
				{
				  if(temp[i] == $scope.projectActivities[j].id)
					  {
					  $scope.userTMS[index].projectActivitiesDrpDN.push({"name":$scope.projectActivities[j].name, "value":$scope.projectActivities[j].id });
					  //usertms.projectActivitiesDrpDN.push({"name":$scope.projectActivities[j].name, "value":$scope.projectActivities[j].id });
					  }
					  }
					  
				}
        }
		
		
		//return $scope.projectActivitiesDrpDN;
		
	};
};





function AddWeekTMSController($scope,$rootScope,$q, $location,$routeParams,TMSRetrievalByDateService,  NewTMSService, ProjectActivityService, ProjectBasedOnUserService) {
	
	$scope.tms = new NewTMSService();
	$scope.projectOptions = [];
	 //$scope.projects = [];
	$scope.projectOptions=  ProjectBasedOnUserService.query({user: $rootScope.user.id});
	
	$scope.projectActivitiesDrpDN = [];
	$scope.projectActivities = ProjectActivityService.query();
	
	//**this code is for edit to pre populate the date and display on UI
	
	
	//
	$scope.userTMSEdit = TMSRetrievalByDateService.query({month:$routeParams.month,year:$routeParams.year,date:$routeParams.day,username:$rootScope.user.name });
	var delayedValue = function($scope, deferred, value) {
	    setTimeout(function() {
	        $scope.$apply(function () {
	            deferred.resolve(value);
	            if(value.length > 0)
	    		{
	    	for(var i=0; i <value.length; i++)
	    		{
	    		$scope.userTMS = [];
	    		$scope.userTMS.push({id:value[i].id,userTmsId:value[i].userTmsId,projectName:value[i].projectName,activityName:value[i].activityName, noOfHours:value[i].noOfHours, projectOptions:$scope.projectOptions, projectActivitiesDrpDN:[], tmsDate:$routeParams.day+"/"+$routeParams.month+"/"+$routeParams.year});
	    		$scope.change($scope.userTMS[i]);
	    		}
	    		}
	    	else
	    		{
	    	$scope.userTMS = [{projectName:null,activityName:null, noOfHours:null, projectOptions:$scope.projectOptions, projectActivitiesDrpDN:[], tmsDate:$routeParams.day+"/"+$routeParams.month+"/"+$routeParams.year}];
	    		}
	        });
	    }, 1000);
	    return deferred.promise;
	};
	
	var deferred = $q.defer();
	  $scope.userTMSEdit = delayedValue($scope, deferred, $scope.userTMSEdit);
	
	
	
	
	$scope.error = false;
	//$scope.roless = ['user', 'admin'];
	
	$scope.save = function(method) {
		$scope.tms.month=$routeParams.month;
		$scope.tms.year=$routeParams.year;
		$scope.tms.day=$routeParams.day;
		$scope.tms.tmsDate=  $scope.tms.day + "/" + $scope.tms.month +"/"+$scope.tms.year;
		if(method == 'save')
		{
			$scope.tms.saved= true;
		}
		else if(method == 'submit')
		{
			$scope.tms.submitted= true;
		}
		for(var i=0; i <$scope.userTMS.length; i++)
		{
			
			delete $scope.userTMS[i]['projectOptions'];
			delete $scope.userTMS[i]['projectActivitiesDrpDN'];
			if(method == 'save')
			{
				$scope.userTMS[i].saved = true;
			}
			else if(method == 'submit')
			{
				$scope.userTMS[i].submitted = true;
			}
			$scope.tms.id=$scope.userTMS[i].userTmsId;
			
		}
		$scope.tms.projectTMS=$scope.userTMS;
		
		$scope.tms.userName=$rootScope.user.name;
		$scope.tms.$save(function() {
			$location.path('/monthlytms');
		});
			
	};
	
	
	
	$scope.addRow = function() {
		$scope.userTMS.push({projectName:null,activityName:null, noOfHours:null, projectOptions:$scope.projectOptions, projectActivitiesDrpDN:[]});
	};
	
	$scope.change = function(usertms) {
		//alert($scope.tms.projectName);
		 //console.log(person);
		$scope.projectActivitiesDrpDN = [];
		usertms.projectActivitiesDrpDN = [];
		var temp = [];
		var index = 0;
		for(var i=0; i <$scope.userTMS.length; i++)
			{
			 if($scope.userTMS[i].projectName == usertms.projectName)
				 {
				 for(var j=0; j<$scope.userTMS[i].projectOptions.length; j++ )
					 {
					 if($scope.userTMS[i].projectOptions[j].projectName == usertms.projectName)
						 {
						 temp =  $scope.userTMS[i].projectOptions[j].projectActivities;
						 $scope.userTMS[i].approverName = $scope.userTMS[i].projectOptions[j].projectOwner;
						 }
					 }
				 index = i;
				 }
			}
		
		
		
		for(var i=0; i<temp.length; i++){ 
			
			//$scope.selected_items.push($scope.pre_selected[i].id);
			for(var j=0; j<$scope.projectActivities.length; j++){ 
				{
				  if(temp[i] == $scope.projectActivities[j].id)
					  {
					  $scope.userTMS[index].projectActivitiesDrpDN.push({"name":$scope.projectActivities[j].name, "value":$scope.projectActivities[j].id });
					  //usertms.projectActivitiesDrpDN.push({"name":$scope.projectActivities[j].name, "value":$scope.projectActivities[j].id });
					  }
					  }
					  
				}
        }
		
		
		//return $scope.projectActivitiesDrpDN;
		
	};
};


function LoginController($scope, $rootScope, $location, $cookieStore, UserService) {
	
	$scope.rememberMe = false;
	
	$scope.login = function() {
		UserService.authenticate($.param({username: $scope.username, password: $scope.password}), function(authenticationResult) {
			var authToken = authenticationResult.token;
			$rootScope.authToken = authToken;
			if ($scope.rememberMe) {
				$cookieStore.put('authToken', authToken);
			}
			UserService.get(function(user) {
				$rootScope.user = user;
				$location.path("/");
			});
		});
	};
};


var services = angular.module('exampleApp.services', ['ngResource']);

services.factory('UserService', function($resource) {
	
	return $resource('rest/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				},
			}
		);
});

services.factory('NewsService', function($resource) {
	
	return $resource('rest/news/:id', {id: '@id'});
});

services.factory('NewUserService', function($resource) {
	
	return $resource('rest/user/adduser');
});

services.factory('NewProjectService', function($resource) {
	
	return $resource('rest/project/addproject');
});





services.factory('ListUserService', function($resource) {
	
	return $resource('rest/user/listuser/:id', {id: '@id'});
});

services.factory('ProjectService', function($resource) {
	
	return $resource('rest/project/listproject/:id', {id: '@id'});
});
services.factory('ProjectBasedOnUserService', function($resource) {
	return $resource('rest/project/listproject/:user', {user: '@user'}, {
        query: {
            isArray: true,
            method: 'GET'
        }
    });
});

services.factory('ProjectActivityService', function($resource) {
	
	return $resource('rest/project/listprojectactivity/:id', {id: '@id'});
});

services.factory('UserEditService', function($resource) {
	
	return $resource('rest/user/:id', {id: '@id'});
});

services.factory('ProjectEditService', function($resource) {
	
	return $resource('rest/project/:id', {id: '@id'});
});

services.factory('NewTMSService', function($resource) {
	
	return $resource('rest/tms/addtms');
});

services.factory('TMSService', function($resource) {
	
	return $resource('rest/tms/gettms');
});

services.factory('TMSApprovalService', function($resource) {
	
	return $resource('rest/tms/getalltmsforaprroval/:approverName', {approverName: '@approverName'}, {
        query: {
            isArray: true,
            method: 'GET'
        }
    });
});

services.factory('TMSRetrievalByDateService', function($resource) {
	
	return $resource('rest/tms/gettms/:month/:year/:date/:username', {month: '@month',year:'@year',date:'@date',username:'@username'}, {
        query: {
            isArray: true,
            method: 'POST'
        }
    });
});

services.factory('TMSApprovalUpdateService', function($resource) {
	
	return $resource('rest/tms/update');
});


services.factory('MonthlyTMSService', function($resource) {
	
	return $resource('rest/tms/currentmonthcalendar/:month/:year/:date/:username',{month: '@month',year:'@year', date:'@date', username:'@username' } );
});


