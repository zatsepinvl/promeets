var app = angular.module('app', ['ngMaterial', 'ngResource', 'ngRoute']);


//configuration for templates routing
app.config(function ($routeProvider, $httpProvider) {
        //$locationProvider.html5Mode(true);
        /* $routeProvider.when('/group/:groupId', {
         templateUrl: '../templates/group.html',
         controller: 'groupCtrl'
         }).when('/edit_meet/:meetId', {
         templateUrl: '../templates/dialog_edit_meet.html',
         controller: 'editMeetCtrl'
         }).when('/edit_group/:groupId', {
         templateUrl: '../templates/edit_group.html',
         controller: 'editGroupCtrl'
         }).otherwise('/')*/
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

//login controller
app.controller('loginCtrl', function ($scope, $http) {
    $scope.error = false;
    $scope.loading = false;
    var authenticate = function (user, callback) {
        $scope.error = false;
        $scope.loading = true;
        var headers = user ? {
            authorization: "Basic "
            + btoa(user.email + ":" + user.password)
        } : {};
        $http.get('/api/user', {headers: headers}).success(function (user) {
            if (user.email) {
                window.location.pathname = "/group/1";
            }
            else {
                $scope.loading = false;
            }
            callback && callback(data);
        }).error(function (error) {
            $scope.loading = false;
            $scope.error = user ? true : false;
            callback && callback(error);
        });
    };
    //authenticate();
    $scope.user = {};
    $scope.login = function () {
        authenticate($scope.user);
    };
});

//group controller
app.controller('groupCtrl', function ($routeParams, $scope, Entity, $mdDialog) {
    $scope.groupId = location.pathname.split("/")[2];
    $scope.group = Entity.get({entity: "groups", id: $scope.groupId}, function (data) {
        $scope.tab = "meets";
    });
    $scope.editGroup = function () {
        $mdDialog.show({
                controller: EditGroupDialogController,
                templateUrl: '../templates/group/dialog_edit_group.html',
                parent: angular.element(document.body),
                clickOutsideToClose: true,
                locals: {
                    group: $scope.group
                }
            })
            .then(function (answer) {
                //
            }, function () {
                //
            });
    }
});

function cloneGroup(group) {
    return {
        title: group.title,
        type: group.type,
        status: group.status
    };
}

function EditGroupDialogController($scope, group, $http, Entity, $mdDialog) {
    $http.get('/api/group_types').success(function (types) {
        $scope.types = types;
        $scope.load=true;
    });
    $scope.group = cloneGroup(group);
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
    $scope.save = function () {
        group.title = $scope.group.title;
        group.status = $scope.group.status;
        group.type = {typeId: $scope.group.type.typeId, name: $scope.group.type.name};
        console.log(group.type);
        Entity.update({entity: "groups", id: group.groupId}, group);
        $mdDialog.hide();
    };
}

//meet list controller
app.controller('meetListCtrl', function ($scope, Entity, $mdDialog, $mdMedia) {
    $scope.error = false;
    $scope.load = false;
    var loadMeets = function () {
        $scope.meets = Entity.query({entity: "groups", id: $scope.groupId, d_entity: "meets"}, function (meets) {
            meets.forEach(function (meet) {
                meet.time = new Date(meet.time);
            });
            $scope.load = true;
        }, function () {
            $scope.load = true;
            $scope.error = true;
            $scope.load_error = "Can't load meets. Try again later.";
        });
    };
    loadMeets();
});


//edit meet controller
app.controller("editMeetCtrl", function ($scope, Entity) {
    $scope.load = false;
    var id = window.location.pathname.split("/")[2];
    console.log(id.substr(1, id.length));
    if (id != undefined && id[0] != 'g') {
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
    else {
        $scope.meet = new Meet();
        $scope.types = getMeetTypes();
        /*Entity.get({entity: "meet_types"}, function () {
         $scope.load = true;
         $scope.meet.type = $scope.types[0];
         });*/
        $scope.meet.group = Entity.get({entity: "groups", id: id.substr(1, id.length)},
            function () {
                $scope.load = true;
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
        console.log($scope.meet);
        Entity.save({entity: "meets"}, $scope.meet);
        var aims = $scope.aims;
        if (aims) {
            for (var i = 0; i < aims.length; i++) {
                Entity.save({entity: "meet_aims"}, aims[i]);
                $scope.initAims.splice($scope.initAims.indexOf(aims[i]), 1);
            }
            aims = $scope.initAims;
            for (i = 0; i < aims.length; i++) {
                Entity.remove({entity: "meet_aims", id: aims[i].aimId});
            }
        }
        window.location = "/group/" + $scope.meet.group.groupId;
    };

    $scope.cancel = function () {
        window.history.back();
    }
});



