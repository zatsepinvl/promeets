app.directive('board', function () {
    return {
        restrict: 'E',
        templateUrl: '/templates/board/board.html',
        scope: {
            brSave: '=',
            brEdit: '=',
            brCancel: '=',
            brData: '=',
            brLoading: '=',
            brLocked: '=',
            brEditing: '=',
            brEditor: '=',
            brFree: '='
        },
        link: function ($scope) {
            //init canvas
            var tempBoardData;

            function zoomIt(factor) {
                var canvas = $scope.fabric;
                /* canvas.setHeight(canvas.getHeight() * factor);
                 canvas.setWidth(canvas.getWidth() * factor);*/
                if (canvas.backgroundImage) {
                    var bi = canvas.backgroundImage;
                    bi.width = bi.width * factor;
                    bi.height = bi.height * factor;
                }
                var objects = canvas.getObjects();
                for (var i = 0; i < objects.length; i++) {
                    console.log(objects[i]);
                    var scaleX = objects[i].scaleX;
                    var scaleY = objects[i].scaleY;
                    var left = objects[i].left;
                    var top = objects[i].top;
                    objects[i].set(
                        {
                            //top: top * factor,
                            //left: left * factor,
                            scaleX: scaleX * factor,
                            scaleY: scaleY * factor
                        });
                }
                canvas.calcOffset();
            }

            //init fabric
            $scope.board = new fabric.Canvas('board');


            $scope.$watch('brData', function () {
                console.log('BOARD: DATA CHANGED');
                console.log($scope.brData);
                if ($scope.brData) {
                    tempBoardData = $scope.brData;
                    $scope.board.loadFromDatalessJSON($scope.brData);
                    var text = new fabric.Text('XXX', {left: 10, top: 10});
                    $scope.board.add(text);
                    zoomIt(2);
                    $scope.render();
                }
            });

            $scope.$watch('brEditing', function () {
                $scope.changeBoardState();
            });

            $scope.$watch('brLocked', function () {
                $scope.changeBoardState();
            });

            $scope.text = '';
            $scope.board.isDrawingMode=true;
            $scope.board.on('mouse:down', function (options) {
                if ($scope.brEditing) {
                    if (options.target && options.target.type == 'text') {
                        $scope.text = options.target.text;
                        $scope.selected = options.target;
                        $scope.$apply();
                    }
                    else {
                        $scope.unselect();
                        $scope.$apply();
                    }
                }
            });
            $scope.unselect = function () {
                $scope.selected = undefined;
                $scope.text = '';
            };

            $scope.$watch('text', function (value) {
                if ($scope.selected) {
                    $scope.selected.setText(value);
                    $scope.render();
                }
            });

            $scope.render = function () {
                $scope.board.renderAll();
            };

            $scope.openMenu = function ($mdOpenMenu, ev) {
                $mdOpenMenu(ev);
            };

            $scope.save = function () {
                $scope.brSave && $scope.brSave($scope.board.toDatalessJSON());
            };

            $scope.edit = function () {
                $scope.brEdit && $scope.brEdit();
                changeSelectedState($scope.board, true);
                $scope.render();
            };

            $scope.cancel = function () {
                $scope.fabric.loadFromDatalessJSON(tempBoardData);
                $scope.brCancel && $scope.brCancel();
                changeSelectedState($scope.board, false);
                $scope.render();
            };

            $scope.changeBoardState = function () {
                changeSelectedState($scope.board, $scope.brEditing);
                if (!$scope.brEditing) {
                    $scope.unselect();
                }
            }

        }
    };

    function changeSelectedState(board, state) {
        board.selection = state;
        board.forEachObject(function (o) {
            o.selectable = state;
        });
    }
});