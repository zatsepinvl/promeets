/**
 * Created by Vladimir on 15.03.2016.
 */
app.controller('drawerCtrl', function ($scope, $state, $stateParams, $rootScope, $http, EventHandler, UserService, UserMeetService, UserMessageService, UserGroupsService, $state, appConst) {
    $scope.user = UserService.get();
    var layout = document.querySelector('.mdl-layout');
    var primary = '#2e7d32';
    var accent = '#ef6c00';
    var changeColor = function (color) {
        $(".mdl-layout__drawer-button").children(".material-icons").css("color", color);
    };

    $scope.logout = function () {
        $http.post('/logout')
            .success(function () {
                UserService.logout();
                $state.transitionTo('home.presentation');
            })
            .error(function (error) {
                console.log(error);
            });
    };

    $scope.go = function (state, params) {
        layout.MaterialLayout.toggleDrawer();
        $state.transitionTo(state, params);
    };

    $scope.newMeets = UserMeetService.getNewMeets();
    $scope.newMessages = UserMessageService.getNewMessages();
    $scope.invites = UserGroupsService.getInvites();


    angular.element(document).ready(function () {
        if ($scope.newMeets.length) {
            setTimeout(function () {
                changeColor(accent);
            }, 400);
        }
        if ($scope.newMessages.length) {
            setTimeout(function () {
                changeColor(accent);
            }, 400);
        }
        if ($scope.invites.length) {
            setTimeout(function () {
                changeColor(accent);
            }, 400);
        }
    });

    $scope.$watchCollection('newMeets', function () {
        if ($scope.newMeets.length) {
            changeColor(accent);
        }
        else if (!$scope.newMessages.length && !$scope.invites.length) {
            changeColor(primary);
        }
    });

    $scope.$watchCollection('newMessages', function () {
        if ($scope.newMessages.length) {
            changeColor(accent);
        }
        else if (!$scope.newMeets.length && !$scope.invites.length) {
            changeColor(primary);
        }
    });

    $scope.$watchCollection('invites', function () {
        if ($scope.invites.length) {
            changeColor(accent);
        }
        else if (!$scope.newMeets.length && !$scope.newMessages.length) {
            changeColor(primary);
        }
    });

    $scope.$on('meet', function (event, message) {
        if (message.action == appConst.ACTION.UPDATE) {
            var meet = message.data;
            if ($state.current.name == 'user.venue' && $stateParams.meetId == meet.meetId) {
                return;
            }
            EventHandler.message(
                'Meet \'' + meet.title + '\' has been updated',
                undefined,
                {
                    state: 'user.group.calendar',
                    params: {
                        groupId: meet.group.groupId,
                        selected: meet.time
                    }
                }
            );
        }
    });

    $scope.$on('usermeet', function (event, message) {
        if (message.action == appConst.ACTION.CREATE) {
            var meet = message.data.meet;
            EventHandler.message(
                'New meet on ' + $scope.toDayTime(meet.time),
                meet.admin,
                {
                    state: 'user.group.calendar',
                    params: {
                        groupId: meet.group.groupId,
                        selected: meet.time
                    }
                }
            );
            $scope.newMeets.push(message.id);
            $scope.$apply();
        }
    });

    $scope.$on('usermessage', function (event, data) {
        onMessageReceive(data);
        $scope.$apply();
    });


    $scope.$on('usergroupinvite', function (event, message) {
        if (message.action == appConst.ACTION.CREATE) {
            message = message.data;
            if (message.user.userId != $scope.user.userId) {
                EventHandler.message($scope.fio(message.user)
                    + ' has been invited into '
                    + message.group.title
                    + ' by ' + $scope.fio(message.inviter),
                    message.user,
                    {
                        state: 'user.group.main',
                        params: {groupId: message.group.groupId}
                    });
            }
            else {
                EventHandler.message('New invitation into '
                    + message.group.title
                    + ' by ' + $scope.fio(message.inviter),
                    message.inviter);
                $scope.invites.push(message);
                $scope.$apply();
            }
        }
        else if (message.action == appConst.ACTION.UPDATE) {
            message = message.data;
            if (message.user.userId != $scope.user.userId && message.accepted) {
                EventHandler.message($scope.fio(message.user)
                    + ' joined into '
                    + message.group.title,
                    message.user,
                    {
                        state: 'user.group.main',
                        params: {groupId: message.group.groupId}
                    });
            }
        }
    });

    $rootScope.$on('usermessageLocal', function (event, data) {
        onMessageReceive(data);
    });

    $rootScope.$on('usermeetLocal', function (event, message) {
        if (message.action == appConst.ACTION.UPDATE) {
            $scope.newMeets.splice($scope.newMeets.indexOf(message.id), 1);
        }
    });


    var onMessageReceive = function (message) {
        if (message.action == appConst.ACTION.CREATE) {
            $scope.newMessages.push(message.id);
            if ($state.current.name != 'user.group.chat') {
                var sender = message.data.message.user;
                EventHandler.message(
                    'New message by ' + sender.firstName + ' ' + sender.lastName,
                    sender);
            }
        }
        else if (message.action == appConst.ACTION.UPDATE) {
            $scope.newMessages.splice($scope.newMessages.indexOf(message.id), 1);
        }
    };


    /*var onMeetOnline = function (data) {
     var sender = data.data.user;
     if (data.data.user.userId == $scope.user.userId)
     return;
     if (data.action == appConst.ACTION.UPDATE && data.data.online && data.data.connected)
     {
     EventHandler.message(sender.firstName + ' ' + sender.lastName + ' join to chat', sender.image.url);
     }

     }*/

});