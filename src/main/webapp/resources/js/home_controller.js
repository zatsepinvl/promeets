//index controller
app.controller('homeCtrl', function ($scope, $mdDialog, $state) {
    $scope.signUp = function () {
        //$mdDialog.show({
        //        controller: SignUpController,
        //        templateUrl: '../static/home/presentation/dialog_signup.html',
        //        parent: angular.element(document.body)
        //    })
        //    .then(function () {
        //
        //    })
        //    .then(function (user) {
        //
        //    });
        $state.go('home.signup');
    }
});

function SignUpController($scope, $state, $mdDialog) {
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
    $scope.signUp = function () {
        $state.transitionTo('home.signup');
        $mdDialog.cancel();
    };
    $scope.gSignUp = function () {
        window.location.pathname = "/g/oauth2";
    }
}
