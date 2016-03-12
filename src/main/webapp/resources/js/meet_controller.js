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