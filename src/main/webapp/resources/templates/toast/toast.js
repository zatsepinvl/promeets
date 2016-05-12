//services
app.service('EventHandler', function ($mdToast) {

    var delay = 7000;
    var dialog = {
        position: 'right bottom',
        controller: ToastCtrl,
        templateUrl: 'templates/toast/toast.html'
    };

    this.setNew = function (item) {
        item.isNew = true;
        setTimeout(function () {
            item.isNew = false;
        }, 1000);
    };

    this.error = function (message) {
        dialog.locals =
        {
            message: message,
            loading: false,
            action: false,
            image: undefined,
            error: true
        };
        dialog.hideDelay = delay;
        $mdToast.show(dialog);
    };

    this.message = function (message, user) {
        dialog.locals =
        {
            message: message,
            loading: false,
            action: false,
            user:user,
            error: false
        };
        dialog.hideDelay = delay;
        $mdToast.show(dialog);
    };

    this.load = function (message) {
        dialog.locals =
        {
            message: message,
            loading: true,
            action: false,
            user: undefined,
            error: false
        };
        dialog.hideDelay = 10000;
        $mdToast.show(dialog);
    };

    this.action = function (message, act, action, none) {
        dialog.locals =
        {
            message: message,
            loading: false,
            action: act,
            user: undefined,
            error: false
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
            action: action,
            user: undefined,
            error: false
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


function ToastCtrl($scope, message, action, loading, error,user, $mdToast) {
    $scope.message = message;
    $scope.loading = loading;
    $scope.action = action;
    $scope.user=user;
    $scope.error = error;
    $scope.hide = function () {
        $mdToast.hide(action);
    }
}