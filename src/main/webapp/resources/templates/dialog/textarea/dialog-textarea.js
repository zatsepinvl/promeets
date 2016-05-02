//Textarea dialog service
app.service('TextareaDialog', function (DialogService) {
    this.show = function (title, placeholder, text, event, success, cancel) {
        DialogService.show(
            TextareaDialogController,
            'templates/dialog/textarea/dialog-textarea.html',
            {
                title: title,
                placeholder: placeholder,
                text: text
            },
            event,
            success,
            cancel
        );
    }
});


function TextareaDialogController($scope, title, placeholder, text, $mdDialog) {
    $scope.title = title;
    $scope.placeholder = placeholder;
    $scope.text = text;
    $scope.save = function () {
        $mdDialog.hide($scope.text);
    };
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
}