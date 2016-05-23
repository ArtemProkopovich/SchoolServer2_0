/**
 * Created by Евгений on 04.05.2016.
 */
var schoolApp = angular.module('schoolApp', ['ngRoute', 'ngAnimate', 'ngResource']);
var role = null;

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
        .when ('/teacher', {
            templateUrl : 'pages/teacher.html',
            controller : 'teacherController'
        })
        .when ('/admin', {
            templateUrl : 'pages/admin.html',
            controller : 'adminController'
        })
        .otherwise({redirectTo: '/'});
});

// create the controller and inject Angular's $scope
schoolApp.controller('mainController', function($scope, $http, $location) {

    // create a message to display in our view
    $scope.pageClass = 'page-login';
    $scope.submit = function() {
        var params = {
            "login" : $scope.login,
            "password" : $scope.password
        };
        
        $http.post( 'login', params
        ).then(function(data) {
            console.log(data);
            //$location.path('/');
            $scope.loginmsg = 'OK!';
            document.getElementById('toast').style.display = 'block';
            document.getElementById('toast').style.animation = 'toastanim 4s both ease-in';
        })/*.error(function () {
            console.log("Incorrect login or password.");
        });*/
    }
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
schoolApp.controller('teacherController', function($scope) {
    $scope.pageClass = 'page-app';
});
schoolApp.controller('adminController', function($scope) {
    $scope.pageClass = 'page-app';
});
function openAdminTab(event, tabName, tabIndex) {
    // Declare all variables
    var i, tabcontent, tablinks, prevTabIndex;

    prevTabIndex=0;
    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        if (tabcontent[i].style.animation == "slideInLeft 1s ease-in both" || tabcontent[i].style.animation == "slideInRight 1s ease-in both") {
            prevTabIndex = i;
            console.log (prevTabIndex);
            break;
        }
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tabcontent.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    // Show the current tab, and add an "active" class to the link that opened the tab
    if (tabIndex>prevTabIndex) {
        tabcontent[prevTabIndex].style.animation = "slideOutLeft 1s both ease-in";
        document.getElementById(tabName).style.animation = "slideInRight 1s both ease-in";
    } else {
        tabcontent[prevTabIndex].style.animation = "slideOutRight 1s both ease-in";
        document.getElementById(tabName).style.animation = "slideInLeft 1s both ease-in";
    }
    event.currentTarget.className += " active";
}
function toggleWindow (windowId, isVisible) {
    if (isVisible==true) {
        document.getElementById(windowId).style.display = "block"
    } else {
        document.getElementById(windowId).style.display = "none";
    }
}