//group controller
app.controller('groupCtrl', function ($scope, $state, $stateParams, Entity, GroupService, $mdDialog) {
    $scope.groupId = $stateParams.groupId;
    $scope.group = GroupService.get();

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

    $scope.messages = 0;
    $scope.$on('message', function (event, data) {
        if (data.action == appConst.ACTION.CREATE) {
            UserEntity.get({entity: "messages", id: data.id},
                function (message) {
                    $scope.message++;
                });
        }
    });

});

function cloneGroup(group) {
    return {
        title: group.title,
        type: group.type,
        status: group.status
    };
}

function EditGroupDialogController($scope, group, Entity, $mdDialog) {
    $scope.group = cloneGroup(group);
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

app.controller('groupMainCtrl', function ($scope, $state, $stateParams, Entity, GroupService, $mdDialog) {
    $scope.groupId = $stateParams.groupId;
    $scope.users = GroupService.getMembers();
});


