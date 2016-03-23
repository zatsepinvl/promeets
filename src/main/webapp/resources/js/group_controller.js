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


