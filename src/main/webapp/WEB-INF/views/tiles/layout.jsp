<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
    <title>Promeets</title>

    <meta name="description" content="Promeets. We help you to arrange meetings.">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <base href="/">

    <!-- Material Styles -->
    <link href="<c:url value="lib/material/material.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="lib/material/fonts_icons.css"/>" rel="stylesheet"/>
    <link href="<c:url value="lib/material/material_fonts.css"/>" rel="stylesheet"/>

    <!-- Angular Material Style  -->
    <link href="<c:url value="lib/angular_material/angular.material.min.css"/>" rel="stylesheet"/>

    <!-- Style -->
    <link href="<c:url value="css/style.css"/>" rel="stylesheet"/>
</head>

<body ng-app="app" ng-cloak ng-controller="appCtrl" ng-show="pageState.load">
<div class="<tiles:insertAttribute name="layout_container_class"/>" style="background-color: #f5f5f5">
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="body"/>
    <tiles:insertAttribute name="footer"/>
</div>


<!-- Material Script -->
<script src="<c:url value='lib/material/material.min.js'/>"></script>

<!-- Angular js -->
<script src="<c:url value='lib/angular/angular.min.js'/>"></script>
<script src="<c:url value='lib/angular/angular-resource.min.js'/>"></script>
<script src="<c:url value='lib/angular/angular-animate.min.js'/>"></script>
<script src="<c:url value='lib/angular/angular-aria.min.js'/>"></script>
<script src="<c:url value='lib/angular/angular-route.min.js'/>"></script>

<!-- Angular Material Script -->
<script src="<c:url value='lib/angular_material/angular-material.min.js'/>"></script>

<!-- App context -->
<script src="<c:url value='js/app_controller.js'/>"></script>
<script src="<c:url value='js/header_controller.js'/>"></script>
<script src="<c:url value='js/drawer_controller.js'/>"></script>
<script src="<c:url value='js/home_controller.js'/>"></script>
<script src="<c:url value='js/login_controller.js'/>"></script>
<script src="<c:url value='js/signup_controller.js'/>"></script>
<script src="<c:url value='js/gsignup_controller.js'/>"></script>
<script src="<c:url value='js/group_controller.js'/>"></script>
<script src="<c:url value='js/meet_controller.js'/>"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.2.8/angular-ui-router.min.js"></script>

</body>
</html>