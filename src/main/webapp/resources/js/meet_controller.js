app.controller("meetCtrl", function ($scope, Entity, $state, UserService, MeetService, TextareaDialog, EventHandler) {
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

        $scope.multiLine = function (str) {
            return str.replaceAll("\n", "<br/>");
        }
    }
);
app.controller("meetUsersCtrl", function ($scope) {

});