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

function CardEditDialogCtrl($scope, title, action, card, $mdDialog, UploadService, EventHandler) {
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

    $scope.upload = function (uploadFile, invalidFiles, file, loading, progress) {
        loading = true;
        progress = 0;
        var temp = file.url;
        file.url = undefined;
        UploadService.upload(uploadFile, file.fileId,
            function (data) {
                file.url = data.message;
            },
            function (error) {
                EventHandler.error(error.message);
                file.url = temp;
            },
            function (pr) {
                progress = pr;
                if (progress == 100) {
                    loading = false;
                }
            });
    };
}