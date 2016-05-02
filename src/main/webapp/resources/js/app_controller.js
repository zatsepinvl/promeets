//Main App Controller
app.controller('appCtrl', function ($scope, AppService) {
    $scope.toDateTime = AppService.toDateTime;
    $scope.toTime = AppService.toTime;
});
