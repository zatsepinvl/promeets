//login controller
app.controller('loginCtrl', function ($scope, $http, $state, UserService) {
    $scope.error = {show: false, value: ""};
    $scope.loading = false;
    var authenticate = function (user) {
        $scope.error.show = false;
        $scope.loading = true;
        UserService.login(user.email, user.password,
            function (user) {
                $scope.$emit('closeDialog');
                $state.transitionTo('user.group.main', {groupId: 1});
            }, function (error) {
                $scope.loading = false;
                $scope.error.show = user ? true : false;
                $scope.error.value = error;
            });
    };
    $scope.user = {};
    $scope.login = function () {
        authenticate($scope.user);
    };
});