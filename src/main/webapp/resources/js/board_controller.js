app.controller('boardCtrl', function ($scope, $stateParams, $http, MeetService, Entity, UserService) {
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
        })
    };

    $scope.onEdit = function () {
        $scope.free = false;
        $scope.board.editor = $scope.user;
        Entity.update({entity: 'boards', id: $scope.board.boardId}, $scope.board, function () {
        })
    };

    $scope.onCancel = function () {
        $scope.free = true;
        $scope.board.editor = undefined;
        Entity.update({entity: 'boards', id: $scope.board.boardId}, $scope.board, function () {
        })
    };


    $scope.$on('board', function (event, message) {
        $scope.board = message.data;
        $scope.$apply();
    })


});