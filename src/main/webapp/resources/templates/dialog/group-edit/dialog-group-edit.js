app.service('EditGroupDialogService', function (DialogService) {
    this.show = function (group, event, success, cancel) {
        DialogService.show(
            EditGroupDialogCtrl,
            'templates/dialog/group-edit/dialog-group-edit.html',
            {
                title: 'Edit group',
                action: 'Save',
                group: group
            },
            event,
            success,
            cancel);
    };

});


function EditGroupDialogCtrl($scope, group, Entity, $mdDialog) {
    $scope.group = {};
    clone(group, $scope.group);
    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    $scope.save = function () {
        group.title = $scope.group.title;
        group.status = $scope.group.status;
        group.type = {typeId: $scope.group.type.typeId, name: $scope.group.type.name};
        Entity.update({entity: "groups", id: group.groupId}, group);
        $mdDialog.hide();
    };
}