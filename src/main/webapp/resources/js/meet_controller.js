app.controller("meetCtrl", function ($scope, $rootScope, $http, $window,appConst, Entity, $state, UserService, MeetEditDialogService, MeetService, TextareaDialog, EventHandler) {
    $scope.meet = MeetService.get();
    $scope.user = UserService.get();
    $scope.notes = MeetService.getNotes();
    $scope.tasks = MeetService.getTasks();

    $scope.goBack = function () {
        $state.transitionTo('user.group.main', {groupId: $scope.meet.group.groupId});
    };

    $scope.createNote = function (event) {
        TextareaDialog.show('New note', 'Note text', undefined, event, function (result) {
            var note = {
                value: result,
                user: $scope.user,
                meet: $scope.meet
            };
            Entity.save({entity: "notes"}, note,
                function (data) {
                    note.noteId = data.noteId;
                    $scope.notes.push(note);
                },
                function (error) {
                    EventHandler.message(error.data.message);
                });
        });
    };

    $scope.createTask = function (event) {
        TextareaDialog.show('New task', 'Task text', undefined, event, function (result) {
            var task = {
                value: result,
                checked: false,
                user: $scope.user,
                meet: $scope.meet
            };
            Entity.save({entity: "tasks"}, task,
                function (data) {
                    task.taskId = data.taskId;
                    $scope.tasks.push(task);
                },
                function (error) {
                    EventHandler.message(error.data.message);
                });
        });
    };

    $scope.removeNote = function (note) {
        $scope.notes.splice($scope.notes.indexOf(note), 1);
        Entity.remove({entity: "notes", id: note.noteId});
    };

    $scope.removeTask = function (task) {
        $scope.tasks.splice($scope.tasks.indexOf(task), 1);
        Entity.remove({entity: "tasks", id: task.taskId});
    };

    $scope.updateTask = function (task) {
        task.checked = !task.checked;
        Entity.update({entity: 'tasks', id: task.taskId}, task);
    };

    $scope.$on('meetnote', function (event, message) {
        if (message.data.meet.meetId == $scope.meet.meetId) {
            if (message.action == appConst.ACTION.CREATE) {
                $scope.notes.push(message.data);
                EventHandler.setNew(message.data);
                EventHandler.message(
                    'New note by '
                    + message.data.user.firstName
                    + ' ' + message.data.user.lastName);
                $scope.$apply();
            }
            else if (message.action == appConst.ACTION.DELETE) {
                for (var i = 0; i < $scope.notes.length; i++) {
                    if ($scope.notes[i].noteId === message.id) {
                        $scope.notes.splice(i, 1);
                        $scope.$apply();
                        return;
                    }
                }
            }
        }
    });

    $scope.$on('meettask', function (event, message) {
        if (message.data.meet.meetId == $scope.meet.meetId) {
            if (message.action == appConst.ACTION.CREATE) {
                $scope.tasks.push(message.data);
                EventHandler.setNew(message.data);
                EventHandler.message(
                    'New task by '
                    + message.data.user.firstName
                    + ' ' + message.data.user.lastName);
                $scope.$apply();
            }
            else if (message.action == appConst.ACTION.DELETE) {
                for (var i = 0; i < $scope.tasks.length; i++) {
                    if ($scope.tasks[i].taskId === message.id) {
                        $scope.tasks.splice(i, 1);
                        $scope.$apply();
                        return;
                    }
                }
            }
            else if (message.action == appConst.ACTION.UPDATE) {
                for (var i = 0; i < $scope.tasks.length; i++) {
                    if ($scope.tasks[i].taskId === message.id) {
                        $scope.tasks[i] = message.data;
                        $scope.$apply();
                        return;
                    }
                }
            }
        }
    });


    $scope.editMeet = function (meet, event) {
        MeetEditDialogService.show(meet, event,
            function (result) {
                var meet = result;
                Entity.update({entity: "meets", id: meet.meetId}, meet,
                    function (data) {
                        $scope.meet = data;
                        EventHandler.message('Meeting has been updated');
                    },
                    function (error) {
                        EventHandler.message(error.data.message);
                    })
            }
        )
    };

    $scope.$on('usermeetinfo', function (event, message) {
        if (message.action == appConst.ACTION.UPDATE) {
            var sender = message.data.user;
            if (message.data.online && message.data.connected) {
                EventHandler.message(sender.firstName + ' ' + sender.lastName + ' join connect to conference', sender);
            }
            else if (message.data.online && !message.data.connected) {
                EventHandler.message(sender.firstName + ' ' + sender.lastName + ' join meeting', sender);
            }
            else if (!message.data.online) {
                EventHandler.message(sender.firstName + ' ' + sender.lastName + ' leave meet', sender);
            }
            for (var i = 0; i < $scope.meetUsers.length; i++) {
                if ($scope.meetUsers[i].user.userId === message.data.user.userId) {
                    clone(message.data, $scope.meetUsers[i]);
                    $scope.$apply();
                    return;
                }
            }
        }
    });

    var stopWatch = $rootScope.$on('$stateChangeStart',
        function (event, toState, toParams, fromState, fromParams) {
            console.log('changing state');
            $scope.currentMeetUser.online = false;
            $scope.currentMeetUser.connected = false;
            $http.put('/api/users/meets/info/' + $scope.currentMeetUser.meet.meetId, $scope.currentMeetUser)
                .success(function (data, status, headers, config) {
                });
            stopWatch();
        });

    $scope.meetUsers = MeetService.getMeetUsers();
    $scope.currentMeetUser = MeetService.getCurrentMeetUser();

    $window.onbeforeunload = function () {
        $scope.currentMeetUser.online = false;
        $scope.currentMeetUser.connected = false;
        $http.put('/api/users/meets/info/' + $scope.currentMeetUser.meet.meetId, $scope.currentMeetUser)
            .success(function (data, status, headers, config) {
            })
    };

});


