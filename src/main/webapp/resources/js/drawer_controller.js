/**
 * Created by Vladimir on 15.03.2016.
 */
app.controller('drawerCtrl', function ($scope, $state, $rootScope, $http, EventHandler, UserService, UserMeetService, UserMessageService, $state, appConst) {
    $scope.user = UserService.get();
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
        $state.transitionTo(state, params);
    };

    $scope.newMeets = UserMeetService.getNewMeets();
    $scope.newMessages = UserMessageService.getNewMessages();

    $scope.$on('meet', function (event, data) {
        if (data.action == appConst.ACTION.CREATE) {
            $scope.newMeets.push(data.id);
            $scope.$apply();
        }
    });

    $scope.$on('usermessage', function (event, data) {
        onMessageReceive(data);
        $scope.$apply();
    });

    $rootScope.$on('usermessageLocal', function (event, data) {
        onMessageReceive(data);
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
    }
});