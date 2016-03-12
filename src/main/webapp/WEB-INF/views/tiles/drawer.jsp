<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="mdl-layout__drawer">
    <span class="mdl-layout-title">Menu</span>
    <nav class="mdl-navigation">
        <a class="mdl-navigation__link" href="/profile"> <i class="material-icons">face</i> Profile</a>
        <a class="mdl-navigation__link" href="/group"> <i class="material-icons icon">group_work</i>Groups</a>
        <a class="mdl-navigation__link" href="/calendar"> <i class="material-icons icon">date_range</i>Calendar</a>
        <a class="mdl-navigation__link" href="/message"> <i class="material-icons icon">message</i>Messages</a>
    </nav>
</div>
