<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<header class="mdl-layout__header mdl-layout__header--scroll" style="background: #2e7d32;" ng-controller="headerCtrl"
        ng-cloak>
    <img class=" mdl-layout-icon " src="../image/promeets_icon.png"/>
    <div class="mdl-layout__header-row">
        <span class="mdl-layout-title mdl-layout--large-screen-only">Promeets</span>
        <div class="mdl-layout-spacer"></div>
        <nav class="mdl-navigation">
            <div ng-show="tab=='nav_log'">
                <a class="mdl-navigation__link" href="/group">Profile</a>
            </div>
            <div ng-show="tab=='nav_unlog'">
                <a class="mdl-navigation__link" href="" ng-click="login()">Log in</a>
            </div>
        </nav>
    </div>
</header>

