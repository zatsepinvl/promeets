<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<div class="mdl-card mdl-card mdl-shadow--2dp" style="width: 100%; margin: 2px" ng-controller="groupCtrl">
    <!-- Group header-->
    <div class="mdl-grid" style="width: 100%; margin-top:0; margin-bottom: 0; padding: 0">

        <!-- Group name and description-->
        <div class="mdl-cell mdl-cell--8-col mdl-cell--5-col-tablet mdl-cell--4-col-phone">
            <form name="groupForm" style="padding: 8px">
                <div ng-show="!edit_header_mode" style="font-size: 26px; font-weight: 200"
                     ng-mouseover="header_icon = true;" ng-mouseleave="header_icon = false;">
                    {{group.title}}
                    <i class="material-icons edit-mode" ng-click="switchHeaderMode(true)"
                       ng-show="header_icon">create</i>
                </div>
                <md-input-container class="md-block" md-no-float ng-show="edit_header_mode" style="width:100%;">
                    <input ng-blur="switchHeaderMode(false)" placeholder="Title" ng-model="group.title"
                           name="title" required>
                    <div ng-messages="groupForm.title.$error">
                        <div ng-message="required">This is required.</div>
                    </div>
                </md-input-container>

                <!--Group type-->
                <div ng-show="!edit_type_mode"
                     style="font-size:14px; font-weight: 100; color:#ef6c00;"
                     ng-click="edit_type_mode=true;" ng-mouseover="type_icon = true;"
                     ng-mouseleave="type_icon = false;">
                    {{group.type.name}} group
                    <i class="material-icons edit-mode" ng-show="type_icon">create</i>
                </div>

                <md-input-container class="md-block" flex-gt-xs style="width:45%;" ng-show="edit_type_mode">
                    <label>Group type</label>
                    <md-select ng-change="edit_type_mode=false;" ng-model="group.type.name" id="group_type"
                               style="width: 200px;">
                        <md-option value="Public">
                            Public
                        </md-option>
                        <md-option value="Private">
                            Private
                        </md-option>
                    </md-select>
                </md-input-container>
                <!--end -->

                <div ng-show="!edit_status_mode"
                     class="mdl-cell mdl-cell--12-col mdl-cell--8-col-tablet mdl-cell--4-col-phone"
                     ng-mouseover="status_icon = true;" ng-mouseleave="status_icon = false;"
                     style="margin-left:0px;">
                    {{group.status}}
                    <i class="material-icons edit-mode" ng-click="switchStatusMode(true)"
                       ng-show="status_icon">create</i>
                </div>
                <md-input-container class="md-block" md-no-float ng-show="edit_status_mode" style="width:100%;">
                    <input ng-blur="switchStatusMode(false)" ng-model="group.status" placeholder="Status">
                </md-input-container>
            </form>
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

    <!-- Current meet-->
    <div class="current_meet">
        <i class="material-icons"
           style=" margin-bottom:4px; line-height: 40px; vertical-align: middle">group</i>
        Current meet
    </div>

    <!-- Group menu-->
    <div class="mdl-grid" style="width: 100%; padding: 0">
        <div class="mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
            <a  ng-click="tab='meets'" style="text-decoration: none;">
                <div class="menu_item">
                    Meets
                </div>
            </a>
        </div>
        <div class="mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
            <a  ng-click="tab='files'" style="text-decoration: none;">
                <div class="menu_item ">
                    Files
                </div>
            </a>
        </div>
        <div class="mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
            <a  ng-click="tab='members'" style="text-decoration: none;">
                <div class="menu_item">
                    Members
                </div>
            </a>
        </div>
        <div class="mdl-cell mdl-cell--3-col mdl-cell--2-col-tablet mdl-cell--2-col-phone">
            <a ng-click="tab='chat'" style="text-decoration: none;">
                <div class="menu_item">
                    Chat
                </div>
            </a>
        </div>
    </div>
    <md-divider></md-divider>
    <!-- Group content -->
    <ng-include src="'../templates/group/'+tab+'.html'"></ng-include>
</div>
