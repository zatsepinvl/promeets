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
    clone(group, $scope.group = {});
    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    $scope.save = function () {
        $mdDialog.hide($scope.group);
    };
}