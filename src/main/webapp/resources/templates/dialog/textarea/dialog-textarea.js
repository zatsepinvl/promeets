//Textarea dialog service
app.service('TextareaDialog', function ($mdDialog) {
    this.show = function (title, placeholder, text, success, cancel) {
        $mdDialog.show({
                controller: TextareaDialogController,
                templateUrl: 'templates/dialog/textarea/dialog-textarea.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                locals: {
                    title: title,
                    placeholder: placeholder,
                    text: text
                }
            })
            .then(function (result) {
                success && success(result);
            }, function () {
                cancel && cancel();
            });
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