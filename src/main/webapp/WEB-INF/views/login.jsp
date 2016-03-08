<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div ng-controller="loginCtrl">
    <div class="mdl-grid" style="padding: 0;">
        <div class="mdl-cell mdl-cell--4-col"></div>
        <div class="mdl-cell mdl-cell--4-col" style="margin-top: 0;">
            <!-- <div class="mdl-layout--large-screen-only" style=" height:1px; width:1px; margin-top: 100px" ></div>
            -->
            <div class="mdl-card mdl-shadow--2dp" style="width: 100%; ">
                <md-content layout-padding>
                    <form name="loginForm" ng-submit="login()" style="margin: 0">
                        <md-input-container class="md-block">
                            <label>Email</label>
                            <md-icon class="material-icons">email</md-icon>
                            <input ng-model="user.email" type="email" required>
                        </md-input-container>
                        <md-input-container class="md-block">
                            <label>Password</label>
                            <md-icon class="material-icons">lock</md-icon>
                            <input ng-model="user.password" type="password" required>
                        </md-input-container>

                        <div layout="row">
                            <button type="submit" ng-hide="loading"
                                    class="mdl-button mdl-js-button mdl-button--raised  mdl-button--primary"
                                    flex style="background-color: #2e7d32">
                                Sign in
                            </button>
                        </div>
                        <p style="text-align: center; color:darkred; margin-top: 10px; " ng-show="error">
                            Invalid email or password.
                        </p>
                        <md-progress-circular style="margin: auto" md-mode="indeterminate"
                                              ng-show="loading"></md-progress-circular>
                    </form>
                </md-content>
            </div>
        </div>
    </div>
</div>