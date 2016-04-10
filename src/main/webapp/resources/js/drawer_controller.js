/**
 * Created by Vladimir on 15.03.2016.
 */
app.controller('drawerCtrl', function ($scope, $http, UserService, UserMeetService, $state) {
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
});