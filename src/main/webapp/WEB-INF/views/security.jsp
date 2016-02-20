<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<html ng-app="securityApp" ng-cloak>
<head>
    <title>Login form</title>
</head>
<body ng-controller="securityCtrl">
<form role="form" ng-submit="login()" ng-hide="logi">
    <h2>Try to get secured data throw REST and Angular!</h2>
    <label>
        Username:
        <input type="text" name="username" ng-model="credentials.user">
    </label>
    <br>
    <br>
    <label>
        Password:
        <input type="password" name="password" ng-model="credentials.password">
    </label>
    <br>
    <br>
    <button type="submit">Login</button>
</form>
<div ng-show="logi">
    <h1>Yes!</h1>
    <H2>Secured location: {{data.location}}</H2>
    <H2>You: {{data.you}}</H2>
</div>
</body>
<!-- Angular js -->
<script src="<c:url value='lib/angular/angular.min.js'/>"></script>
<script src="<c:url value='lib/angular/angular-resource.min.js'/>"></script>
<script src="<c:url value='lib/angular/angular-animate.min.js'/>"></script>
<script src="<c:url value='lib/angular/angular-aria.min.js'/>"></script>
<script src="<c:url value='lib/angular/angular-route.min.js'/>"></script>

<!-- App context -->
<script src="<c:url value='js/security_controller.js'/>"></script>
</html>
