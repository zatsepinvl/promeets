<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<div ng-controller="groupCtrl" ng-cloak>
    <div class="mdl-card mdl-card mdl-shadow--2dp" style="width: 100%; margin: 2px">
        <!-- Group header-->
        <div class="mdl-grid" style="width: 100%; margin-top:0; margin-bottom: 0; padding: 0">

            <!-- Group name and description-->
            <div class="mdl-cell mdl-cell--8-col mdl-cell--5-col-tablet mdl-cell--4-col-phone">
                <div style="margin:10px 0 0 10px">
                    <div ng-show="!edit_header_mode" style="font-size: 26px; font-weight: 200; ">
                        {{group.title}}
                    </div>
                    <!--Group type-->
                    <div style="font-size:14px; font-weight: 100; color:#2e7d32; margin:4px 0 4px 0">
                        {{group.type.name+" group"}}
                    </div>
                    {{group.status}}
                </div>
            </div>

            <!-- Group logo-->
            <div class="mdl-cell mdl-cell--4-col mdl-cell--3-col-tablet mdl-cell--4-col-phone img-teaser"
                 style="position:relative; display:table;">
                <img style="width: 100%; margin: auto" src="../image/group.jpg"/>
                <button class="figcaption mdl-button mdl-js-button mdl-button--fab mdl-button--mini-fab mdl-button--primary mdl-js-ripple-effect">
                    <i class="material-icons">file_download</i>
                </button>
            </div>
        </div>

        <!-- Group action menu -->
        <div class="mdl-cell mdl-cell--12-col" style="padding: 0; margin: 0 4px 0 0">
            <p style="text-align: right; color: #ef6c00; cursor: pointer">
                <i class="material-icons icon" ng-click="editGroup()">
                    <md-tooltip md-direction="left">
                        Edit group
                    </md-tooltip>
                    edit
                </i>
                <i class="material-icons icon">
                    <md-tooltip md-direction="right">
                        Leave group
                    </md-tooltip>
                    exit_to_app</i>
            </p>
        </div>

        <!-- Current meet-->
        <div class="current_meet">
            <i class="material-icons"
               style=" margin-bottom:4px; line-height: 40px; vertical-align: middle">group</i>
            Current meet
        </div>

        <!-- Group elements menu-->
        <div class="mdl-grid" style="width: 100%; padding: 0">
            <div class="mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
                <a ng-click="tab='meets'" style="text-decoration: none;">
                    <div class="group_menu_item">
                        Meets
                    </div>
                </a>
            </div>
            <div class="mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
                <a ng-click="tab='files'" style="text-decoration: none;">
                    <div class="group_menu_item ">
                        Files
                    </div>
                </a>
            </div>
            <div class="mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
                <a ng-click="tab='members'" style="text-decoration: none;">
                    <div class="group_menu_item">
                        Members
                    </div>
                </a>
            </div>
            <div class="mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
                <a ng-click="tab='chat'" style="text-decoration: none;">
                    <div class="group_menu_item">
                        Chat
                    </div>
                </a>
            </div>
        </div>
        <md-divider></md-divider>
        <!-- Group content -->
        <ng-include src="'../templates/group/'+tab+'.html'"></ng-include>
    </div>
</div>