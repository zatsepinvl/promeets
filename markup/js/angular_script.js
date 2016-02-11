var meet = {
    name: "Meet",
    time: new Date(1970, 0, 1),
    description: "You are always welcome.",
    type: "remote",
    targets: ["Whe everything sucks?"],
    meet_time: new Date(1970, 0, 1)
};

var app = angular.module('app', ['ngMaterial'])
    .config(function ($mdThemingProvider) {
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

app.controller("editMeetCtrl", function ($scope) {
    $scope.meet = meet;
    $scope.createTarget = function () {
        $scope.meet.targets.push($scope.target);
        $scope.target="";
    }
    $scope.removeTarget = function (target) {
        $scope.meet.targets.splice( $scope.meet.targets.indexOf(target), 1);
    }
});


app.controller('meetListCtrl', function ($scope) {
    var meet1 = {
        name: "Meet",
        time: new Date(1970, 11, 12, 14, 50),
        description: "You are always welcome.",
        type: "remote"
    };
    var meet2 = {
        name: "Meet2",
        time: new Date(1970, 11, 12, 14, 50),
        description: "You are always welcome.",
        type: "remote"
    };
    var meet3 = {
        name: "Meet3",
        time: new Date(1970, 11, 12, 14, 50),
        description: "You are always welcome.",
        type: "remote"
    };

    $scope.meets = [meet1, meet2, meet3];

    $scope.editMeet = function () {
        window.location = "edit_meet.html";
    };
});