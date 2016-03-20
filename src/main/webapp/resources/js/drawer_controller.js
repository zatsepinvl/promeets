/**
 * Created by Vladimir on 15.03.2016.
 */
app.controller('drawerCtrl', function ($scope, $http, UserService) {
    $scope.user = {};
    UserService.load(function (user) {
        console.log(user);
        if (user.email) {
            $scope.user = user;
        }
    });
    $scope.logout = function () {
        $http.post('/logout')
            .success(function () {
                window.location.pathname = '/';
            })
            .error(function (error) {
                console.log(error);
            });
    };

});