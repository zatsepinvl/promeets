//Textarea dialog service
app.service('ImageViewDialog', function (DialogService) {
    this.show = function (title, url, event, success, cancel) {
        DialogService.show(
            ImageViewDialogCtrl,
            'templates/dialog/image-view/dialog-image-view.html',
            {
                title: title,
                url: url
            },
            event,
            success,
            cancel);
    };
});

function ImageViewDialogCtrl($scope, title, url, $mdDialog) {
    $scope.title = title;
    $scope.url = url;

    $scope.cancel = function () {
        $mdDialog.cancel();
    };
}