app.directive("calendar", function () {
    return {
        restrict: "E",
        templateUrl: "templates/calendar/calendar.html",
        scope: {
            selected: "=",
            model: "=",
            dayClick: "=",
            nextMonth: "=",
            prevMonth: '=',
            loading: '='
        },
        link: function ($scope) {
            $scope.load = false;
            $scope.$watch('model', function () {
                if ($scope.model.length < 1) {
                    return;
                }
                $scope.weeks.forEach(function (week) {
                    week.days.forEach(function (day) {
                        day.events = [];
                        $scope.model.forEach(function (event) {
                            if ((event.meet.time) && (day.date.isSame(event.meet.time, 'day'))) {
                                day.events.push(event);
                                if (!event.viewed) {
                                    day.notViewed = true;
                                }
                            }
                        });
                        !$scope.load && day.date.isSame($scope.selected, 'day') && $scope.select(day);
                    });
                });
                $scope.load = true;
            }, true);

            $scope.selected = $scope.selected ? $scope.selected.hour(0).minute(0).second(0).millisecond(0) : moment().hour(0).minute(0).second(0).millisecond(0);
            $scope.month = $scope.selected.clone();

            var start = $scope.selected.clone();
            _removeTime(start.date(1).day(0));

            _buildMonth($scope, start, $scope.month);

            $scope.select = function (day) {
                $scope.selected = day.date;
                $scope.dayClick && $scope.dayClick(day);
            };

            $scope.next = function () {
                var next = $scope.month.clone();
                _removeTime(next.month(next.month() + 1).date(1));
                $scope.month.month($scope.month.month() + 1);
                _buildMonth($scope, next, $scope.month);
                $scope.nextMonth && $scope.nextMonth($scope.month);
            };

            $scope.previous = function () {
                var previous = $scope.month.clone();
                _removeTime(previous.month(previous.month() - 1).date(1));
                $scope.month.month($scope.month.month() - 1);
                _buildMonth($scope, previous, $scope.month);
                $scope.prevMonth && $scope.prevMonth($scope.month);
            };


        }
    };

    function _removeTime(date) {
        return date.day(0).hour(0).minute(0).second(0).millisecond(0);
    }

    function _buildMonth(scope, start, month) {
        scope.weeks = [];
        var done = false, date = start.clone(), monthIndex = date.month(), count = 0;
        while (!done) {
            scope.weeks.push({days: _buildWeek(date.clone(), month)});
            date.add(1, "w");
            done = count++ > 2 && monthIndex !== date.month();
            monthIndex = date.month();
        }
    }

    function _buildWeek(date, month) {
        var days = [];
        for (var i = 0; i < 7; i++) {
            days.push({
                name: date.format("dd").substring(0, 1),
                number: date.date(),
                isCurrentMonth: date.month() === month.month(),
                isToday: date.isSame(new Date(), "day"),
                date: date,
                events: []
            });
            date = date.clone();
            date.add(1, "d");
        }
        return days;
    }
});

