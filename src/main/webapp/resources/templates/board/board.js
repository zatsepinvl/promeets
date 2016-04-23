app.directive('board', function () {
    return {
        restrict: 'E',
        templateUrl: '/templates/board/board.html',
        scope: {
            brSave: '=',
            brModel: '=',
            brData: '=',
            brLoading: '='
        },
        link: function ($scope) {

            //init canvas
            var canvasEl = document.getElementById('board');
            var ctx = canvasEl.getContext('2d');
            ctx.canvas.width = window.innerWidth;
            ctx.canvas.height = window.innerHeight;

            //init fabric
            $scope.fabric = new fabric.Canvas('board');
            $scope.$watchCollection('brModel', function () {
                if ($scope.brModel) {
                    $scope.brModel.forEach(function (item) {
                        $scope.fabric.add(item);
                    })
                    $scope.render();
                }
            });

            $scope.$watch('brData', function () {
                if ($scope.brData) {
                    $scope.fabric.loadFromDatalessJSON($scope.brData);
                    $scope.render();
                }
            });

            $scope.text = '';
            $scope.fabric.on('mouse:down', function (options) {
                if (options.target && options.target.type == 'text') {
                    $scope.text = options.target.text;
                    $scope.selected = options.target;
                    $scope.$apply();
                }
                else {
                    $scope.selected = undefined;
                    $scope.text = '';
                    $scope.$apply();
                }
            });

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
                originatorEv = ev;
                $mdOpenMenu(ev);
            };

            $scope.save = function () {
                $scope.brSave && $scope.brSave($scope.fabric.toDatalessJSON());
            }
        }
    };

    function initBoard(board, items) {

    }
});