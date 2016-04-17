var app = angular.module('app', ['ngMaterial', 'ngResource', 'ui.router', 'ngMessages', 'ngSanitize']);
String.prototype.replaceAll = function (search, replace) {
    return this.split(search).join(replace);
};

app.run(function ($rootScope, $location, $timeout) {
    $rootScope.$on('$viewContentLoaded', function () {
        $timeout(function () {
            componentHandler.upgradeAllRegistered();
        });
    });
});

app.config(function ($locationProvider, $httpProvider, $stateProvider, $urlRouterProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        $urlRouterProvider.otherwise('/');
        $stateProvider
            .state('app', {
                abstract: true,
                url: '',
                resolve: {
                    user: function (UserService) {
                        return UserService.load();
                    }
                }
            })
            .state('home', {
                abstract: true,
                parent: 'app',
                url: '',
                templateUrl: 'static/home/home.html'

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
                    parent: 'app',
                    url: '',
                    templateUrl: '/static/user/user.html'
                })
            .state('user.group',
                {
                    abstract: true,
                    url: '/group/{groupId}',
                    views: {
                        'body': {
                            templateUrl: '/static/user/group/group.html'
                        }
                    },
                    resolve: {
                        group: function (GroupService, $stateParams) {
                            return GroupService.load($stateParams.groupId);
                        }
                    }
                })
            .state('user.group.main',
                {
                    url: '/main',
                    templateUrl: '/static/user/group/group_main.html'
                })
            .state('user.group.calendar',
                {
                    url: '/calendar',
                    templateUrl: '/static/user/group/meets/calendar.html'
                })
            .state('user.venue',
                {
                    url: '/venue/{meetId}',
                    views: {
                        'body': {
                            templateUrl: '/static/user/venue/venue.html'
                        },
                        'left_column': {
                            templateUrl: '/static/user/venue/venue_users.html'
                        }
                    },
                    resolve: {
                        meet: function (MeetService, $stateParams) {
                            return MeetService.load($stateParams.meetId);
                        }
                    }
                })
			.state('user.group.chat',
                {
					url: '/chat',
                    templateUrl: '/static/user/group/chat/chat.html'
                });
        $locationProvider.html5Mode(true);
    }
)
;


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


function clone(from, to) {
    for (var k in from) to[k] = from[k];
};
