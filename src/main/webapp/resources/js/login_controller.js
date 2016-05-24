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
                $state.transitionTo('user.calendar');
            }, function (error) {
                $scope.loading = false;
                $scope.error.show = user ? true : false;
                $scope.error.value = 'Invalid email or password';
            });
    };
    $scope.user = {};
    $scope.login = function () {
        authenticate($scope.user);
    };
    $scope.goSignup = function () {
        $scope.$emit('closeDialog');
        $state.go('home.signup');
    }
});