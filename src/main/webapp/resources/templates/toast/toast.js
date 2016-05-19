//services
app.service('EventHandler', function ($mdToast) {

    var delay = 30000;
    var dialog = {
        position: 'right bottom',
        controller: ToastCtrl,
        templateUrl: 'templates/toast/toast.html'
    };

    this.setNew = function (item, timeOut) {
        item.isNew = true;
        setTimeout(function () {
            item.isNew = false;
            timeOut && timeOut();
        }, 1000);
    };

    this.error = function (message) {
        dialog.locals =
        {
            message: message,
            loading: false,
            action: false,
            image: undefined,
            error: true,
            url: undefined,
            user: undefined
        };
        dialog.hideDelay = delay;
        $mdToast.show(dialog);
    };

    this.message = function (message, user, url) {
        dialog.locals =
        {
            message: message,
            loading: false,
            action: false,
            user: user,
            error: false,
            url: url
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
            error: false,
            url: undefined
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
            error: false,
            url: undefined
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

    this.show = function (locals) {
        dialog.locals = locals;
        dialog.hideDelay = locals.delay ? locals.delay : delay;
        $mdToast.show(dialog)
            .then(function (response) {
                if (response) {
                    locals.action && locals.action()
                }
                else {
                    locals.cancel && locals.cancel();
                }
            });
    }
});


function ToastCtrl($scope, message, action, loading, url, error, user, $mdToast, $state) {
    $scope.message = message;
    $scope.loading = loading;
    $scope.action = action;
    $scope.user = user;
    $scope.error = error;
    $scope.url = url;
    $scope.hide = function () {
        $mdToast.hide(action);
    };

    $scope.go = function () {
        $state.transitionTo(url.state, url.params);
    }
}