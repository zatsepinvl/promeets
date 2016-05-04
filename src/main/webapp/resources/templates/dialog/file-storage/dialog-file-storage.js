//Textarea dialog service
app.service('FileStorageDialog', function (DialogService) {
    this.show = function (user, file, format, maxSize, url, event, success, cancel) {
        DialogService.show(
            FileStorageDialogCtrl,
            'templates/dialog/file-storage/dialog-file-storage.html',
            {
                title: 'File storage',
                file: file,
                user: user,
                format: format,
                maxSize: maxSize,
                url: url
            },
            event,
            success,
            cancel);
    };
});

function FileStorageDialogCtrl($scope, file, user, format, maxSize, url, Entity, $mdDialog, Upload, EventHandler) {
    $scope.user = user;
    $scope.format = format;
    $scope.maxSize = maxSize;
    $scope.file = file;
    var tempFile = file;

    var rollBack = function () {
        Entity.update({entity: "files", id: tempFile.fileId}, tempFile);
        clone(tempFile, file);
    };

    $scope.onClicked = function (selected) {
        $scope.file.url = selected.url;
        $scope.file.name = selected.name;
        Entity.update({entity: "files", id: $scope.file.fileId}, $scope.file);
        $scope.save();
    };

    $scope.cancel = function () {
        rollBack();
        $mdDialog.cancel();
    };

    $scope.save = function () {
        $mdDialog.hide($scope.file);
    };

    $scope.upload = function (uploadFile, invalidFiles) {
        if (!$scope.file) {
            return;
        }
        if ($scope.cleared) {
            $scope.cleared = false;
            return;
        }
        $scope.cleared = true;
        $scope.loaded = false;
        if (!uploadFile && invalidFiles) {
            EventHandler.error('Invalid file format or too big size (max ' + maxSize + ' )');
            return;
        }
        $scope.progress = 0;
        $scope.loading = true;
        Upload.upload({
            url: url,
            data: {
                file: uploadFile,
                id: $scope.file.fileId
            }
        }).then(function (resp) {
            //success
            $scope.file = resp.data;
            $scope.loading = false;
            $scope.loaded = true;
            $scope.progress = 0;
        }, function (error) {
            //error
            EventHandler.error(error.data.message);
            $scope.loading = false;
            $scope.loaded = false;
            $scope.progress = 0;
        }, function (evt) {
            //progress
            $scope.progress = evt.loaded / evt.total * 100;
        });

        $scope.back = function () {
            rollBack();
            $scope.loaded = false;
        };


    };
}