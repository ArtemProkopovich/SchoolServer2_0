/**
 * Created by Евгений on 04.05.2016.
 */
var schoolApp = angular.module('schoolApp', ['ngRoute', 'ngAnimate', 'ngResource']);
var role = 'guest';
var firstname, lastname, classGrade, classLetter, teacherType, ID;
var day = new Date();

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

schoolApp.service("LogOut", function ($location) {
    this.logout = function() {
        role = 'guest';
        $location.path('/');
    }
});
schoolApp.controller('mainController', function($scope, $http, $location) {

    // create a message to display in our view
    $scope.pageClass = 'page-login';
    role = 'guest';
    $scope.submit = function() {
        var params = {
            "login" : $scope.login,
            "password" : $scope.password
        };

        $http.post('login',params).then(function(response) {
            console.log(response);
            role = response.data.role;
            firstname = response.data.name;
            lastname = response.data.surname;
            classGrade = response.data.classGrade;
            classLetter = response.data.classLetter;
            teacherType = response.data.type;
            ID = response.data.entityID;
            if (role=='PUPIL') {
                $location.path('/pupil');
                $scope.loginmsg = 'Hello, pupil!';
            } else if (role=='TEACHER') {
                $location.path('/teacher');
                $scope.loginmsg = 'Welcome, teacher!';
            } else if (role=='ADMIN') {
                $location.path('/admin');
                $scope.loginmsg = 'Welcome, admin!';
            } else {
                $scope.loginmsg = 'Incorrect login or password';
            }
            showToast();
        }).error(function () {
            console.log("Incorrect login or password.");
        });
    }
});
schoolApp.controller('pupilController', function($scope, $location, LogOut, $http) {
    $scope.logout = function() {
        LogOut.logout();
    };
    $scope.getPupilDay = function() {
        var params = {
            "pupilID" : ID,
            "date" : getDDMMYYY(day)
        };
        $http.post('getPupilDay',params).then(function(response) {
            $scope.daySchedule = response.data;
        });
    };
    $scope.setTomorrow = function() {
        day.setDate(day.getDate() + 1);
        $scope.day = getDayName(day)+', '+ getDDMMYYY(day);
        $scope.getPupilDay();
    };
    $scope.setYesterday = function() {
        day.setDate(day.getDate()-1);
        $scope.day = getDayName(day)+', '+getDDMMYYY(day);
        $scope.getPupilDay();
    };
    $scope.pageClass = 'page-app';
    if (role!='PUPIL') {
        LogOut.logout();
    }
    $scope.firstname = firstname;
    $scope.lastname = lastname;
    $scope.classGrade = classGrade;
    $scope.classLetter = classLetter;
    $scope.day = getDayName(day)+', '+ getDDMMYYY(day);
    $scope.getPupilDay();
});
schoolApp.controller('teacherController', function($scope, LogOut, $location, $http) {
    $scope.logout = function() {
        LogOut.logout();
    };
    $scope.getTeacherDay = function() {
        var params = {
            "teacherID" : ID,
            "date" : getDDMMYYY(day)
        };
        $http.post('getTeacherDay',params).then(function(response) {
            $scope.daySchedule = response.data;
        });
    };
    $scope.setTomorrow = function() {
        day.setDate(day.getDate() + 1);
        $scope.day = getDayName(day)+', '+ getDDMMYYY(day);
        $scope.getTeacherDay();
    };
    $scope.setYesterday = function() {
        day.setDate(day.getDate()-1);
        $scope.day = getDayName(day)+', '+getDDMMYYY(day);
        $scope.getTeacherDay();
    };
    $scope.resetHomework = function() {
        $scope.newHomework = $scope.homework;
    };
    $scope.setHomework = function(lessonID) {
        var params = {
            "lessonID":lessonID,
            "homework":$scope.newHomework
        };
        $http.post('setHomework',params).success(function() {
            $scope.message = 'Homework is saved.';
            $scope.homework = $scope.newHomework;
        }).error(function() {
            $scope.message = "Error: can't save homework.";
        });
        showToast();
    };
    $scope.loadLesson = function(lessonID) {
        var params = {
          "lessonID":lessonID
        };
        $http.post('getTeacherLesson',params).then(function(response) {
            $scope.subject = response.data.subject;
            $scope.timerange = response.data.timeRange;
            $scope.classGrade = response.data.classGrade;
            $scope.classLetter = response.data.classLetter;
            $scope.homework = response.data.homework;
            $scope.pupils = response.data.pupils;
            $scope.curLessonID = lessonID;
            $scope.resetHomework();
        });

    };
    $scope.goToSchedule = function() {
        document.getElementById('lesson').style.animation = 'slideOutRight 1s both ease-in';
        document.getElementById('schedule').style.animation = 'slideInLeft 1s both ease-in';
    };
    $scope.goToLesson = function(lessonID) {
        $scope.loadLesson(lessonID);
        document.getElementById('lesson').style.animation = 'slideInRight 1s both ease-in';
        document.getElementById('schedule').style.animation = 'slideOutLeft 1s both ease-in';
    };
    $scope.openMarkWindow = function(pupilID, pupilSurname, pupilName) {
        $scope.curPupilID = pupilID;
        $scope.curSurname = pupilSurname;
        $scope.curName = pupilName;
        document.getElementById('markwindow').style.display = 'block';
    };
    $scope.setMark = function(pupilID, lessonID) {
        var params = {
            "lessonID":lessonID,
            "pupilID":pupilID,
            "mark":$scope.mark
        };
        $http.post('setMark',params).success(function() {
            $scope.message = 'Mark is saved.';
            document.getElementById('markwindow').style.display = 'none';
        }).error(function() {
            $scope.message = "Error: can't save mark.";
        });
        showToast();
        $scope.loadLesson(lessonID);
    };
    $scope.pageClass = 'page-app';
    if (role!='TEACHER') {
        LogOut.logout();
    }
    $scope.firstname = firstname;
    $scope.lastname = lastname;
    $scope.teacherType = teacherType;
    $scope.day = getDayName(day)+', '+ getDDMMYYY(day);
    $scope.getTeacherDay();
});
schoolApp.controller('adminController', function($scope, LogOut) {
    $scope.pageClass = 'page-app';
    if (role!='ADMIN') {
       LogOut.logout();
    }
    $scope.logout = function() {
        LogOut.logout();
    }
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
function getDDMMYYY (date) {
    var dd = date.getDate();
    var mm = date.getMonth()+1; //January is 0!
    var yyyy = date.getFullYear();

    if(dd<10) {
        dd='0'+dd
    }

    if(mm<10) {
        mm='0'+mm
    }
    return (dd+'.'+mm+'.'+yyyy);
}
function getDayName (date) {
    switch (date.getDay()) {
        case 0: return 'Sunday';
        case 1: return 'Monday';
        case 2: return 'Tuesday';
        case 3: return 'Wednesday';
        case 4: return 'Thursday';
        case 5: return 'Friday';
        case 6: return 'Saturday';
    }
}
function showToast() {
    var toast = document.getElementById('toast');
    toast.classList.remove("toast");
    toast.offsetWidth = toast.offsetWidth;
    toast.classList.add("toast");
    toast.style.display = 'block';
}