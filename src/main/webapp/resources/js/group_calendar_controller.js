app.controller('groupCalendarCtrl', function ($scope, Entity, $http, $stateParams, $mdToast) {
    $scope.events = [];
    $scope.selectedMeets = [];
    $scope.meet = {group: {groupId: $stateParams.groupId}, time: moment()};
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
    $scope.loadByMonth(new Date().getTime());
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

    $scope.createMeet = function () {
        if ($scope.createMeetForm.$valid) {
            var meet = $scope.meet;
            console.log($scope.time);
            var tempTime = meet.time.hour($scope.time.getHours()).minute($scope.time.getMinutes()).second(0).millisecond(0);
            meet.time = tempTime.utc().valueOf();
            console.log(meet);
            Entity.save({entity: "meets"}, meet, function () {
                $mdToast.show(
                    $mdToast.simple()
                        .textContent('Meeting has been created.')
                        .position('right bottom')
                        .hideDelay(3000)
                        .action('CLOSE')
                );
                meet.time = tempTime.local();
                $scope.events.push({meet: meet, time: meet.time});

            });

        }
    };

});


