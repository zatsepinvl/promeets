app.controller('profileCtrl', function ($scope, UserService, UserInfoService) {
    $scope.user = UserService.get();
    $scope.userInfo = UserInfoService.get();
    $scope.state = UserInfoService.getState();
});