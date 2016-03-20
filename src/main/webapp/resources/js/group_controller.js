//group controller
app.controller('groupCtrl', function ($scope, $state, $stateParams, Entity, $mdDialog) {
    $scope.groupId = $stateParams.groupId;
    $state.go('.meets');
    $scope.group = Entity.get({entity: "groups", id: $scope.groupId}, function () {
        $scope.tab = "meets";
    });

    $scope.editGroup = function () {
        $mdDialog.show({
                controller: EditGroupDialogController,
                templateUrl: 'static/user/group/dialog_edit_group.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                locals: {
                    group: $scope.group
                }
            })
            .then(function () {
                //
            }, function () {
                //
            });
    };

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
    }
});

function cloneGroup(group) {
    return {
        title: group.title,
        type: group.type,
        status: group.status
    };
}

function EditGroupDialogController($scope, group, $http, Entity, $mdDialog) {
    $scope.group = cloneGroup(group);
    $http.get('/api/group_types').success(function (types) {
        $scope.types = types;
        $scope.load = true;
    });
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
    $scope.save = function () {
        group.title = $scope.group.title;
        group.status = $scope.group.status;
        group.type = {typeId: $scope.group.type.typeId, name: $scope.group.type.name};
        console.log(group.type);
        Entity.update({entity: "groups", id: group.groupId}, group);
        $mdDialog.hide();
    };
}

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


//meet list controller
app.controller('meetListCtrl', function ($scope, Entity, $mdDialog, $mdMedia) {
    $scope.error = false;
    $scope.load = false;
    $scope.meets = [];
    $scope.$on('newMeet', function (event, meet) {
        $scope.meets.push(meet);
    });
    var loadMeets = function () {
        $scope.meets = Entity.query({entity: "groups", id: $scope.groupId, d_entity: "meets"}, function (meets) {
            meets.forEach(function (meet) {
                meet.time = new Date(meet.time);
            });
            $scope.load = true;
        }, function () {
            $scope.load = true;
            $scope.error = true;
            $scope.load_error = "Can't load meets. Try again later.";
        });
    };
    loadMeets();
});