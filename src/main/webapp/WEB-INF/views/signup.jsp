<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div ng-controller="signUpCtrl">
    {{user={}; user.firstName="${userFirstName}"; console.log(${userFirstName})}}
    <div class="mdl-grid" style="padding: 0">
        <div class="mdl-cell mdl-cell--3-col mdl-cell--1-col-tablet mdl-cell--4-col-phone">
        </div>
        <div class="phone mdl-cell mdl-cell--6-col mdl-cell--3-col-table mdl-cell--4-col-phone" style="margin-top: 0;">
            <div class="mdl-card mdl-shadow--2dp" style="width: 100%">
                <div layout="column">
                    <md-content layout-padding>
                        <div class="mdl-card__title">
                            Sign up form
                        </div>
                        <div class="mdl-card__menu">
                            <button type="button" class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect"
                                    style="bottom: 14px"
                                    ng-click="cancel()">
                                <i class="material-icons">close</i>
                            </button>
                        </div>

                        <table>

                            <tbody>
                            <!-- First name -->
                            <tr>
                                <td class="label">First name:</td>
                                <td>
                                    <md-input-container class="md-block" md-no-float>
                                        <input ng-model="user.firstName" type="text" required placeholder="First name">
                                    </md-input-container>
                                </td>
                            </tr>

                            <!-- Last name -->
                            <tr>
                                <td class="label">Last name:</td>
                                <td>
                                    <md-input-container class="md-block" md-no-float>
                                        <input ng-model="user.lastName" type="text" required placeholder="Last name">
                                    </md-input-container>
                                </td>
                            </tr>

                            <!-- Email -->
                            <tr>
                                <td class="label">Email:</td>
                                <td>
                                    <md-input-container class="md-block" md-no-float>
                                        <input ng-model="user.email" type="email" required placeholder="Email">
                                    </md-input-container>
                                </td>
                            </tr>


                            <!-- Password -->
                            <tr>
                                <td class="label">Password:</td>
                                <td>
                                    <md-input-container class="md-block" md-no-float>
                                        <input ng-model="user.password" type="password" required placeholder="Password">
                                    </md-input-container>
                                </td>
                            </tr>

                            <!-- Confirm Password -->
                            <tr>
                                <td class="label">Confirm <br>password:</td>
                                <td>
                                    <md-input-container class="md-block" md-no-float>
                                        <input ng-model="password" type="password" required
                                               placeholder="Confirm password">
                                    </md-input-container>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div layout="row" style="margin-top: 4px">
                            <md-button type="submit" class="md-raised" ng-hide="loading"
                                       flex style="background-color: #2e7d32; color: #fff;">
                                Sign up
                            </md-button>
                        </div>
                        <p style="text-align: center; color:darkred; margin-top: 10px; " ng-show="error.show">
                            {{error.value}}
                        </p>
                    </md-content>
                </div>
            </div>
        </div>
    </div>
</div>



