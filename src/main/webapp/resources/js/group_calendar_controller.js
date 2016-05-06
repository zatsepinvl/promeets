app.controller('groupCalendarCtrl', function ($scope,
                                              Entity,
                                              $http,
                                              $stateParams,
                                              $state,
                                              GroupService,
                                              UserService,
                                              MeetEditDialogService,
                                              ConfirmDialog,
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
    };

    $scope.meetClicked = function (meetId) {
        $state.transitionTo("user.venue", {meetId: meetId});
    };

    $scope.createMeet = function (event) {
        var meet = {time: $scope.selectedDay, group: GroupService.get()};
        MeetEditDialogService.show(meet, event,
            function (result) {
                var meet = result;
                meet.admin = UserService.get();
                Entity.save({entity: "meets"}, meet,
                    function (data) {
                        meet = data;
                        meet.time = moment(meet.time).local();
                        $scope.events.push(meet);
                        if($scope.selectedDay.isSame(meet.time,'day'))
                        {
                            $scope.selected.push(meet);
                        }
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

    $scope.deleteMeet = function (meet, event) {
        ConfirmDialog.show('Delete meet?', 'Delete', 'Cancel', event,
            function () {
                Entity.remove({entity: "meets", id: meet.meetId}, function () {
                        EventHandler.message('Meet has been deleted');
                        $scope.events.splice($scope.events.indexOf(meet), 1);
                        $scope.selected.splice($scope.selected.indexOf(meet), 1);
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


