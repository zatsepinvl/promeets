//Textarea dialog service
app.service('ConfirmDialog', function (DialogService) {
    this.show = function (title, okText, cancelText, event, success, cancel) {
        DialogService.show(
            ConfirmDialogCtrl,
            'templates/dialog/confirm/dialog-confirm.html',
            {
                title: title,
                okText: okText,
                cancelText: cancelText
            },
            event,
            success,
            cancel);
    };
});

function ConfirmDialogCtrl($scope, title, okText, cancelText, $mdDialog) {
    $scope.title = title;
    $scope.okText = okText;
    $scope.cancelText = cancelText;

    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    $scope.ok = function () {
        $mdDialog.hide(1);
    };


}