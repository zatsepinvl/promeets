
//directives
app.directive('complexPassword', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function (password) {
                var hasUpperCase = /[A-Z]/.test(password);
                var hasLowerCase = /[a-z]/.test(password);
                var hasNumbers = /\d/.test(password);
                var hasNonalphas = /\W/.test(password);
                var characterGroupCount = hasUpperCase + hasLowerCase + hasNumbers + hasNonalphas;

                if ((password.length >= 8) && (characterGroupCount >= 3)) {
                    ctrl.$setValidity('complexity', true);
                    return password;
                }
                else {
                    ctrl.$setValidity('complexity', false);
                    return undefined;
                }

            });
        }
    }
});

app.directive('time', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function (time) {
                if (time.length == 0) {
                    ctrl.$setValidity('timeComplex', true);
                    return time;
                }
                var hours = time.split(':')[0];
                var minutes = time.split(':')[1];
                if (time.length > 5
                    || !minutes
                    || hours < 0
                    || hours > 23
                    || minutes < 0
                    || minutes > 59
                    || hours.length < 2
                    || minutes.length < 2) {
                    ctrl.$setValidity('timeComplex', false);
                    return undefined;
                }
                ctrl.$setValidity('timeComplex', true);
                return time;
            });
        }
    }
});

app.directive('onScrollToTop', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var fn = scope.$eval(attrs.onScrollToTop);
            element.on('scroll', function (e) {
                if (!e.target.scrollTop) {
                    scope.$apply(fn);
                }
            });
        }
    };
});

app.directive('pressedEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13 && !event.shiftKey) {
                scope.$apply(function (){
                    scope.$eval(attrs.pressedEnter);
                });
                event.preventDefault();
            }
        });
    };
});
