app.controller('boardCtrl', function ($scope, $stateParams, $http, MeetService, Entity) {
    $scope.meet = MeetService.get();
    $scope.loading = false;
    $scope.onSave = function (data) {
        $scope.loading = true;
        var board = {};
        clone($scope.board, board);
        board.data = JSON.stringify(data);
        Entity.update({entity: 'boards', id: board.boardId}, board, function () {
            $scope.loading = false;
        })
    };


    $http.get('/api/meets/' + $stateParams.meetId + '/boards?page=0')
        .success(function (board) {
            $scope.board = board;
        });

    $scope.$on('board', function (event, message) {
        $scope.board = message.data;
        $scope.$apply();
    })

});