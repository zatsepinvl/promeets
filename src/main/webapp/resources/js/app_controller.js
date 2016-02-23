var app = angular.module('app', ['ngMaterial', 'ngResource', 'ngRoute']);


//configuration for templates routing
app.config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/', {
            redirectTo: '/group/1/meets'
        }).when('/group/:groupId', {
            templateUrl: '../templates/group.html',
            controller: 'groupCtrl'
        }).when('/group/:groupId/:tab', {
            templateUrl: '../templates/group.html',
            controller: 'groupCtrl'
        }).when('/edit_meet/:meetId', {
            templateUrl: '../templates/edit_meet.html',
            controller: 'editMeetCtrl'
        }).when('/edit_group/:groupId', {
            templateUrl: '../templates/edit_group.html',
            controller: 'editGroupCtrl'
        }).otherwise('/');
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

//group controller
app.controller('groupCtrl', function ($routeParams, $scope, Entity) {

    $scope.tab = $routeParams.tab;
    if ($scope.tab == undefined) {
        $scope.tab = "meets";
    }

    var group_admin = new User(0, "Mike");
    var group = new Group(777);
    group.setAdmin(group_admin);
    group.title = "NetCracker";
    group.status = "Super-puper group for everybody.";

    /*изменить при подгрузке изображения*/
    group.setImage(new File(0, "../image/group.jpg"));
    $scope.group = group;

    $scope.edit_header_mode = false;
    $scope.edit_status_mode = false;
    $scope.status_icon = false;
    $scope.header_icon = false;

    $scope.switchHeaderMode = function (val) {
        if (group.title != undefined) {
            $scope.edit_header_mode = val;
        }
    };
    $scope.switchStatusMode = function (val) {
        console.log(group.status);
        if (group.status != "") {
            $scope.edit_status_mode = val;
        }

    };

});

//meet list controller
app.controller('meetListCtrl', function ($scope, Entity) {
    $scope.error = false;

    var loadMeets = function () {
        $scope.meets = Entity.query({entity: "meets"}, function (meets) {
            meets.forEach(function (meet) {
                meet.time = new Date(meet.time);
            });
        }, function () {
            $scope.meet_load_error = "Can't load meets. Try again later."
        });
    };
    loadMeets();

});

//edit meet controller
app.controller("editMeetCtrl", function ($routeParams, $scope, Entity) {
    $scope.load = false;
    var id = $routeParams.meetId;
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
    };

    $scope.removeAim = function (aim) {
        $scope.aims.splice($scope.aims.indexOf(aim), 1);
    };

    $scope.save = function () {
        Entity.save({entity: "meets"}, $scope.meet);
        var aims = $scope.aims;
        for (var i = 0; i < aims.length; i++) {
            Entity.save({entity: "meet_aims"}, aims[i]);
            $scope.initAims.splice($scope.initAims.indexOf(aims[i]), 1);
        }
        aims = $scope.initAims;
        for (i = 0; i < aims.length; i++) {
            Entity.remove({entity: "meet_aims", id: aims[i].aimId});
        }
        window.history.back();
    };

    $scope.cancel = function () {
        window.history.back();
    }
});


/*app.controller('editGroupCtrl', function($routeParams, $scope, Entity){

 var groupId = parseInt($routeParams.groupId);
 if (groupId != undefined) {
 var group_admin = new User(0, "Mike");
 var group = new Group(groupId);
 group.name = "Test group";
 group.status = "This group is for learning English";
 group.setAdmin(group_admin);
 group.createdTime = new Date(2015, 9, 5);
 group.type = new GroupType(0, "Private");
 group.setImage(new File(0, "../image/group.jpg"));
 for (var i = 0; i < 3; i++) {
 group.addUser(new User(i, "user " + i));
 }
 var action = "update";
 $scope.action = action;
 $scope.group = group;
 $scope.removeUser = function (number) {
 $scope.group.users.splice(number, 1);
 };
 $scope.cancel = function () {
 window.history.back();
 }
 }
 }); */


