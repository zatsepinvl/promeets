// header controller
app.controller('headerCtrl', function ($routeParams, $scope, $http, $mdDialog, $rootScope) {

    var loadId = $rootScope.pageState.push(false);
    $scope.$on('user', function (event, user) {
        if (user) {
            $scope.tab = "nav_log";
            $scope.currentUser = user;
        }
        else {
            $scope.tab = "nav_unlog";
        }
        $rootScope.pageState.push(true,loadId);
    });
    $scope.login = function () {
        $mdDialog.show({
                controller: LoginDialogController,
                templateUrl: '../templates/login/dialog_login.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            })
            .then(function () {
                //
            }, function () {
                //
            });
    };
});

function LoginDialogController($scope, $mdDialog) {
    $scope.cancel = function () {
        $mdDialog.cancel();
    }
}