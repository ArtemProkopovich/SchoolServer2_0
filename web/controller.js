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
    var docType = 'csv';

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
    $scope.setDoctype = function(type) {
        docType = type;
    };
    $scope.openDoclist = function(event) {
        showDropdown('doclist',event.x,event.y);
    };
    $scope.setDocument = function(doc) {
        $scope.doc = doc;
        hideDropdown('doclist');
    };
    $scope.download = function() {
        var data = {
            pupilID: ID,
            docType: docType
        };
        window.open('downloadPupil'+$scope.doc+'?docType='+data.docType+'&pupilID='+data.pupilID);
    };
    $scope.pageClass = 'page-app';
    if (role!='PUPIL') {
        LogOut.logout();
    }
    $scope.doc = 'Schedule';
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
            console.log(response.data.pupils);
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
    $scope.openMarkWindow = function(pupilID, event) {
        console.log(pupilID);
        $scope.curPupilID = pupilID;
        showDropdown('marklist',event.x,event.y);
    };
    $scope.setMark = function(mark) {
        console.log($scope.curPupilID);
        var params = {
            "lessonID":$scope.curLessonID,
            "pupilID":$scope.curPupilID,
            "mark":mark
        };
        $http.post('setMark',params).success(function() {
            $scope.message = 'Mark is saved.';
        }).error(function() {
            $scope.message = "Error: can't save mark.";
        });
        hideDropdown('marklist');
        showToast();
        $scope.loadLesson($scope.curLessonID);
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
schoolApp.controller('adminController', function($scope, LogOut, $location, $http) {
    $scope.logout = function() {
        LogOut.logout();
    };
    $scope.getTeachers = function() {
        $http.get('getTeachers').then(function(response) {
            $scope.teachers = response.data;
            console.log(response.data);
        });
    };
    $scope.openMainTab = function(event, tabName, tabIndex) {
        hideAll();
        switch (tabName) {
            case 'teachers': $scope.getTeachers(); break;
            case 'pupils':
                $scope.getClasses();
                $scope.getPupils(null);
                break;
            case 'subjects':
                $scope.getSubjects();
                break;
            case 'schedule':
                $scope.getClasses();
                $scope.getClasses();
                $scope.curClass = $scope.classes[0];
                $scope.curDay = {
                    name:'Monday',
                    number:0
                };
                $scope.setDay(0);
                break;
        }
        openAdminTab(event, tabName, tabIndex);
    };
    $scope.openSetTeacherWindow = function(teacher) {
        document.getElementById('setTeacherWindow').style.display = 'block';
        console.log(teacher);
        if (teacher==null) {
            $scope.teacherHeader = 'Add teacher';
            $scope.curTeacherID = null;
            $scope.curTeacherSurname = null;
            $scope.curTeacherName = null;
            $scope.curTeacherLogin = null;
            $scope.curTeacherPassword = null;
            $scope.curTeacherType = null;
        } else {
            $scope.teacherHeader = 'Edit teacher';
            $scope.curTeacherID = teacher.teacherID;
            $scope.curTeacherSurname = teacher.surname;
            $scope.curTeacherName = teacher.name;
            $scope.curTeacherLogin = teacher.login;
            $scope.curTeacherPassword = teacher.password;
            $scope.curTeacherType = teacher.type;
        }
    };
    $scope.setTeacher = function() {
        var actionName = 'editTeacher';
        if ($scope.curTeacherID==null) actionName = 'addTeacher';
        var params = {
            "teacherID":    $scope.curTeacherID,
            "login":        $scope.curTeacherLogin,
            "password":     $scope.curTeacherPassword,
            "name":         $scope.curTeacherName,
            "surname":      $scope.curTeacherSurname,
            "type":         $scope.curTeacherType
        };
        $http.post(actionName,params).success(function() {
            $scope.message = 'Saved successfully';
            document.getElementById('setTeacherWindow').style.display = 'none';
        }).error(function() {
            $scope.message = "Error: changes aren't saved";
        });
        $scope.getTeachers();
        showToast();
    };
    $scope.openDeleteTeacherWindow = function(teacher) {
        document.getElementById('deleteTeacherWindow').style.display = 'block';
        $scope.curTeacherID = teacher.teacherID;
        $scope.curTeacherSurname = teacher.surname;
        $scope.curTeacherName = teacher.name;
        $scope.curTeacherLogin = teacher.login;
        $scope.curTeacherPassword = teacher.password;
        $scope.curTeacherType = teacher.type;
    };
    $scope.deleteTeacher = function() {
        var params = {
            "teacherID":$scope.curTeacherID
        };
        $http.post('deleteTeacher',params).success(function() {
            document.getElementById('deleteTeacherWindow').style.display = 'none';
            $scope.message = 'Teacher is deleted';

        }).error(function() {
            $scope.message = "Error: teacher is not deleted";
        });
        showToast();
        $scope.getTeachers();
    };

    $scope.getClasses = function() {
        $http.get('getClasses').then(function(response) {
            $scope.classes = response.data;
        });
    };
    $scope.showClassList = function(event) {
        $scope.getClasses();
        showDropdown('classlist',event.x,event.y);
    };
    $scope.showList = function(event) {
        showDropdown('lightclasslist',event.x,event.y);
    };
    $scope.changeClassAndClose = function(curClass) {
        $scope.getPupils(curClass);
        $scope.curClass = curClass;
        if (document.getElementById('setPupilWindow').style.display=='none')
            $scope.curPupilClassName = $scope.curClassName;
        hideDropdown('classlist');
    };
    $scope.openSetClassWindow = function(curClass) {
        document.getElementById('classlist').style.display = 'none';
        if (curClass==null) return;
        if (curClass==0) {
            $scope.classHead = 'Add class';
            $scope.curClassID = null;
            $scope.curClassGrade = 0;
            $scope.curClassLetter = null;
        } else {
            $scope.classHead = 'Edit class';
            $scope.curClassID = curClass.ID;
            $scope.curClassGrade = curClass.grade;
            $scope.curClassLetter = curClass.letter;
        }
        document.getElementById('setClassWindow').style.display = 'block';
    };
    $scope.setClass = function() {
        var action = 'editClass';
        if ($scope.curClassID==null) action = 'addClass';
        var params = {
            "classID":$scope.curClassID,
            "classGrade":$scope.curClassGrade,
            "classLetter":$scope.curClassLetter
        };
        $http.post(action,params).success(function() {
            document.getElementById('setClassWindow').style.display = 'none';
            $scope.message = 'Class is saved';
            if (action=='editClass') $scope.curClassName = $scope.curClassGrade.toString() + '-' + $scope.curClassLetter;
        }).error(function () {
            $scope.message = "Error: class isn't saved";
        });
        showToast();
    };
    $scope.openDeleteClassWindow = function() {
        if ($scope.curClass==null) return;
        document.getElementById('deleteClassWindow').style.display = 'block';
    };
    $scope.deleteClass = function() {
        var params = {
            "classID":$scope.curClass.ID
        };
        $http.post('deleteClass',params).success(function() {
            document.getElementById('deleteClassWindow').style.display = 'none';
            $scope.message = "Class is deleted";
            $scope.getPupils(null);
        }).error(function() {
            $scope.message = "Error: class isn't deleted";
        });
        showToast();
    };

    $scope.getPupils = function(curClass) {
        var classID=-1;
        if (curClass!=null) {
            classID = curClass.ID;
            $scope.curClassName = curClass.grade.toString() + '-' + curClass.letter;
        } else {
            $scope.curClassName = 'Unassigned';
        }
        var params = {
            "classID":classID
        };
        $http.post('getPupils',params).then(function (response) {
            $scope.pupils = response.data;
        });
    };
    $scope.changePupilClass = function (curClass) {
        if (curClass==null) {
            $scope.curPupilClassName = 'Unassigned';
            $scope.curPupilClassID = null;
        } else {
            $scope.curPupilClassName = curClass.grade.toString() + '-' + curClass.letter;
            $scope.curPupilClassID = curClass.ID;
        }
        hideDropdown('lightclasslist');
    };
    $scope.openSetPupilWindow = function (pupil) {
        document.getElementById('setPupilWindow').style.display = 'block';
        if (pupil==null) {
            $scope.pupilHeader = 'Add pupil';
            $scope.curPupilID = null;
            $scope.curPupilClassName = 'Unassigned';
            $scope.curPupilClassID = null;
            $scope.curPupilLogin = null;
            $scope.curPupilPassword = null;
            $scope.curPupilName = null;
            $scope.curPupilSurname = null;
        } else {
            $scope.pupilHeader = 'Edit pupil';
            $scope.curPupilID = pupil.pupilID;
            $scope.curPupilClassName = $scope.curClassName;
            if ($scope.curClass == null) {
                $scope.curPupilClassID = null;
            } else {
                $scope.curPupilClassID = $scope.curClass.ID;
            }
            $scope.curPupilLogin = pupil.login;
            $scope.curPupilPassword = pupil.password;
            $scope.curPupilName = pupil.name;
            $scope.curPupilSurname = pupil.surname;
        }
    };
    $scope.setPupil = function() {
        var action = 'editPupil';
        if ($scope.curPupilID==null) action = 'addPupil';
        var params = {
            "pupilID":$scope.curPupilID,
            "name":$scope.curPupilName,
            "surname":$scope.curPupilSurname,
            "classID":$scope.curPupilClassID,
            "login":$scope.curPupilLogin,
            "password":$scope.curPupilPassword
        };
        $http.post(action,params).success(function() {
            $scope.message = "Pupil is saved";
            toggleWindow('setPupilWindow',false);
            $scope.getPupils($scope.curClass);
        }).error(function() {
            $scope.message = "Error: pupil is not saved";
        });
        showToast();
    };
    $scope.openDeletePupilWindow = function(pupil) {
        document.getElementById('deletePupilWindow').style.display = 'block';
        $scope.curPupilID = pupil.pupilID;
        $scope.curPupilLogin = pupil.login;
        $scope.curPupilPassword = pupil.password;
        $scope.curPupilName = pupil.name;
        $scope.curPupilSurname = pupil.surname;
    };
    $scope.deletePupil = function() {
        var params = {
            "pupilID":$scope.curPupilID
        };
        $http.post('deletePupil',params).success(function() {
            toggleWindow('deletePupilWindow',false);
            $scope.message = "Pupil is deleted";
            $scope.getPupils($scope.curClass);
        }).error(function () {
            $scope.message = "Error: pupil isn't deleted";
        });
        showToast();
    };

    $scope.getSubjects = function () {
        var params = {
            "all":true
        };
        $http.post('getSubjects',params).then(function(response) {
            $scope.subjects = response.data;
        });
    };
    $scope.resetSearch = function () {
        $scope.search.name = undefined;
        $scope.search.classGrade = undefined;
        $scope.search.classLetter = undefined;
        $scope.search.teacher = undefined;
    };
    $scope.showTeacherList = function(event) {
        $scope.getTeachers();
        showDropdown('teacherlist',event.x,event.y);
    };
    $scope.showSubjectClassList = function(event) {
        $scope.getClasses();
        showDropdown('subclasslist',event.x,event.y);
    };
    $scope.changeTeacherAndClose = function(teacher) {
        $scope.curTeacher = teacher;
        hideDropdown('teacherlist');
    };
    $scope.changeSubjectClass = function(curClass) {
        $scope.curSubjectClass = curClass;
        hideDropdown('subclasslist');
    };
    $scope.openSetSubjectWindow = function(subject) {
        $scope.curSubjectClass= null;
        $scope.curTeacher = null;
        if (subject==null) {
            $scope.subjectHeader = 'Add subject';
            $scope.curSubjectID = null;
            $scope.curSubjectName = null;
            $scope.curSubjectLessons = null;
        } else {
            $scope.subjectHeader = "Edit subject";
            $scope.curSubjectID = subject.subjectID;
            $scope.curSubjectName = subject.name;
            $scope.curSubjectLessons = subject.lessonCount;
        }
        toggleWindow('setSubjectWindow',true);
    };
    $scope.openDeleteSubjectWindow = function(subject) {
        $scope.delSubject = subject;
        toggleWindow('deleteSubjectWindow', true);
    };
    $scope.setSubject = function() {
        if ($scope.curTeacher==null ||
            $scope.curSubjectClass==null ||
            $scope.curSubjectName==null ||
            $scope.curSubjectLessons==null) {
                $scope.message = "Fill all properties before apply";
                showToast();
                return;
        }
        var action = 'editSubject';
        if ($scope.curSubjectID==null) action='addSubject';
        var params = {
            "subjectID":$scope.curSubjectID,
            "name":$scope.curSubjectName,
            "lessonCount":$scope.curSubjectLessons,
            "classID":$scope.curSubjectClass.ID,
            "teacherID":$scope.curTeacher.teacherID
        };
        console.log(params);
        $http.post(action,params).success(function() {
            toggleWindow('setSubjectWindow',false);
            $scope.message = "Subject is saved";
            $scope.getSubjects();
        }).error(function() {
            $scope.message = "Error: subject isn't saved";
        });
        showToast();
    };
    $scope.deleteSubject = function() {
        var params = {
            "subjectID":$scope.delSubject.subjectID
        };
        $http.post('deleteSubject',params).success(function() {
            $scope.message = "Subject is deleted";
            toggleWindow('deleteSubjectWindow', false);
            $scope.getSubjects();
        }).error(function() {
            $scope.message = "Error: subject isn't deleted";
        });
        showToast();
    };

    $scope.getSubjectsByClass = function() {
        var params = {
            "classID":$scope.curClass.ID
        };
        $http.post('getSubjectsByClass',params).then(function (response) {
            $scope.subjects = response.data;
        });
    };
    $scope.getClassDaySchedule = function() {
        var params = {
            "classID":$scope.curClass.ID,
            "day":$scope.curDay.number
        };
        $http.post('getClassDaySchedule',params).then(function(response) {
            $scope.lessons = response.data;
        })
    };
    $scope.showDayList = function(event) {
        showDropdown('daylist',event.x,event.y);
    };
    $scope.setDay = function(dayNum) {
        $scope.curDay.number = dayNum;
        switch (dayNum) {
            case 0:$scope.curDay.name = 'Monday'; break;
            case 1:$scope.curDay.name = 'Tuesday'; break;
            case 2:$scope.curDay.name = 'Wednesday'; break;
            case 3:$scope.curDay.name = 'Thursday'; break;
            case 4:$scope.curDay.name = 'Friday'; break;
            case 5:$scope.curDay.name = 'Saturday'; break;
            case 6:$scope.curDay.name = 'Sunday'; break;
        }
        hideDropdown('daylist');
        $scope.getClassDaySchedule();
    };
    $scope.showScheduleClassList = function(event) {
        $scope.getClasses();
        showDropdown('scheduleclasslist',event.x,event.y);
    };
    $scope.setScheduleClass = function(curClass) {
        $scope.curClass = curClass;
        $scope.getClassDaySchedule();
        hideDropdown('scheduleclasslist');
    };
    $scope.deleteLesson = function(lesson) {
        var params = {
            "lessonID":lesson.lessonID
        };
        $http.post('deleteLesson',params).success(function() {
            $scope.message = "Lesson is deleted";
            $scope.getClassDaySchedule();
        }).error(function() {
            $scope.message = "Error: lesson isn't deleted";
        });
        showToast();
    };
    $scope.openSetLessonWindow = function(lesson) {
        if (lesson==null) {
            $scope.lessonHeader = 'Add lesson';
            $scope.curLessonID = null;
            $scope.curLessonNumber = null;
            $scope.curAuditorium = null;
        } else {
            $scope.lessonHeader = 'Edit lesson';
            $scope.curLessonID = lesson.lessonID;
            $scope.curLessonNumber = lesson.number;
            $scope.curAuditorium = lesson.auditorium;
        }
        $scope.curScheduleSubject = null;
        toggleWindow('setLessonWindow',true);
    };
    $scope.showSubjectList = function(event) {
        $scope.getSubjectsByClass();
        showDropdown('subjectlist',event.x,event.y);
    };
    $scope.setLessonSubject = function(subject) {
        $scope.curScheduleSubject = subject;
        hideDropdown('subjectlist');
    };
    $scope.setLesson = function() {
        var action = 'editLesson';
        if ($scope.curLessonID==null) action='addLesson';
        var params = {
            "lessonID":$scope.curLessonID,
            "day":$scope.curDay.number,
            "classID":$scope.curClass.ID,
            "number":$scope.curLessonNumber,
            "auditorium":$scope.curAuditorium,
            "subjectID":$scope.curScheduleSubject.subjectID
        };
        console.log(params);
        $http.post(action,params).success(function() {
            $scope.message = "Lesson is saved";
            $scope.getClassDaySchedule();
            toggleWindow('setLessonWindow',false);
        }).error(function () {
            $scope.message = "Error: lesson isn't saved";
        });
        showToast();
    };
    $scope.pageClass = 'page-app';
    if (role!='ADMIN') {
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
function hideDropdown(id) {
    document.getElementById(id).style.display = 'none';
    document.getElementById(id).style.top = 'none';
    document.getElementById(id).style.left = 'none';
    document.getElementById(id).style.bottom = 'none';
    document.getElementById(id).style.right = 'none';
}
function showDropdown(id,x,y) {
    document.getElementById(id).style.display = 'block';
    if (x>window.innerWidth/2) {
        document.getElementById(id).style.right = window.innerWidth-x;
    } else {
        document.getElementById(id).style.left = x;
    }
    if (y>window.innerHeight/2) {
        document.getElementById(id).style.bottom = window.innerHeight-y;
    } else {
        document.getElementById(id).style.top = y;
    }
}
function hideAll() {
    //Make it!!!
}