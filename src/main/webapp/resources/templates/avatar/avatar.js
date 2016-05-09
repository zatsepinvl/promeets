app.directive("avatar", function ($state) {
    return {
        restrict: "E",
        templateUrl: "templates/avatar/avatar.html",
        scope: {
            user: '=',
            min: '='
        },
        link: function ($scope) {
            $scope.go = function (userId) {
                $state.transitionTo('user.profile', {userId: userId});
            }
        }
    }
});