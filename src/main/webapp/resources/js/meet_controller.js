app.controller("meetCtrl", function ($scope, appConst, Entity, $state, UserService, MeetService, TextareaDialog, EventHandler) {
    $scope.meet = MeetService.get();
    $scope.notes = MeetService.getNotes();
    $scope.tasks = MeetService.getTasks();


    $scope.goBack = function () {
        $state.transitionTo('user.group.main', {groupId: $scope.meet.group.groupId});
    };

    $scope.createNote = function () {
        TextareaDialog.show('New note', 'Note text', undefined, function (result) {
            var note = {
                value: result,
                user: UserService.get(),
                meet: {meetId: $scope.meet.meetId}
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

    $scope.createTask = function () {
        TextareaDialog.show('New task', 'Task text', undefined, function (result) {
            var task = {
                value: result,
                checked: false,
                user: UserService.get(),
                meet: {meetId: $scope.meet.meetId}
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
});

app.controller("meetUsersCtrl", function ($scope, UserEntity, MeetService, $http) 
{
	$scope.userMeets = [];
	$http.get('/api/users/meets/188/all')
        .success(function (userMeets) 
		{
			$scope.userMeets = userMeets;
        });
		
	UserEntity.get({entity: "meets", id: 188},
        function (data) 
		{
            var userMeet = data;
			data.online = true;
			UserEntity.update({entity: "meets", id: userMeet.meet.meetId}, userMeet);
        });
			
	$scope.$on('userMeet', function (event, message) 
	{
        if (message.action == appConst.ACTION.UPDATE) 
		{
            for (var i = 0; i < $scope.userMeets.length; i++)
			{
				if ($scope.userMeets[i].meet.meetId === message.id) {
                        $scope.userMeets[i] = message.data;
                        $scope.$apply();
                        return;
                    }
			}
        }
		
    });

});


