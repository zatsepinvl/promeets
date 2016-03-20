//index controller
app.controller('homeCtrl', function ($scope, $mdDialog) {
    $scope.signUp = function () {
        $mdDialog.show({
                controller: SignUpController,
                templateUrl: '../static/home/presentation/dialog_signup.html',
                parent: angular.element(document.body)
            })
            .then(function () {

            })
            .then(function (user) {

            });
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
