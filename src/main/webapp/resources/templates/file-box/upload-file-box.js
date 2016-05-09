app.directive("uploadFileBox", function (EventHandler, Upload, FileStorageDialog) {
    return {
        restrict: "E",
        templateUrl: "templates/file-box/upload-file-box.html",
        scope: {
            file: '=',
            whiteSpaceText: '@',
            maxSize: '@',
            format: '@',
            url: '@',
            onUploaded: '=',
            onDelete: '=',
            user: '=',
            smallSize: '@',
            useOriginal: '='
        },
        link: function ($scope) {
            $scope.upload = function (event) {
                FileStorageDialog.show($scope.user, $scope.file, $scope.format, $scope.maxSize, $scope.url, $scope.smallSize, event,
                    function (file) {
                        $scope.file = file;
                        $scope.onUploaded && $scope.onUploaded(file);
                    });
            };

            $scope.delete = function (file) {
                $scope.onDelete && $scope.onDelete(file);
            }
        }
    }
});