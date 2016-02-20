var app = angular.module('securityApp', ['ngResource', 'ngRoute']);
app.controller('securityCtrl', function ($scope, $http) {
    $scope.logi = false;
    var authenticate = function (credentials, callback) {
        var headers = credentials ? {
            authorization: "Basic "
            + btoa(credentials.user + ":" + credentials.password)
        } : {};
        $http.get('/security/user', {headers: headers}).success(function (data) {
            if (data.name) {
                console.log("data: " + data);
                $scope.logi = true;
                $http.get('/security/data').success(function (data) {
                    $scope.data = data;
                });
            }
            console.log("login:" + $scope.login);
            callback && callback(data);

        }).error(function (error) {
            console.log(error);
            callback && callback(error);
        });
    };
    authenticate();
    $scope.credentials = {};
    $scope.login = function () {
        authenticate($scope.credentials, function (data) {
            console.log(data);
        });
    };
});