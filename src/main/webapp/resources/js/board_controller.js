app.controller('boardCtrl', function ($scope, $stateParams, $http, MeetService, Entity, UserService) {
    var updateTimeOut = 3000;
    var timerId = -1;

    $scope.meet = MeetService.get();
    $scope.board = MeetService.getBoard();
    $scope.loading = false;
    $scope.free = true;
    $scope.locked = false;
    $scope.user = UserService.get();

    $scope.onSave = function (data) {
        $scope.loading = true;
        $scope.board.data = JSON.stringify(data);
        Entity.update({entity: 'boards', id: $scope.board.boardId}, $scope.board, function () {
            $scope.loading = false;
            // $scope.board = tempBoard;
        });
    };

    $scope.onEdit = function () {
        $scope.free = false;
        $scope.board.editor = $scope.user;
        Entity.update({entity: 'boards', id: $scope.board.boardId}, $scope.board);
        setTimer();
    };

    var setTimer = function () {
        timerId = setTimeout(function () {
            if ($scope.board.editor) {
                Entity.update({entity: 'boards', id: $scope.board.boardId}, $scope.board);
                setTimer();
            }
        }, updateTimeOut);
    };

    $scope.onCancel = function () {
        $scope.free = true;
        $scope.board.editor = undefined;
        if (timerId != -1) {
            clearTimeout(timerId);
        }
        Entity.update({entity: 'boards', id: $scope.board.boardId}, $scope.board);
    };


    $scope.$on('board', function (event, message) {
        if (message.data.editor && message.data.editor.userId == $scope.user.userId) {
            return;
        }
        $scope.board = message.data;
        $scope.$apply();
    });


});