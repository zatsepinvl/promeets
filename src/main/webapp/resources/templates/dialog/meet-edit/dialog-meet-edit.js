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

function MeetEditDialogCtrl($scope, title, action, meet, $mdDialog, appConst) {
    $scope.title = title;
    $scope.action = action;
    clone(meet, $scope.meet = {});
    var time = moment($scope.meet.time).local();
    $scope.date = time.toDate();
    if (meet.meetId) {
        $scope.time = time.format(appConst.TIME_FORMAT.TIME);
    }

    $scope.cancel = function () {
        $mdDialog.cancel();
    };

    $scope.save = function () {
        if ($scope.createMeetForm.$valid) {
            console.log($scope.time);
            $scope.time = moment(moment().format('YYYY-MM-DD') + 'T' + $scope.time + ':00');
            console.log($scope.time);
            meet = $scope.meet;
            meet.time = moment($scope.date)
                .hour($scope.time.hour())
                .minute($scope.time.minute())
                .second(0)
                .millisecond(0)
                .utc().valueOf();
            $mdDialog.hide(meet);
        }
    };
}