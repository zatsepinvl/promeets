function div(val, by) {
    return (val - val % by) / by;
}
var day = 1000 * 60 * 60 * 24;

app.controller('groupCalendarCtrl', function ($scope, Entity, $mdDialog, $timeout, $mdMedia, $http, $stateParams, $q) {
    $scope.times = [];
    $scope.meets = [];
    $scope.load = false;
    $scope.$on('newMeet', function (event, meet) {
        $scope.meets.push(meet);
    });

    $scope.loadByMonth = function (date) {
        $scope.times = [];
        $http.get("/api/groups/" + $stateParams.groupId + "/meets/month/" + date)
            .success(function (meets) {
                for (var i = 0; i < meets.length; i++) {
                    $scope.times.push(meets[i].time);
                }
                $scope.meets = meets;
                $scope.load = true;
            })
            .error(function (error) {
                console.log(error);
            });
    };
    $scope.loadByMonth(new Date().getTime());
    $scope.createMeet = function () {
        $mdDialog.show({
                controller: CreateMeetController,
                templateUrl: 'static/user/group/meets/dialog_new_meet.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                locals: {
                    group: $scope.group
                }
            })
            .then(function () {

            })
            .then(function (meet) {
                if (meet.title) {
                    $scope.$broadcast('newMeet', meet);
                }
            });
    };

    $scope.change = function (date) {
        $scope.loadByMonth(date.format('x'));
    };

    $scope.selectedMeets = [];
    $scope.dayClicked = function (date) {
        console.log(date.format('x'));
        $scope.selectedMeets = [];
        for (var i = 0; i < $scope.meets.length; i++) {
            if (($scope.meets[i].time - date.format('x')) < day && ($scope.meets[i].time - date.format('x')) >= 0) {
                $scope.selectedMeets.push($scope.meets[i]);
            }
        }
        console.log($scope.selectedMeets);
    };

});

function CreateMeetController($scope, group, Entity, $mdDialog) {
    $scope.meet = {group: group, time: new Date()};
    $scope.cancel = function () {
        $mdDialog.hide(false);
    };
    $scope.save = function () {
        Entity.save({entity: "meets"}, $scope.meet);
        $mdDialog.hide($scope.meet);
    };
}

