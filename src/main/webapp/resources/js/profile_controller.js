app.controller('profileCtrl', function ($scope, UserService, EventHandler, UserInfoService, Entity) {
    $scope.user = UserService.get();
    $scope.userInfo = UserInfoService.get();
    $scope.state = UserInfoService.getState();

    $scope.onImageDelete = function (file) {
        file.original = null;
        Entity.update({entity: "files", id: file.fileId}, file,
            function (data) {
                //clone(data, file);
            });
    };

    $scope.edit = function () {
        $scope.editing = true;
    };

    $scope.done = function () {
        UserInfoService.update(function () {
            EventHandler.message('Profile has been updated');
            $scope.editing = false;
        });
    }
});