/**
 * Created by Vladimir on 15.03.2016.
 */
app.controller('drawerCtrl', function ($scope, $http, UserService, UserMeetService, UserMessageService, $state, appConst) {
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

    $scope.newMeets = UserMeetService.getNewMeets();
    $scope.newMessages = UserMessageService.getNewMessages();

    $scope.$on('meet', function (event, data) {
        if (data.action == appConst.ACTION.CREATE) {
            $scope.newMeets.push(data.id);
        }
    });

    $scope.$on('usermessage', function (event, data) {
        if (data.action == appConst.ACTION.CREATE) {
            $scope.newMessages.push(data.id);
            $scope.$apply();
        }
        else if (data.action == appConst.ACTION.UPDATE) {
            $scope.newMessages.splice($scope.newMessages.indexOf(data.id), 1);
            $scope.$apply();
        }

    });
});