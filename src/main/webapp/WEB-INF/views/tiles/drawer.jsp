<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="mdl-layout__drawer" ng-controller="drawerCtrl">
    <header>
        <img src="image/face.png"
             style="width:50px; height: 50px;"
             class="md-avatar"/>
        <div class="username">John Connor</div>
        <div class="email">some1@mail.com</div>
    </header>
    <nav class="mdl-navigation">
        <a class="mdl-navigation__link" href="/profile"> <i class="material-icons">face</i>Profile</a>
        <a class="mdl-navigation__link" href="/group"> <i class="material-icons icon">group_work</i>Groups</a>
        <a class="mdl-navigation__link" href="/calendar"> <i class="material-icons icon">date_range</i>Calendar</a>
        <a class="mdl-navigation__link" href="/message"> <i class="material-icons icon"> message</i><span
                class="mdl-badge"
                data-badge="4">Messages</span></a>
        <a class="mdl-navigation__link " href="/settings"> <i class="material-icons">settings</i>Settings</a>
        <a class="mdl-navigation__link " href="" ng-click="logout()"> <i class="material-icons">exit_to_app</i>Log out</a>
    </nav>
</div>
