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
        /*    var canvas = document.getElementById('board');
            var parent = document.getElementById('board-container');*/
            // resize the canvas to fill browser window dynamically
            window.addEventListener('resize', function(){console.log('resize');}, false);
          /*  function resizeCanvas() {
                ctx.canvas.width = window.innerWidth;
                ctx.canvas.height = window.innerHeight;
                $scope.render && $scope.render();
            }
            resizeCanvas();*/

            function zoomIt(factor) {
                var canvas = $scope.fabric;
               /* canvas.setHeight(canvas.getHeight() * factor);
                canvas.setWidth(canvas.getWidth() * factor);*/
                if (canvas.backgroundImage) {
                    // Need to scale background images as well
                    var bi = canvas.backgroundImage;
                    bi.width = bi.width * factor; bi.height = bi.height * factor;
                }
                var objects = canvas.getObjects();
                for (var i=0; i<objects.length; i++) {
                    console.log(objects[i]);
                    var scaleX = objects[i].scaleX;
                    var scaleY = objects[i].scaleY;
                    var left = objects[i].left;
                    var top = objects[i].top;

                    var tempScaleX = scaleX * factor;
                    var tempScaleY = scaleY * factor;
                    var tempLeft = left * factor;
                    var tempTop = top * factor;

                    objects[i].scaleX = tempScaleX;
                    objects[i].scaleY = tempScaleY;
                    objects[i].left = tempLeft;
                    objects[i].top = tempTop;

                    objects[i].setCoords();
                    console.log(objects[i]);
                }
               // canvas.renderAll();
                canvas.calcOffset();
            }



            function fitToContainer(){
               /* canvas.style.width='100%';
                canvas.style.height='100%';
                canvas.width  = canvas.offsetWidth;
                canvas.height = canvas.offsetHeight;
*/
                console.log(parent.offsetHeight);
               /* canvas.width = parent.offsetWidth;
                canvas.height = parent.offsetHeight;*/
            }
            fitToContainer();

            //init fabric
            $scope.fabric = new fabric.Canvas('board');


            $scope.$watch('brData', function () {
                console.log('BOARD: DATA CHANGED');
                console.log($scope.brData);
                if ($scope.brData) {
                    tempBoardData = $scope.brData;
                    $scope.fabric.loadFromDatalessJSON($scope.brData);
                    /*    var text = new fabric.Text('hello world', { left: 100, top: 100 });
                     $scope.fabric.add(text);*/
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
            $scope.fabric.on('mouse:down', function (options) {
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
                $scope.fabric.renderAll();
            };

            $scope.openMenu = function ($mdOpenMenu, ev) {
                $mdOpenMenu(ev);
            };

            $scope.save = function () {
                $scope.brSave && $scope.brSave($scope.fabric.toDatalessJSON());
            };

            $scope.edit = function () {
                $scope.brEdit && $scope.brEdit();
                changeSelectedState($scope.fabric, true);
                $scope.render();
            };

            $scope.cancel = function () {
                $scope.fabric.loadFromDatalessJSON(tempBoardData);
                $scope.brCancel && $scope.brCancel();
                changeSelectedState($scope.fabric, false);
                $scope.render();
            };

            $scope.changeBoardState = function () {
                changeSelectedState($scope.fabric, $scope.brEditing);
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