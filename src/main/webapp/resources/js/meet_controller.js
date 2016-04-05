app.service('MeetService', function (Entity) {
    var value = {};
    var loaded = false;
    this.load = function (meetId, success, error) {
        if (!loaded || value && value.meetId && value.meetId != meetId) {
            loaded = true;
            Entity.get({entity: "meets", id: meetId}
                , function (meet) {
                    clone(meet, value);
                    value.time = moment(meet.time).local().format('DD MMMM YYYY HH:mm');
                    success && success(meet);
                }
                , function (err) {
                    error && error(err);
                });
            return value;
        }
        else {
            success && success(value);
            return value;
        }
    };
});

app.controller("meetCtrl", function ($scope, Entity, $stateParams, MeetService) {
    $scope.meetId = $stateParams.meetId;
    MeetService.load($scope.meetId, function (meet) {
        $scope.meet = meet;
        console.log(meet);
    });
});

app.controller("mainMeetCtrl", function ($scope, Entity, $stateParams, MeetService, UserService) {
    $scope.note = {};
    $scope.meetId = $stateParams.meetId;
    MeetService.load($scope.meetId, function (meet) {
        $scope.meet = meet;
    });

    $scope.notes = Entity.query({entity: "meets", id: $scope.meetId, d_entity: "notes"},
        function (notes) {

        });

    $scope.createNote = function () {
        if (!$scope.note.user) {
            $scope.note.user = UserService.load();
        }
        if (!$scope.note.meet) {
            $scope.note.meet = {meetId: $scope.meetId};
        }
        Entity.save({entity: "notes"}, $scope.note, function (note) {
            $scope.note.noteId = note.noteId;
            console.log($scope.note.value);
            $scope.notes.push($scope.note);
            $scope.note = {};
        });
    };

    $scope.removeNote = function (note) {
        $scope.notes.splice($scope.notes.indexOf(note), 1);
        Entity.remove({entity: "notes", id: note.noteId});
    };

    $scope.multiLine = function (str) {
        return str.replaceAll("\n", "<br/>");
    }
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