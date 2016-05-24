//group controller
app.controller('groupCtrl', function ($scope, $state, EditGroupDialogService, EventHandler, $stateParams, Entity, GroupService) {
    $scope.groupId = $stateParams.groupId;
    $scope.group = GroupService.get();

    $scope.transitionTo = function (state) {
        $state.transitionTo(state, {groupId: $scope.groupId});
    };

    $scope.editGroup = function (event) {
        EditGroupDialogService.show($scope.group, event,
            function (group) {
                $scope.group = group;
                Entity.update({entity: "groups", id: group.groupId}, group);
            });
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


app.controller('groupMainCtrl', function ($scope, $state, appConst, $stateParams, Entity, GroupService, UserSearchDialogService, UserService) {
    $scope.groupId = $stateParams.groupId;
    $scope.users = GroupService.getMembers();
    $scope.invited = GroupService.getInvited();
    $scope.user = UserService.get();
    $scope.showInvited = function () {
        $scope.show = !$scope.show;
    };
    $scope.invite = function () {
        UserSearchDialogService.show($scope.group, $scope.users, $scope.invited);
    };

    $scope.$on('usergroupinvite', function (event, message) {
        if (message.action == appConst.ACTION.UPDATE) {
            message = message.data;
            if (message.user.userId != $scope.user.userId) {
                if (message.accepted) {
                    $scope.users.push(message.user);
                }
                $scope.invited.splice($scope.invited.map(
                    function (e) {
                        return e.userId;
                    }).indexOf(message.user.userId), 1);
                $scope.$apply();
            }
        }
    });
});


