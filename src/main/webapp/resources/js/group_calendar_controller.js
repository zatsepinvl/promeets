app.controller('groupCalendarCtrl', function ($scope, Entity, $http, $stateParams, $mdToast, $state, EventHandler) {
    $scope.events = [];
    $scope.selectedMeets = [];
    $scope.meet = {group: {groupId: $stateParams.groupId}, time: moment().day(0).minute(0).millisecond(0)};
    $scope.$on('newMeet', function (event, meet) {
        $scope.meets.push(meet);
    });

    $scope.loadByMonth = function (date) {
        $scope.events = [];
        $http.get("/api/groups/" + $stateParams.groupId + "/meets/month/" + date)
            .success(function (meets) {
                meets.forEach(function (meet) {
                    meet.time = moment(meet.time).local();
                    $scope.events.push({meet: meet, time: meet.time});
                });
            })
            .error(function (error) {
                console.log(error);
            });
    };
    $scope.loadByMonth(moment().day(0).minute(0).millisecond(0));
    $scope.change = function (date) {
        $scope.loadByMonth(date.format('x'));
    };


    $scope.dayClicked = function (day) {
        $scope.meet.time = day.date;
        $scope.selectedMeets = [];
        day.events.forEach(function (event) {
            $scope.selectedMeets.push(event.meet);
        });
        console.log($scope.selectedMeets);
    };

    $scope.meetClicked = function (meetId) {
        $state.transitionTo("user.venue", {meetId: meetId});
    };

    $scope.createMeet = function () {
        if ($scope.createMeetForm.$valid) {
            var meet = $scope.meet;
            console.log($scope.time);
            var tempTime = meet.time.hour($scope.time.getHours()).minute($scope.time.getMinutes()).second(0).millisecond(0);
            meet.time = tempTime.utc().valueOf();
            console.log(meet);
            Entity.save({entity: "meets"}, meet, function () {
                EventHandler.show("Meet has been created");
                meet.time = tempTime.local();
                $scope.events.push({meet: meet, time: meet.time});
            });

        }
    };

});


