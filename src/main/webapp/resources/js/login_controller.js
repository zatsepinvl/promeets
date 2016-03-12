//login controller
app.controller('loginCtrl', function ($scope, $http) {
    $scope.error = {show: false, value: ""};
    $scope.loading = false;
    var authenticate = function (user, callback) {
        $scope.error.show = false;
        $scope.loading = true;
        var headers = user ? {
            authorization: "Basic "
            + btoa(user.email + ":" + user.password)
        } : {};
        $http.get('/api/user', {headers: headers}).success(function (user) {
            if (user.email) {
                window.location.pathname = "/group/1";
            }
            else {
                $scope.loading = false;
            }
            callback && callback(data);
        }).error(function (error) {
            $scope.loading = false;
            $scope.error.show = user ? true : false;
            $scope.error.value="Invalid email or password";
            callback && callback(error);
        });
    };
    //authenticate();
    $scope.user = {};
    $scope.login = function () {
        authenticate($scope.user);
    };
});