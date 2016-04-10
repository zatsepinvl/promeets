// header controller
app.controller('headerCtrl', function ($scope, UserService, $mdDialog, $state) {

    if (UserService.get().userId) {
        $scope.tab = "nav_log";
    }
    else {
        $scope.tab = "nav_unlog";
    }

    $scope.login = function () {
        //$mdDialog.show({
        //        controller: LoginDialogController,
        //        templateUrl: '../static/home/dialog_login.html',
        //        parent: angular.element(document.body),
        //        clickOutsideToClose: true
        //    })
        //    .then(function () {
        //        //
        //    }, function () {
        //        //
        //    });
        $state.go('home.login');
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