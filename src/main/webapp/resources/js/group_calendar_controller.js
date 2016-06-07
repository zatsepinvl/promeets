app.controller('groupCalendarCtrl', function ($scope,
                                              $rootScope,
                                              Entity,
                                              UserEntity,
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
    $scope.selectedDay = $stateParams.selected ? moment(parseInt($stateParams.selected)).local() : moment().local();
    $scope.user = UserService.get();

    $scope.events = GroupMeetsService.current();
    $scope.state = GroupMeetsService.getState();


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
        day.events.forEach(function (value) {
            if (!value.viewed) {
                var temp = {};
                clone(value, temp);
                temp.viewed = true;
                UserEntity.update({entity: "meets", id: value.meet.meetId}, temp,
                    function () {
                        value.viewed = true;
                        day.notViewed = false;
                        $rootScope.$emit('usermeetLocal', {
                            action: appConst.ACTION.UPDATE,
                            data: value,
                            id: value.meet.meetId,
                            entity: 'usermeet'
                        });
                    },
                    function (error) {
                        EventHandler.error(error.data.message);
                    });
            }
        });
    };

    $scope.meetClicked = function (meetId) {
        $state.transitionTo("user.venue", {meetId: meetId});
    };

    $scope.createMeet = function (event) {
        var meet = {
            time: $scope.selectedDay,
            group: GroupService.get()
        };
        MeetEditDialogService.show(meet, event,
            function (result) {
                var meet = result;
                var userMeet = {user: $scope.user, meet: meet, viewed: true};
                meet.admin = UserService.get();
                UserEntity.save({entity: "meets"}, userMeet,
                    function (data) {
                        userMeet = data;
                        userMeet.meet.time = moment(userMeet.meet.time).local();
                        $scope.events.push(userMeet);
                        if ($scope.selectedDay.isSame(userMeet.meet.time, 'day')) {
                            $scope.selected.push(userMeet);
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

    $scope.deleteMeet = function (userMeet, event) {
        var meet = userMeet.meet;
        ConfirmDialog.show('Delete meet?', 'Delete', 'Cancel', event,
            function () {
                UserEntity.remove({entity: "meets", id: meet.meetId}, function () {
                        EventHandler.message('Meet has been deleted');
                        $scope.events.splice($scope.events.indexOf(userMeet), 1);
                        $scope.selected.splice($scope.selected.indexOf(userMeet), 1);
                    },
                    function (error) {
                        EventHandler.message('Something went wrong. Try again later.');
                    });
            });
    };

    $scope.$on('usermeet', function (event, message) {
        if (message.action == appConst.ACTION.CREATE) {
            var meetTime  = moment(message.data.meet.time);
            if (meetTime.isSame($scope.selectedDay, 'month')) {
                $scope.events.push(message.data);
            } else if (meetTime.isSame($scope.selectedDay.clone().add(1, 'month'), 'month')) {
                GroupMeetsService.getNext().push(message.data);
            }
            else if (meetTime.isSame($scope.selectedDay.clone().add(-1, 'month'), 'month')) {
                GroupMeetsService.getPrev().push(message.data);
            }

        }
    });

    $rootScope.$on('$stateChangeStart',
        function () {
            GroupMeetsService.current().length = 0;
            GroupMeetsService.getNext().length = 0;
            GroupMeetsService.getPrev().length = 0;
        })
});


