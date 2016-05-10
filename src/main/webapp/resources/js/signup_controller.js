app.controller('signUpCtrl', function ($scope, $location) {
    $scope.ready = $location.search().provider ? true : false;

    $scope.sSignUp = function () {
        $scope.ready = true;
    };

    $scope.gSignUp = function () {
        window.location.pathname = "/g/oauth2";
    };
});

app.controller('signUpFormCtrl', function ($scope, $state, Entity, $location, EventHandler) {
        $scope.loading = false;
        $scope.error = {};
        $scope.provider = $location.search().provider;
        $scope.user = {};
        $scope.user.firstName = $location.search().firstName;
        $scope.user.lastName = $location.search().lastName;
        $scope.user.email = $location.search().email;
        $scope.user.image = {
            small: $location.search().img,
            medium: $location.search().img,
            large: $location.search().oimg,
            original: $location.search().oimg
        };

        $scope.signUp = function () {
            if ($scope.signUpForm.$valid) {
                $scope.loading = true;
                Entity.save({entity: "users"}, $scope.user, function () {
                        $scope.loading = false;
                        EventHandler.message('Account has been created.');
                        $state.transitionTo('home.login');
                    },
                    function (response) {
                        $scope.loading = false;
                        $scope.error.show = true;
                        $scope.error.value = response.data.message;
                    });
            }

        };
    }
);