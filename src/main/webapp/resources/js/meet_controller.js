app.controller("meetCtrl", function ($scope, Entity, $stateParams, UserService, MeetService, TextareaDialog, EventHandler) {
        $scope.meet = MeetService.get();
        $scope.notes = MeetService.getNotes();
        $scope.tasks = MeetService.getTasts();

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
                        notes.push(note);
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
                        notes.push(task);
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

        $scope.multiLine = function (str) {
            return str.replaceAll("\n", "<br/>");
        }
    }
)
;
