//group controller
app.controller('groupCtrl', function ($scope, $state,EditGroupDialogService, EventHandler, $stateParams, Entity, GroupService, $mdDialog) {
    $scope.groupId = $stateParams.groupId;
    $scope.group = GroupService.get();

    $scope.transitionTo = function (state) {
        $state.transitionTo(state, {groupId: $scope.groupId});
    };

    $scope.editGroup = function (event) {
        EditGroupDialogService.show($scope.group, event);
    };

    $scope.onGroupImageDelete = function (file) {
        file.original = null;
        file.small = null;
        file.large = null;
        file.meduim = null;
        file.name = null;
        Entity.update({entity: "files", id: file.fileId}, file);
    }
});


app.controller('groupMainCtrl', function ($scope, $state, $stateParams, Entity, GroupService, UserSearchDialogService) {
    $scope.groupId = $stateParams.groupId;
    $scope.users = GroupService.getMembers();
    $scope.invited = GroupService.getInvited();
    $scope.showInvited = function () {
        $scope.show = !$scope.show;
    };
    $scope.invite = function () {
        UserSearchDialogService.show($scope.group, $scope.users, $scope.invited);
    };
});


