app.directive("avatar", function ($state) {
    return {
        restrict: "E",
        templateUrl: "templates/avatar/avatar.html",
        scope: {
            user: '=',
            min: '=',
            online: '='
        },
        link: function ($scope) {
            $scope.$watch('user.image.original', function () {
            });
            $scope.go = function (userId) {
                $state.transitionTo('user.profile', {userId: userId});
            }
        }
    }
});