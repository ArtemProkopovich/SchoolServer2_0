/**
 * Created by Евгений on 04.05.2016.
 */
var schoolApp = angular.module('schoolApp', ['ngRoute', 'ngAnimate']);

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
    //$scope.message = 'Everyone come and see how good I look!';
    $scope.pageClass = 'page-login';
});
schoolApp.controller('pupilController', function($scope) {
    //$scope.message = 'This is pupil page';
    $scope.pageClass = 'page-app';
});