app.controller('groupCalendarCtrl', function ($scope,
                                              Entity,
                                              $http,
                                              $stateParams,
                                              $state,
                                              GroupService,
                                              UserService,
                                              MeetEditDialogService,
                                              EventHandler,
                                              GroupMeetsService,
                                              appConst) {
    $scope.events = [];
    $scope.selected = [];
    $scope.meet = {group: {groupId: $stateParams.groupId}, time: moment().day(0).minute(0).millisecond(0)};
    $scope.selectedDay = moment();

    $scope.events = GroupMeetsService.current();


    $scope.nextMonth = function () {
        $scope.events = GroupMeetsService.next();
    };

    $scope.prevMonth = function () {
        $scope.events = GroupMeetsService.previous();
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
                        if (meet.time.isSame($scope.selected, 'day')) {
                            $scope.selected.splice($scope.selected.indexOf(meet), 1);
                        }
                    },
                    function (error) {
                        EventHandler.message('Something went wrong. Try again later.');
                    });

            });
    };

    $scope.$on('meet', function (event, data) {
        if (data.action == appConst.ACTION.CREATE) {
            Entity.get({entity: 'meets', id: data.id},
                function (meet) {
                    meet.time = moment(meet.time).local();
                    EventHandler.action('New meet on ' + meet.time.format(appConst.TIME_FORMAT.DAY), 'CLOSE');
                    if (meet.time.isSame($scope.selectedDay, 'month')) {
                        $scope.events.push(meet);
                    } else if (meet.time.isSame($scope.selectedDay.clone().add(1, 'month'), 'month')) {
                        GroupMeetsService.getNext().push(meet);
                    }
                    else if (meet.time.isSame($scope.selectedDay.clone().add(-1, 'month'), 'month')) {
                        GroupMeetsService.getPrev().push(meet);
                    }
                });
        }
    });
});


