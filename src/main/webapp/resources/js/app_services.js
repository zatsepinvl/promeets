//constants


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


//factories
app.factory('UserEntity', function ($resource) {
    return $resource('/api/users/:entity/:id/:d_entity', {
        entity: '@entity',
        id: '@id',
        d_entity: "@d_entity",
    }, {
        'update': {
            method: 'PUT'
        }
    });
});


app.service('AppService', function () {
    var today = moment();
    this.toTime = function (time) {
        time = moment(time).local();
        return time.isSame(today, 'day') ? time.format('HH:mm') : time.format('MM-DD-YY');
    };
});

//services
app.service('UserService', function ($http) {
    var value = {};
    var headers;
    this.load = function (success, error) {
        newMeets = [];
        $http.get('/api/users', {headers: headers})
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

app.service('UserMeetService', function ($http) {
    var newMeets = [];
    this.load = function () {
        newMeets = [];
        $http.get('/api/users/meets/new')
            .success(function (meets) {
                clone(meets, newMeets);
            });
        return newMeets;
    };
    this.getNewMeets = function () {
        return newMeets;
    }
});

app.service('UserMessageService', function ($http) {
    var newMessages = [];
    this.load = function () {
        newMessages = [];
        $http.get('/api/users/messages/new')
            .success(function (meets) {
                clone(meets, newMessages);
            });
        return newMessages;
    };
    this.getNewMessages = function () {
        return newMessages;
    }
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
    };
});

app.service('MeetService', function (Entity, $http) {
    var value;
    var notes;
    var tasks;
    var board = {};
    var page = 0;
    var meet;
    this.load = function (meetId, success, error) {
        value = {};
        notes = [];
        tasks = [];
        meet = meetId;
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
        $http.get('/api/meets/' + meetId + '/boards?page=' + page)
            .success(function (data) {
                clone(data, board);
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

    this.getBoard = function () {
        return board;
    }


});

app.service('GroupMeetsService', function ($http) {

    var pre = [];
    var current = [];
    var next = [];
    var currentTime = moment().day(0).hour(0).utc();
    var groupId;

    this.resolve = function (id) {
        groupId = id;
        currentTime = moment().day(0).hour(0).utc();
        var prevM = currentTime.clone().add(-1, 'month');
        var nextM = currentTime.clone().add(1, 'month');
        this.load(groupId, startOfMonth(currentTime.clone()), endOfMonth(currentTime.clone()), current);
        this.load(groupId, startOfMonth(prevM.clone()), endOfMonth(prevM.clone()), pre);
        this.load(groupId, startOfMonth(nextM.clone()), endOfMonth(nextM.clone()), next);
        return current;
    };

    this.load = function (groupId, start, end, data, success, error) {
        $http.get("/api/groups/" + groupId + "/meets/?start=" + start + "&end=" + end)
            .success(function (meets) {
                data.length = 0;
                meets.forEach(function (meet) {
                    meet.time = moment(meet.time).local();
                });
                clone(meets, data);
            })
            .error(function (error) {

            });
    };

    this.current = function () {
        return current;
    };

    this.getNext = function () {
        return next;
    };

    this.getPrev = function () {
        return pre;
    };

    this.next = function () {
        pre.length = 0;
        clone(current, pre);
        current.length = 0;
        clone(next, current);
        currentTime.add(1, 'month');
        var temp = currentTime.clone().add(1, 'month');
        this.load(groupId, startOfMonth(temp.clone()), endOfMonth(temp.clone()), next);
        return current;
    };

    this.previous = function () {
        next.length = 0;
        clone(current, next);
        current.length = 0;
        clone(pre, current);
        currentTime.add(-1, 'month');
        var temp = currentTime.clone().add(-1, 'month');
        this.load(groupId, startOfMonth(temp.clone()), endOfMonth(temp.clone()), pre);
        return current;
    };
});

function endOfMonth(date) {
    return date.endOf('month').valueOf();
}

function startOfMonth(date) {
    return date.date(1).hour(0).minute(0).millisecond(0).valueOf();
}

app.service('GroupChatService', function (appConst, $rootScope, $http, UserService, Entity, UserEntity) {
    var messages = [];
    var chat = {};
    var state = {loading: true};
    var page = 0;

    var updateMessage = function (message) {
        message.viewed = true;
        $rootScope.$emit('usermessageLocal', {
            action: appConst.ACTION.UPDATE,
            data: message,
            id: message.message.messageId,
            entity: 'usermessage'
        });
        UserEntity.update({entity: "messages", id: message.message.messageId}, message);
    };

    var loadPage = function (chatId, page, success, error) {
        $http.get("/api/users/chats/" + chatId + "/messages?page=" + page)
            .success(function (data) {
                success && success(data);
            })
            .error(function (er) {
                error && error(er);
            });
    };


    this.load = function (groupId, page) {
        state.loading = true;
        messages.length = 0;
        Entity.get({entity: "groups", id: groupId, d_entity: "chat"},
            function (data) {
                clone(data, chat);
                page = page ? page : 0;
                loadPage(chat.chatId, page, function (data) {
                    fillData(data);
                    state.loading = false;
                });
            });
    };


    this.loadNextPage = function () {
        var next = page + 1;
        state.loading = true;
        loadPage(chat.chatId, next,
            function (data) {
                if (data.length > 0) {
                    page++;
                }
                fillData(data);
                state.loading = false;
            },
            function () {
                state.loading = false;
            });
    };

    var fillData = function (userMessages) {
        userMessages.forEach(function (value) {
            if (!value.viewed && !value.sender) {
                updateMessage(value);
            }
            messages.push(value);
        });
    };
    this.get = function () {
        return messages;
    };

    this.getChat = function () {
        return chat;
    };

    this.getState = function () {
        return state;
    };

    this.update = updateMessage;
});

app.service('UserChatsService', function (UserEntity) {
    var chats = [];
    var state = {loading: true};
    this.resolve = function () {
        chats.length = 0;
        state.loading = true;
        UserEntity.query({entity: 'chats'},
            function (data) {
                clone(data, chats);
                state.loading = false;
            }, function (error) {
                state.error = error.data;
                state.loading = false;
            });
    };

    this.getChats = function () {
        return chats;
    };

    this.getState = function () {
        return state;
    }

});


app.service('UploadService', function (EventHandler, Upload) {
    this.upload = function (file, id, success, error) {
        if (!file) {
            return;
        }
        EventHandler.load('Uploading file');
        Upload.upload({
            url: '/api/files',
            data: {file: file, id: id}
        }).then(function (resp) {
            //success
            EventHandler.message('File has been uploaded');
            success && success(resp.data);
        }, function (resp) {
            EventHandler.message(resp.data.message);
            error && error(resp.data);
        }, function (evt) {
            //progress
        });
    }
})



