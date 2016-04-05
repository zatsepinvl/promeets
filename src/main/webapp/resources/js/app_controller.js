var app = angular.module('app', ['ngMaterial', 'ngResource', 'ui.router', 'ngMessages', 'luegg.directives']);

app.run(function ($rootScope, $location, $timeout) {
    $rootScope.$on('$viewContentLoaded', function () {
        $timeout(function () {
            componentHandler.upgradeAllRegistered();
        });
    });
});

app.config(function ($locationProvider, $httpProvider, $stateProvider, $urlRouterProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        /* $routeProvider.when('/signup/:provider:param2', {
         controller: 'signupCtrl'
         });*/
        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('home', {
                abstract: true,
                url: '',
                views: {
                    '': {
                        templateUrl: 'static/home/home.html'
                    }
                }
            })
            .state('home.presentation',
                {
                    url: '/',
                    views: {
                        'body': {
                            templateUrl: '/static/home/presentation/presentation.html'
                        }
                    }
                })
            .
            state('home.login',
                {
                    url: '/login',
                    views: {
                        'body': {
                            templateUrl: '/static/home/login/login.html'
                        }
                    }
                })
            .state('home.signup',
                {
                    url: '/signup',
                    views: {
                        'body': {
                            templateUrl: '/static/home/signup/signup.html'
                        }
                    }
                })
            .state('user',
                {
                    abstract: true,
                    url: '',
                    templateUrl: '/static/user/user.html'
                })
            .state('user.group',
                {
                    url: '/group/{groupId}',
                    views: {
                        'body': {
                            templateUrl: '/static/user/group/group.html'
                        }
                    }
                })
            .state('user.group.meets',
                {
                    templateUrl: '/static/user/group/meets/meets.html'
                })
            .state('home.chat',
                {
                    url: '/chat',
                    views: {
                        'body': {
                            templateUrl: '/static/chat/chat.html'
                        }
                    }
                })
			.state('user.group.chat',
                {
                    templateUrl: '/static/chat/chat.html'
                });
        $locationProvider.html5Mode(true);
    }
);

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
                console.log(ctrl.$viewValue);
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


//configuration for angular_material_style
app.config(function ($mdThemingProvider) {
    $mdThemingProvider.definePalette('amazingPaletteName', {
        '50': '2e7d32',
        '100': '2e7d32',
        '200': '2e7d32',
        '300': '2e7d32',
        '400': 'ef6c00',
        '500': 'ef6c00',
        '600': 'ef6c00',
        '700': '2e7d32',
        '800': '2e7d32',
        '900': '2e7d32',
        'A100': '2e7d32',
        'A200': '2e7d32',
        'A400': '2e7d32',
        'A700': '2e7d32',
        'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
        // on this palette should be dark or light
        'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
            '200', '300', '400', 'A100'],
        'contrastLightColors': undefined    // could also specify this if default was 'dark'
    });
    $mdThemingProvider.theme('default')
        .primaryPalette('amazingPaletteName');
});

//factories
app.factory('Entity', function ($resource) {
    return $resource('/api/:entity/:id/:d_entity', {
        entity: '@entity',
        id: '@id',
        d_entity: "@d_entity"
    }, {
        'update': {
            method: 'PUT'
        }
    });
});
app.service('UserService', function ($http) {
    var value = undefined;
    this.load = function (callback) {
        $http.get('/api/user').success(function (user) {
            value = user;
            callback && callback(user);
        }).error(function (error) {
            callback && callback(error);
        });
    };
    this.value = value;
});
//Main App Controller
app.controller('appCtrl', function ($scope) {
});










