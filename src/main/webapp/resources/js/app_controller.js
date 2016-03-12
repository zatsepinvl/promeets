var app = angular.module('app', ['ngMaterial', 'ngResource', 'ngRoute']);

app.config(function ($routeProvider, $httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }
);

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

//Main App Controller
app.controller('appCtrl', function ($scope, $http, $rootScope, $mdDialog) {
    $rootScope.pageState = {
        load: false, values: [], push: function (value, id) {
            var values = $rootScope.pageState.values;
            if (id != undefined) {
                values[id] = value;
            }
            else {
                values.push(value);
                id = values.length - 1;
            }
            for (var i = 0; i < values.length; i++) {
                if (!values[i]) {
                    $rootScope.pageState.load = false;
                    return id;
                }
            }
            $rootScope.pageState.load = true;
            return id;
        }
    };
    var id = $rootScope.pageState.push(false);
    $http.get('/api/user').success(function (user) {
        $scope.$broadcast('user', user);
        $rootScope.pageState.push(true, id);
    }).error(function () {
        $scope.$broadcast('user', undefined);
        $rootScope.pageState.push(true, id);
    });

    $scope.signUp = function () {
        $mdDialog.show({
                controller: SignUpController,
                templateUrl: '../templates/index/dialog_signup.html',
                parent: angular.element(document.body)
            })
            .then(function () {

            })
            .then(function (user) {

            });
    }
});

function SignUpController($scope, $mdDialog) {
    $scope.cancel = function () {
        $mdDialog.cancel();
    }
}








