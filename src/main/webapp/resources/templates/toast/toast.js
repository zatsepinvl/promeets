//services
app.service('EventHandler', function ($mdToast) {

    var delay = 2000;
    var dialog = {
        position: 'right bottom',
        controller: ToastCtrl,
        templateUrl: 'templates/toast/toast.html'
    };

    this.message = function (message) {
        dialog.locals =
        {
            message: message,
            loading: false,
            action: false
        };
        dialog.hideDelay = delay;
        $mdToast.show(dialog);
    };

    this.load = function (message) {
        dialog.locals =
        {
            message: message,
            loading: true,
            action: false
        };
        dialog.hideDelay = 10000;
        $mdToast.show(dialog);
    };

    this.action = function (message, act, action, none) {
        dialog.locals =
        {
            message: message,
            loading: false,
            action: act
        };
        dialog.hideDelay = delay;
        $mdToast.show(dialog)
            .then(function (response) {
                if (response) {
                    action && action()
                }
                else {
                    none && none();
                }
            });
    };

    this.show = function (message, loading, action, duration, act, none) {
        dialog.locals =
        {
            message: message,
            loading: loading,
            action: action
        };
        dialog.hideDelay = duration;
        $mdToast.show(dialog)
            .then(function (response) {
                if (response) {
                    act && act()
                }
                else {
                    none && none();
                }
            });
    }
});


function ToastCtrl($scope, message, action, loading, $mdToast) {
    $scope.message = message;
    $scope.loading = loading;
    $scope.action = action;

    $scope.hide = function () {
        $mdToast.hide(action);
    }
}