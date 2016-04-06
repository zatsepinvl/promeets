app.controller('signUpCtrl', function ($scope, $state, Entity, $location, EventHandler) {
        $scope.loading = false;
        $scope.error = {};
        $scope.provider = $location.search().provider;
        $scope.user = {};
        $scope.user.firstName = $location.search().firstName;
        $scope.user.lastName = $location.search().lastName;
        $scope.user.email = $location.search().email;
        $scope.user.image = {url: $location.search().img};

        $scope.signUp = function () {
            if ($scope.signUpForm.$valid) {
                $scope.loading = true;
                Entity.save({entity: "users"}, $scope.user, function () {
                        $scope.loading = false;
                        EventHandler.show('Account has been created.');
                        $state.transitionTo('home.login');
                    },
                    function (response) {
                        $scope.loading = false;
                        $scope.error.show = true;
                        $scope.error.value = response.data.message;
                    });
            }

        }
        ;

    }
)
;