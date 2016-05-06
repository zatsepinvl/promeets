app.filter('fileFormat', function () {
    return function (files, format) {
        var filtered = [];
        var extensions = '';
        if (format.indexOf('image') > -1) {
            extensions += 'jpeg' + 'png' + 'gif' + 'jpg';
            for (var i = 0; i < files.length; i++) {
                var ext = files[i].name.substr((~-files[i].name.lastIndexOf(".") >>> 0) + 2);
                if (extensions.indexOf(ext) > -1) {
                    filtered.push(files[i]);
                }
            }
            return filtered;
        }
        else {
            return files;
        }

    }
});

app.directive("fileStorage", function (UserEntity) {
    return {
        restrict: "E",
        templateUrl: "templates/file-storage/file-storage.html",
        scope: {
            user: '=',
            onClicked: '=',
            format: '='
        },
        link: function ($scope) {
            $scope.loading = true;
            $scope.files = [];
            UserEntity.query({entity: "files"}
                , function (data) {
                    $scope.files = data;
                    $scope.loading = false;
                });
            $scope.clicked = function (file) {
                $scope.onClicked && $scope.onClicked(file);
            }
        }
    }
});