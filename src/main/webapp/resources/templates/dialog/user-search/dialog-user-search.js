app.service('UserSearchDialogService', function (DialogService) {
    this.show = function (group, members, invited, event, success, cancel) {
        DialogService.show(
            UserSearchDialogCtrl,
            'templates/dialog/user-search/dialog-user-search.html',
            {
                group: group,
                members: members,
                invited: invited
            },
            event,
            success,
            cancel);
    };
});

function UserSearchDialogCtrl($scope, group, members, invited, $mdDialog, $http, UserEntity, UserService) {
    $scope.invited = invited;
    $scope.members = members;
    $scope.group = group;
    $scope.loading = false;
    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    $scope.save = function () {
        $mdDialog.hide();
    };

    $scope.invite = function (user) {
        user.loading = true;
        var inv = {user: user, inviter: UserService.get(), group: group, accepted: false};
        UserEntity.save({entity: "group_invites"}, inv,
            function () {
                user.loading = false;
                user.disabled = true;
                user.status = 'Invited';
                $scope.invited.push(inv);
            },
            function () {
                user.loading = false;
            });
    };

    $scope.search = function () {
        if (!$scope.searchQuery || $scope.searchQuery.length < 0) {
            return
        }
        $scope.loading = true;
        var query = '';
        if ($scope.searchQuery.indexOf('@') > -1) {
            query = '?email=' + $scope.searchQuery;
        }
        else {
            var words = $scope.searchQuery.split(/[ ,]+/);
            query = '?firstName=' + words[0] + (words[1] ? '&lastName=' + words[1] : '');
        }
        $http.get('/api/users/search' + query)
            .success(function (users) {
                $scope.noResults = !users.length;
                users.forEach(function (value) {
                    if ($scope.invited.map(
                            function (e) {
                                return e.userId;
                            }).indexOf(value.userId) > -1) {
                        value.status = 'Invited';
                        value.disabled = true;
                    }
                    else if ($scope.members.map(
                            function (e) {
                                return e.userId;
                            }).indexOf(value.userId) > -1) {
                        value.status = 'Member';
                        value.disabled = true;
                    }
                    else {
                        value.status = 'Invite';
                    }
                });
                $scope.users = users;
                $scope.loading = false;
            })
            .error(function () {
                $scope.loading = false;
            });
    }

}