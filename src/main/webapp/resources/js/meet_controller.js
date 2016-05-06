app.controller("meetCtrl", function ($scope, appConst, Entity, $state, UserService, MeetService, TextareaDialog, EventHandler, $window, $http) {
    $scope.meet = MeetService.get();
    $scope.user = UserService.get();
    $scope.notes = MeetService.getNotes();
    $scope.tasks = MeetService.getTasks();
	
	$scope.meetUsers = MeetService.getMeetUsers();
	$scope.userMeet = MeetService.getUserMeet();
	
    $scope.goBack = function () {
        $state.transitionTo('user.group.main', {groupId: $scope.meet.group.groupId});
    };

    $scope.createNote = function (event) {
        TextareaDialog.show('New note', 'Note text', undefined, event, function (result) {
            var note = {
                value: result,
                user: $scope.user,
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

    $scope.createTask = function (event) {
        TextareaDialog.show('New task', 'Task text', undefined, event, function (result) {
            var task = {
                value: result,
                checked: false,
                user: $scope.user,
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
	
	$scope.$on('meetinfo', function (event, message) 
	{
        if (message.action == appConst.ACTION.UPDATE) 
		{
            for (var i = 0; i < $scope.meetUsers.length; i++)
			{
				if ($scope.meetUsers[i].user.userId === message.data.user.userId) {
                    $scope.meetUsers[i] = message.data;
                    $scope.$apply();
                    return;
                }
			}
			
			$scope.meetUsers.push(message.data);
			$scope.$apply();
        }
		
    });
	
	$window.onbeforeunload = function () {
		$scope.userMeet.online = false;
		$http.put('/api/users/meets/'+$scope.userMeet.meet.meetId, $scope.userMeet)
            .success(function (data, status, headers, config) {
            })
		//return ('bye bye');
	}
	
});

app.controller("meetUsersCtrl", function ($scope, UserEntity, MeetService, $http, UserService, MeetService, UserMeetService) 
{
	$scope.meet = MeetService.get();
    $scope.user = UserService.get();
			
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


