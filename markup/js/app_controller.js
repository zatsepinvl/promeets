var app = angular.module('app', ['ngMaterial', 'ngResource']);

//configuration for angular_material_style
app.config(function ($mdThemingProvider) {
    $mdThemingProvider.definePalette('amazingPaletteName', {
        '50': '2e7d32',
        '100': '2e7d32',
        '200': '2e7d32',
        '300': '2e7d32',
        '400': '2e7d32',
        '500': 'ef6c00',
        '600': '2e7d32',
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
        .primaryPalette('amazingPaletteName')
});

//factories
app.factory('Entity', function ($resource) {
    return $resource('http://localhost:8080/:entity/:id/:d_entity', {
        entity: '@entity',
        id: '@id',
        d_entity: "@d_entity"
    }, {
        'update': {
            method: 'PUT'
        }
    });
});


//edit meet controller
app.controller("editMeetCtrl", function ($location, $scope, Entity) {
    $scope.load = false;
    var id = $location.search().id;
    if (id != undefined) {
        $scope.meet = Entity.get({entity: "meets", id: id}, function () {
            $scope.aims = Entity.query({entity: "meets", id: id, d_entity: "aims"},
                function (aims) {
                    $scope.initAims = [];
                    for (var i = 0; i < aims.length; i++) {
                        $scope.initAims.push(aims[i]);
                    }
                    $scope.meet.time = new Date($scope.meet.time);
                    $scope.load = true;
                });
        });
    }

    $scope.createAim = function () {
        var aim = {meet: $scope.meet, value: $scope.aim};
        $scope.aims.push(aim);
        $scope.aim = "";
        console.log($scope.aims);
    }

    $scope.removeAim = function (aim) {
        $scope.aims.splice($scope.aims.indexOf(aim), 1);
    }

    $scope.save = function () {
        Entity.save({entity: "meets"}, $scope.meet);
        var aims = $scope.aims;
        for (var i = 0; i < aims.length; i++) {
            Entity.save({entity: "meet_aims"}, aims[i]);
            $scope.initAims.splice($scope.initAims.indexOf(aims[i]), 1);
        }
        var aims = $scope.initAims;
        for (var i = 0; i < aims.length; i++) {
            Entity.remove({entity: "meet_aims", id: aims[i].aimId});
            console.log(aims[i]);
        }
        window.location = "index.html";
    };
    $scope.cancel = function () {
        window.location = "index.html";
    }
});

//meet list controller
app.controller('meetListCtrl', function ($scope, Entity) {
    $scope.load = false;
    $scope.meets = Entity.query({entity: "meets"}, function (meets) {
        var meets = $scope.meets;
        meets.forEach(function (meet, i, meets) {
            meet.time = new Date(meet.time);

        });
        $scope.load = true;
    });
    $scope.editMeet = function (meet) {
        window.location = "edit_meet.html#?id=" + meet.meetId;
    };
});