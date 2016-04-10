app.controller('groupCalendarCtrl', function ($scope,
                                              Entity,
                                              $http,
                                              $stateParams,
                                              $state,
                                              GroupService,
                                              UserService,
                                              MeetEditDialogService,
                                              EventHandler) {
    $scope.events = [];
    $scope.selected = [];
    $scope.meet = {group: {groupId: $stateParams.groupId}, time: moment().day(0).minute(0).millisecond(0)};
    $scope.selectedDay = moment();

    $scope.loadByMonth = function (date) {
        $scope.events = [];
        $http.get("/api/groups/" + $stateParams.groupId + "/meets/month/" + date)
            .success(function (meets) {
                meets.forEach(function (meet) {
                    meet.time = moment(meet.time).local();
                    $scope.events.push(meet);
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
        $scope.selectedDay = day.date;
        $scope.meet.time = day.date;
        $scope.selected = day.events;
        console.log($scope.selected);
    };

    $scope.meetClicked = function (meetId) {
        $state.transitionTo("user.venue", {meetId: meetId});
    };

    $scope.createMeet = function (event) {
        var meet = {time: $scope.selectedDay, group: GroupService.get()};
        MeetEditDialogService.show(meet, event,
            function (result) {
                var meet = result;
                var tempTime = {};
                clone(meet.time, tempTime);
                meet.time = meet.time.valueOf();
                meet.admin = UserService.get();
                EventHandler.load('Saving meeting');
                Entity.save({entity: "meets"}, meet,
                    function (data) {
                        meet = data;
                        meet.time = tempTime;
                        $scope.events.push(meet);
                        EventHandler.message('Meeting has been created');
                    },
                    function (error) {
                        EventHandler.message(error.data.message);
                    })
            },
            function () {
            }
        )
    };

    $scope.deleteMeet = function (meet) {
        EventHandler.action('Removing meet', 'UNDO', function () {
            },
            function () {
                Entity.remove({entity: "meets", id: meet.meetId}, function () {
                        EventHandler.message('Meet has been removed');
                        $scope.events.splice($scope.events.indexOf(meet), 1);
                        $scope.selected.splice($scope.selected.indexOf(meet), 1);
                    },
                    function (error) {
                        EventHandler.message(error.data.message);
                    });

            });
    }
});


