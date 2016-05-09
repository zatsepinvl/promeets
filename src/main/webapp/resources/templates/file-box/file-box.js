app.directive("fileBox", function (ImageViewDialog) {
    return {
        restrict: "E",
        templateUrl: "templates/file-box/file-box.html",
        scope: {
            file: '=',
            disabled: '=',
            useOriginal: '='
        },
        link: function ($scope) {
            $scope.url = $scope.useOriginal ? $scope.file.originalUrl : $scope.file.url;
            $scope.$watch('file.url', function () {
                $scope.url = $scope.useOriginal ? $scope.file.originalUrl : $scope.file.url;
            });
            $scope.isImage = function (fileName) {
                if (!fileName) return true;
                return !!(fileName.indexOf('.png') > -1
                || fileName.indexOf('.jpg') > -1
                || fileName.indexOf('.gif') > -1);
            };

            $scope.viewImage = function (file, event) {
                ImageViewDialog.show(file.name, file.originalUrl, event);
            }

        }
    }
});