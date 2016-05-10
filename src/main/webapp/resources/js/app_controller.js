//Main App Controller
app.controller('appCtrl', function ($scope, AppService) {
    $scope.toDayTime = AppService.toDayTime;
    $scope.toTime = AppService.toTime;
    $scope.toShortDayTime = AppService.toShortDayTime;
    $scope.fio = AppService.fio;
});
