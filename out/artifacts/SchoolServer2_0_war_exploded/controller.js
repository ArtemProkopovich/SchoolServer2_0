/**
 * Created by Евгений on 04.05.2016.
 */
var schoolApp = angular.module('schoolApp', ['ngRoute', 'ngAnimate', 'ngResource']);

schoolApp.config(function($routeProvider) {
    $routeProvider

        .when ('/', {
            templateUrl : 'pages/login.html',
            controller : 'mainController'
        })
        .when ('/pupil', {
            templateUrl : 'pages/pupil.html',
            controller : 'pupilController'
        })
        .otherwise({redirectTo: '/'});
});

// create the controller and inject Angular's $scope
schoolApp.controller('mainController', function($scope) {

    // create a message to display in our view
    $scope.pageClass = 'page-login';
});
schoolApp.service("PupilScheduleService", function($http, $q) {
   var deferred = $q.defer();
    $http.get("PupilDaySchedule.json").then(function (data) {
        deferred.resolve(data);
    });
    this.getDaySchedule = function() {
        return deferred.promise;
    }
});
schoolApp.controller('pupilController', function($scope, PupilScheduleService) {
    $scope.pageClass = 'page-app';

    /*$scope.schedule = $resource('http://search.twitter.com/:action',
        {action:'search.json', q:'angular', callback: 'JSON_CALLBACK'},
        {get:{method:'JSONP'}});

    $scope.ClassDaySchedule = $scope.schedule.get();*/
    var promise = PupilScheduleService.getDaySchedule();
    promise.then(function (data) {
        $scope.daySchedule = data.data;
        console.log($scope.daySchedule);
    });
});