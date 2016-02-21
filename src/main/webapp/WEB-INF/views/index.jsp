<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Promeets</title>

    <meta name="description" content="Promeets. We help you to arrange meetings.">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- Style -->
    <link href="<c:url value="css/style.css"/>" rel="stylesheet"/>

    <!-- Material Styles -->
    <link href="<c:url value="lib/material/material.min.css"/>" rel="stylesheet"/>
    <link href="<c:url value="lib/material/fonts_icons.css"/>" rel="stylesheet"/>
    <link href="<c:url value="lib/material/material_fonts.css"/>" rel="stylesheet"/>

    <!-- Angular Material Style  -->
    <link href="<c:url value="lib/angular_material/angular.material.min.css"/>" rel="stylesheet"/>


</head>
<body ng-app="app" ng-cloak>
<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
    <header class="mdl-layout__header mdl-layout__header--scroll" style="background: #2e7d32">
        <img class=" mdl-layout-icon " src="image/face.png"/>
        <div class="mdl-layout__header-row">
            <span class="mdl-layout-title mdl-layout--large-screen-only">Promeets</span>

            <div class="mdl-layout-spacer">
            </div>

            <nav class="mdl-navigation">
                <a class="mdl-navigation__link " href="test.html" style="padding:0"> <i class="material-icons"
                                                                                        style=" line-height:64px;vertical-align: middle; margin-bottom: 4px">settings</i>
                    <span class="mdl-layout--large-screen-only">Settings</span>
                </a>
                <a class="mdl-navigation__link " href="#"><img src="image/face.png"
                                                               style="width:50px; height: 50px;"
                                                               class="md-avatar"/>
                </a>
            </nav>
        </div>
    </header>

    <!-- Page content -->
    <main class="mdl-layout__content" style="background-color: #f5f5f5">
        <div class="page-content">

            <div class="mdl-grid">

                <!-- Navigation menu-->
                <div class="mdl-cell mdl-cell--2-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
                    <div class="mdl-card mdl-card mdl-shadow--2dp"
                         style="width: 100%; margin: 2px;height: auto; min-height: 0;">
                        <div class="mdl-grid" style="width: 100%">
                            <div class="mdl-cell mdl-cell--10-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
                                <a href="#/test" style="text-decoration: none;">
                                    <div class="nav_menu_item ">
                                        <i class="material-icons"
                                           style="line-height: 100%; vertical-align: middle; margin-top:-4px">account_circle</i>
                                        Profile
                                    </div>
                                </a>
                            </div>
                            <div class="mdl-cell mdl-cell--10-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
                                <a href="#" style="text-decoration: none;">
                                    <div class="nav_menu_item ">
                                        <i class="material-icons"
                                           style="line-height: 100%; vertical-align: middle; margin-top:-4px">group_work</i>
                                        Groups
                                    </div>
                                </a>
                            </div>
                            <div class="mdl-cell mdl-cell--10-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
                                <a href="#" style="text-decoration: none;">
                                    <div class="nav_menu_item ">
                                        <i class="material-icons"
                                           style="line-height: 100%; vertical-align: middle; margin-top:-4px">date_range</i>
                                        Calendar
                                    </div>
                                </a>
                            </div>
                            <div class="mdl-cell mdl-cell--10-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
                                <a href="#" style="text-decoration: none;">
                                    <div class="nav_menu_item ">
                                        <i class="material-icons"
                                           style="line-height: 100%; vertical-align: middle; margin-top:-2px">message</i>
                                        Messages
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Content container-->
                <div class="mdl-cell mdl-cell--8-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
                    <div ng-view>

                    </div>
                </div>
                <!-- Maybe some advertisement
                <div class="mdl-cell mdl-cell--2-col mdl-cell--8-col-tablet mdl-cell--4-col-phone">
                    <div class="mdl-card mdl-card mdl-shadow--2dp" css="width: 100%; margin: 2px">
                        CS3
                    </div>
                </div>
                -->
            </div>
        </div>
    </main>
</div>

</body>

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
<!--Entities = data model -->
<script src="<c:url value='js/entities.js'/>"></script>
</html>
