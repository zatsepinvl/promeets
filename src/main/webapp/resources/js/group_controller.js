//group controller
app.controller('groupCtrl', function ($scope, $state, $stateParams, Entity, $mdDialog) {
    $scope.groupId = $stateParams.groupId;
    $scope.group = Entity.get({entity: "groups", id: $scope.groupId}, function () {
        $scope.tab = "meets";
    });

    $scope.transitionTo = function (url) {
        $state.transitionTo(url, {groupId: $scope.groupId});
    };

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

app.controller('groupMainCtrl', function ($scope, $state, $stateParams, Entity, $mdDialog) {
    $scope.groupId = $stateParams.groupId;
    $scope.users = [];
    $scope.users = Entity.query({entity: "groups", id: $scope.groupId, d_entity: "users"});
});


