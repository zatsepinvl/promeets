// header controller
app.controller('headerCtrl', function ($scope, UserService, $mdDialog) {
    UserService.load(function (user) {
        $scope.tab = "nav_log";
        $scope.currentUser = user;
    }, function () {
        $scope.tab = "nav_unlog";
    });
    
    $scope.login = function () {
        $mdDialog.show({
                controller: LoginDialogController,
                templateUrl: '../static/home/dialog_login.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true
            })
            .then(function () {
                //
            }, function () {
                //
            });
    };
})
;

function LoginDialogController($scope, $mdDialog) {
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
    $scope.$on('closeDialog', function () {
        $mdDialog.cancel();
    });
}