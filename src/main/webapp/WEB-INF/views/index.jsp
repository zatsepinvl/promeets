<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!-- Page body-->
<div class="mdl-grid" style="padding: 0">
    <div class="mdl-cell mdl-cell--12-col">
        <div class="mdl-card mdl-shadow--2dp" style="width: 100%">

            <!-- Main info tabs -->
            <md-tabs md-dynamic-height md-border-bottom>

                <!-- Groups tab -->
                <md-tab label="Groups">
                    <md-content class="md-padding">
                        <h1 class="md-display-2">Groups</h1>
                        <p></p>
                    </md-content>
                </md-tab>

                <!-- Meetings tab -->
                <md-tab label="Meetings">
                    <md-content class="md-padding">
                        <h1 class="md-display-2">Meetings</h1>
                        <p></p>
                    </md-content>
                </md-tab>

                <!-- Board tab -->
                <md-tab label="Board">
                    <md-content class="md-padding">
                        <h1 class="md-display-2">Board</h1>
                        <p></p>
                    </md-content>
                </md-tab>
            </md-tabs>
            <div layout="row" style="margin: 16px;">
                <md-button class="md-raised"
                           style="background-color: #2e7d32; color:whitesmoke; margin: 0 0 0 auto"
                           ng-click="signUp()">
                    Sign up
                </md-button>
            </div>
        </div>
    </div>


</div>
