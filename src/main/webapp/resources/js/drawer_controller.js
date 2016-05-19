/**
 * Created by Vladimir on 15.03.2016.
 */
app.controller('drawerCtrl', function ($scope, $state, $rootScope, $http, EventHandler, UserService, UserMeetService, UserMessageService, UserGroupsService, $state, appConst) {
    $scope.user = UserService.get();
    var layout = document.querySelector('.mdl-layout');

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

    $scope.$on('usermeet', function (event, message) {
        if (message.action == appConst.ACTION.CREATE) {
            var meet = message.data.meet;
            console.log(meet.time);
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

    $scope.$on('usermeetinfo', function (event, data) {
        console.log(data.entity + ':DRAWER CONTROLLER:FROM ROOT SCOPE:' + data.action);
        onMeetOnline(data);
    });

    var onMessageReceive = function (data) {
        if (data.action == appConst.ACTION.CREATE) {
            //if ($state.current.name != 'user.group.chat') {
            $scope.newMessages.push(data.id);
            var sender = data.data.message.user;
            //  EventHandler.message('New message by ' + sender.firstName + ' ' + sender.lastName, sender.image.url);
            //}
        }
        else if (data.action == appConst.ACTION.UPDATE) {
            $scope.newMessages.splice($scope.newMessages.indexOf(data.id), 1);
        }
    };

    var onMeetOnline = function (data) {
        var sender = data.data.user;
        if (data.data.user.userId == $scope.user.userId)
            return;
        if (data.action == appConst.ACTION.UPDATE && data.data.online && data.data.connected) {
            EventHandler.message(sender.firstName + ' ' + sender.lastName + ' join to chat', sender.image.url);
        }
        else if (data.action == appConst.ACTION.UPDATE && data.data.online && !data.data.connected) {
            EventHandler.message(sender.firstName + ' ' + sender.lastName + ' join', sender.image.url);
        }
        else if (data.action == appConst.ACTION.UPDATE && !data.data.online) {
            EventHandler.message(sender.firstName + ' ' + sender.lastName + ' leave meet', sender.image.url);
        }
    }

});