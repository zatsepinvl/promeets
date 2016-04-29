var app = angular.module('app', [
    'ngMaterial',
    'ngResource',
    'ui.router',
    'ngMessages',
    'ngSanitize',
    'focus-if',
    'luegg.directives',
    'ngFileUpload'
]);

String.prototype.replaceAll = function (search, replace) {
    return this.split(search).join(replace);
};

app.run(function ($rootScope, $location, $timeout) {
    $rootScope.$on('$viewContentLoaded', function () {
        $timeout(function () {
            componentHandler.upgradeAllRegistered();
        });
    });
    $rootScope.multiLine = function (str) {
        return str.replaceAll("\n", "<br/>");
        /*return str
         .replace(/\r\n?/g, '\n')
         // normalize newlines - I'm not sure how these
         // are parsed in PC's. In Mac's they're \n's
         .replace(/(^((?!\n)\s)+|((?!\n)\s)+$)/gm, '')
         // trim each line
         .replace(/(?!\n)\s+/g, ' ')
         // reduce multiple spaces to 2 (like in "a    b")
         .replace(/^\n+|\n+$/g, '')
         // trim the whole string
         .replace(/[<>&"']/g, function (a) {
         // replace these signs with encoded versions
         switch (a) {
         case '<'    :
         return '&lt;';
         case '>'    :
         return '&gt;';
         case '&'    :
         return '&amp;';
         case '"'    :
         return '&quot;';
         case '\''   :
         return '&apos;';
         }
         })
         .replace(/\n{2,}/g, '</p><p>')
         // replace 2 or more consecutive empty lines with these
         .replace(/\n/g, '<br />')
         // replace single newline symbols with the <br /> entity
         .replace(/^(.+?)$/, '<p>$1</p>');
         // wrap all the string into <p> tags
         // if there's at least 1 non-empty character*/
    };
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
                    templateUrl: '/static/user/user.html',
                    resolve: {
                        newMeets: function (UserMeetService) {
                            return UserMeetService.load();
                        },
                        newMessages: function (UserMessageService) {
                            return UserMessageService.load();
                        }
                    }
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
                    templateUrl: '/static/user/group/meets/calendar.html',
                    resolve: {
                        meets: function (GroupMeetsService, $stateParams) {
                            return GroupMeetsService.resolve($stateParams.groupId);
                        }
                    }
                })
            .state('user.group.chat',
                {
                    url: '/chat',
                    templateUrl: '/static/user/group/chat/chat.html',
                    resolve: {
                        messages: function (GroupChatService,$stateParams) {
                            return GroupChatService.load($stateParams.groupId);
                        }
                    }
                })
			.state('user.group.rtc',
                {
                    url: '/rtc',
                    templateUrl: '/static/user/rtc.html',
                })
            .state('user.messages',
                {
                    url: '/messages',
                    views: {
                        'body': {
                            templateUrl: '/static/user/chats/chats.html'
                        }
                    },
                    resolve: {
                        messages: function (UserChatsService) {
                            return UserChatsService.resolve();
                        }
                    }
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
            .state('files',
                {
                    url: '/files',
                    templateUrl: '/static/file.html'
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
}

app.constant('appConst', {
    WS: {
        URL: '/ws',
        TOPIC: '/topic/',
        BROKER: '/app/',
        RECONNECT_TIMEOUT: 600
    },

    ACTION: {
        CREATE: 'CREATE',
        UPDATE: 'UPDATE',
        DELETE: 'DELETE'
    },

    TIME_FORMAT: {
        DAY: 'DD MMMM YYYY',
        TIME: 'HH:mm',
        DAY_TIME: 'DD MMMM YYYY HH:mm'
    }
});

var DAY_FORMAT = 'DD MMMM YYYY';
var TIME_FORMAT = 'HH:mm';
var DAY_TIME_FORMAT = 'DD MMMM YYYY HH:mm';
