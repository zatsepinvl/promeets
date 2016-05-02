//Textarea dialog service
app.service('CardEditDialogService', function (DialogService) {
    this.show = function (card, event, success, cancel) {
        DialogService.show(
            CardEditDialogCtrl,
            'templates/dialog/card-edit/dialog-card-edit.html',
            {
                title: 'Edit card',
                action: 'Save',
                card: card
            },
            event,
            success,
            cancel
        );
    };
});

function CardEditDialogCtrl($scope, title, action, card, $mdDialog, UploadService) {
    $scope.title = title;
    $scope.action = action;
    clone(card, $scope.card = {});

    $scope.includeFiles = card.image.url || (card.files && card.files.length);

    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    $scope.save = function () {
        if ($scope.editCardForm.$valid) {
            $mdDialog.hide($scope.card);
        }
    };

    $scope.upload = function (file, invalidFiles) {
        UploadService.upload(file, $scope.card.image.fileId, function (data) {
            $scope.card.image.url = data.message;
        });
    };
}