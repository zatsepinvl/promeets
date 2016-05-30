app.directive("fileBox", function (ImageViewDialog) {
    return {
        restrict: "E",
        templateUrl: "templates/file-box/file-box.html",
        scope: {
            file: '=',
            disabled: '=',
            size: '@'
        },
        link: function ($scope) {

            $scope.$watch('file.original', function () {
                $scope.url = getUrl();
            });

            $scope.isImage = function (fileName) {
                if (!fileName) return true;
                return !!(fileName.indexOf('.png') > -1
                || fileName.indexOf('.jpg') > -1
                || fileName.indexOf('.jpeg') > -1
                || fileName.indexOf('.gif') > -1);
            };

            $scope.viewImage = function (file, event) {
                ImageViewDialog.show(file.name, file.original, event);
            };
            var getUrl = function () {
                switch ($scope.size) {
                    case 'small':
                        return $scope.file.small;
                    case 'medium':
                        return $scope.file.medium;
                    case 'large':
                        return $scope.file.large;
                    case 'original':
                        return $scope.file.original;
                    default:
                        return $scope.file.original;
                }
            };
            $scope.url = getUrl();
        }
    }
});