//Textarea dialog service
app.service('MeetEditDialogService', function (DialogService) {
    this.show = function (meet, event, success, cancel) {
        DialogService.show(
            MeetEditDialogCtrl,
            'templates/dialog/meet-edit/dialog-meet-edit.html',
            {
                title: 'Edit meet',
                action: 'Save',
                meet: meet
            },
            event,
            success,
            cancel);
    };
});

function MeetEditDialogCtrl($scope, title, action, meet, $mdDialog) {
    $scope.title = title;
    $scope.action = action;
    clone(meet, $scope.meet = {});
    $scope.date = $scope.meet.time.toDate();

    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    $scope.save = function () {
        if ($scope.createMeetForm.$valid) {
            meet = $scope.meet;
            meet.time = moment($scope.date)
                .hour($scope.time.getHours())
                .minute($scope.time.getMinutes())
                .second(0)
                .millisecond(0);
            $mdDialog.hide(meet);
        }
    };


}