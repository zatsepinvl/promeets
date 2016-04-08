//constants


//directives
app.directive('complexPassword', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function (password) {
                var hasUpperCase = /[A-Z]/.test(password);
                var hasLowerCase = /[a-z]/.test(password);
                var hasNumbers = /\d/.test(password);
                var hasNonalphas = /\W/.test(password);
                var characterGroupCount = hasUpperCase + hasLowerCase + hasNumbers + hasNonalphas;

                if ((password.length >= 8) && (characterGroupCount >= 3)) {
                    ctrl.$setValidity('complexity', true);
                    return password;
                }
                else {
                    ctrl.$setValidity('complexity', false);
                    return undefined;
                }

            });
        }
    }
});

app.directive('time', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function (time) {
                console.log(ctrl.$viewValue);
                if (time.length == 0) {
                    ctrl.$setValidity('timeComplex', true);
                    return time;
                }
                var hours = time.split(':')[0];
                var minutes = time.split(':')[1];
                if (time.length > 5
                    || !minutes
                    || hours < 0
                    || hours > 23
                    || minutes < 0
                    || minutes > 59
                    || hours.length < 2
                    || minutes.length < 2) {
                    ctrl.$setValidity('timeComplex', false);
                    return undefined;
                }
                ctrl.$setValidity('timeComplex', true);
                return time;
            });
        }
    }
});

//factories
app.factory('Entity', function ($resource) {
    return $resource('/api/:entity/:id/:d_entity', {
        entity: '@entity',
        id: '@id',
        d_entity: "@d_entity"
    }, {
        'update': {
            method: 'PUT'
        }
    });
});


//services
app.service('EventHandler', function ($mdToast) {
    this.message = function (message) {
        $mdToast.show(
            $mdToast.simple()
                .textContent(message)
                .position('right bottom')
                .hideDelay(3000)
                .action('CLOSE')
        );
    };
});
app.service('UserService', function ($http) {
    var value = {};
    var headers;
    this.load = function (success, error) {
        $http.get('/api/user', {headers: headers})
            .success(function (user) {
                clone(user, value);
                success && success(value);
                headers = undefined;
            })
            .error(function (err) {
                error && error(err);
            });
        return value;
    };

    this.get = function () {
        return value;
    };

    this.logout = function () {
        value = {};
    };

    this.login = function (email, password, success, error) {
        headers = {
            authorization: "Basic "
            + btoa(email + ":" + password)
        };
        this.load(success, error);
    };
});


app.service('GroupService', function (Entity) {
    var value;
    var members;
    this.load = function (groupId, success, error) {
        value = {};
        members = [];
        Entity.get({entity: "groups", id: groupId},
            function (group) {
                clone(group, value);
                success && success(value);
            },
            function (err) {
                error && error(err);
            });
        Entity.query({entity: "groups", id: groupId, d_entity: "users"}, function (data) {
            clone(data, members);
        });
        return value;
    };
    this.get = function () {
        return value;
    };

    this.getMembers = function () {
        return members;
    }
});

app.service('MeetService', function (Entity) {
    var value;
    var notes;
    var tasks;
    this.load = function (meetId, success, error) {
        value = {};
        notes = [];
        tasks = [];
        Entity.get({entity: "meets", id: meetId},
            function (meet) {
                clone(meet, value);
                value.time = moment(meet.time).local().format('DD MMMM YYYY HH:mm');
                success && success(value);
            },
            function (err) {
                error && error(err);
            });
        Entity.query({entity: "meets", id: meetId, d_entity: "notes"},
            function (data) {
                clone(data, notes);
            });
        Entity.query({entity: "meets", id: meetId, d_entity: "tasks"},
            function (data) {
                clone(data, tasks)
            });
        return value;
    };

    this.get = function () {
        return value;
    };

    this.getNotes = function () {
        return notes;
    };

    this.getTasks = function () {
        return tasks;
    };
});

