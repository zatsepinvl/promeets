<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<div class="mdl-card mdl-card mdl-shadow--2dp"
     style="width: 100%; margin: 2px;height: auto; min-height: 0;">
    <div class="mdl-grid" style="width: 100%">
        <div class="mdl-cell mdl-cell--10-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
            <a href="/profile" style="text-decoration: none;">
                <div class="nav_menu_item ">
                    <i class="material-icons icon">account_circle</i>
                    Profile
                </div>
            </a>
        </div>
        <div class="mdl-cell mdl-cell--10-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
            <a href="/group" style="text-decoration: none;">
                <div class="nav_menu_item ">
                    <i class="material-icons icon">group_work</i>
                    Groups
                </div>
            </a>
        </div>
        <div class="mdl-cell mdl-cell--10-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
            <a href="/calendar" style="text-decoration: none;">
                <div class="nav_menu_item ">
                    <i class="material-icons icon">date_range</i>
                    Calendar
                </div>
            </a>
        </div>
        <div class="mdl-cell mdl-cell--10-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
            <a href="/messages" style="text-decoration: none;">
                <div class="nav_menu_item ">
                    <i class="material-icons icon">message</i>
                    Messages
                </div>
            </a>
        </div>
    </div>
</div>