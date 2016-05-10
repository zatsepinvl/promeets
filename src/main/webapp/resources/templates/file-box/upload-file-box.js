app.directive("uploadFileBox", function (EventHandler, Upload, FileStorageDialog) {
    return {
        restrict: "E",
        templateUrl: "templates/file-box/upload-file-box.html",
        scope: {
            file: '=',
            whiteSpaceText: '@',
            maxSize: '@',
            format: '@',
            onUploaded: '=',
            onDelete: '=',
            size: '@'
        },
        link: function ($scope) {
            $scope.url = '/api/files';
            $scope.upload = function (event) {
                FileStorageDialog.show($scope.file, $scope.format, $scope.maxSize, $scope.url, event,
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