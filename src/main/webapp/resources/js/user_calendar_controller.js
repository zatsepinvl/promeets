app.controller('userCalendarCtrl', function ($scope, $rootScope, appConst, Entity, ConfirmDialog, $state, UserCalendarService, UserEntity, EventHandler) {
    $scope.events = UserCalendarService.current();
    $scope.state = UserCalendarService.getState();
    $scope.nextMonth = function () {
        $scope.events = UserCalendarService.next();
    };

    $scope.prevMonth = function () {
        $scope.events = UserCalendarService.previous();
    };

    $scope.dayClicked = function (day) {
        $scope.selectedDay = day.date;
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
            var meet = message.data.meet;
            if (moment(meet.time).local().isSame($scope.selectedDay, 'month')) {
                $scope.events.push(message.data);
                if (moment(meet.time).local().isSame($scope.selectedDay, 'day')) {
                    $scope.selected.push(message.data);
                    message.data.viewed = true;
                    EventHandler.setNew(message.data);
                    UserEntity.update({entity: "meets", id: message.data.meet.meetId}, message.data);
                }
            } else if (meet.time.isSame($scope.selectedDay.clone().add(1, 'month'), 'month')) {
                UserCalendarService.getNext().push(message.data);
            }
            else if (meet.time.isSame($scope.selectedDay.clone().add(-1, 'month'), 'month')) {
                UserCalendarService.getPrev().push(message.data);
            }
            $scope.$apply();
        }
        $rootScope.$on('$stateChangeStart',
            function () {
                UserCalendarService.current().length = 0;
                UserCalendarService.getNext().length = 0;
                UserCalendarService.getPrev().length = 0;
            })
    });
});