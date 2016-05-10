app.directive("avatar", function ($state) {
    return {
        restrict: "E",
        templateUrl: "templates/avatar/avatar.html",
        scope: {
            user: '=',
            min: '='
        },
        link: function ($scope) {
            $scope.$watch('user.image.original', function () {
                //$scope.$apply();
                console.log('from avatar');
            });
            $scope.go = function (userId) {
                $state.transitionTo('user.profile', {userId: userId});
            }
        }
    }
});