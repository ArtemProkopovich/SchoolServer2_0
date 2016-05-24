<%--
  Created by IntelliJ IDEA.
  User: Евгений
  Date: 30.04.2016
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html ng-app="schoolApp">
  <head>
    <script src="http://code.angularjs.org/1.3.3/angular.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.3.3/angular-route.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.3/angular-animate.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.3/angular-resource.js"></script>
    <script src="controller.js"></script>
    <link rel="stylesheet" type="text/css" href="pages/SchoolStyle.css">
    <title>School Server</title>
  </head>
  <body>
    <div class="page {{ pageClass }}" ng-view></div>
  </body>
</html>
