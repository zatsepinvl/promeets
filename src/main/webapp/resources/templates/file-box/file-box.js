app.directive("fileBox", function (ImageViewDialog) {
    return {
        restrict: "E",
        templateUrl: "templates/file-box/file-box.html",
        scope: {
            file: '=',
            disabled: '='
        },
        link: function ($scope) {
            $scope.isImage = function (fileName) {
                return !!(fileName.indexOf('.png') > -1
                || fileName.indexOf('.jpg') > -1
                || fileName.indexOf('.gif') > -1);
            };

            $scope.viewImage = function(file,event)
            {
                ImageViewDialog.show(file.name,file.url,event);
            }

        }
    }
});