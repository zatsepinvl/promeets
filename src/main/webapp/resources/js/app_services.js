//constants


//factories
app.factory('Entity', function ($resource) {
    return $resource('/api/:entity/:id/:d_entity/:d_id', {
        entity: '@entity',
        id: '@id',
        d_entity: '@d_entity',
        d_id: '@d_id'
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
        d_entity: '@d_entity'
    }, {
        'update': {
            method: 'PUT'
        }
    });
});


app.service('AppService', function (appConst) {
    var today = moment();

    this.toShortDayTime = function (time) {
        time = moment(time).local();
        return time.isSame(today, 'day') ? time.format(appConst.TIME_FORMAT.TIME) : time.format(appConst.TIME_FORMAT.DAY_SHORT);
    };

    this.toDayTime = function (time) {
        return moment(time).local().format(appConst.TIME_FORMAT.DAY_TIME);
    };

    this.toTime = function (time) {
        return moment(time).local().format(appConst.TIME_FORMAT.TIME);
    };

    this.fio = function (user) {
        return user.firstName + ' ' + user.lastName;
    }
});

//services
app.service('UserService', function ($http) {
    var value = {};
    var headers;
    this.load = function (success, error) {
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
	var userMeets = [];
    this.load = function (meetId) {
        newMeets = [];
        $http.get('/api/users/meets/new')
            .success(function (meets) {
                clone(meets, newMeets);
            });
        return newMeets;
    };
    this.getNewMeets = function () {
        return newMeets;
    };
	this.get = function () {
		return userMeets;
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

app.service('MeetService', function (Entity, UserEntity, $http) {
    var value;
    var notes = [];
    var tasks = [];
    var cards = [];
	
	var meetUsers = [];
	var currentUserMeet = {};
    var page = 0;
    var meet;
	
    this.load = function (meetId, success, error) {
        value = {};
        notes.length = 0;
        tasks.length = 0;
        cards.length = 0;
        meet = meetId;
        Entity.get({entity: "meets", id: meetId},
            function (meet) {
                clone(meet, value);
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
        $http.get('/api/meets/' + meetId + '/cards?page=' + page)
            .success(function (data) {
                clone(data, cards);
            });
		UserEntity.get({entity: "meets", id: meetId},
        function (data) {
            var userMeet = data;
			userMeet.online = true;
			UserEntity.update({entity: "meets", id: meetId}, userMeet);
			clone(userMeet, currentUserMeet);
        });
		$http.get('/api/meets/'+meetId+'/info')
			.success(function (meets) {
				for (var i=0; i<meets.length; i++) {
					if (meets[i].user.userId == currentUserMeet.user.userId) {
						meets.splice(i,1);
						break;
					}
				}
				clone(meets, meetUsers);
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

    this.getCards = function () {
        return cards;
    }
	
	this.getMeetUsers = function () {
		return meetUsers;
	}
	
	this.getUserMeet = function () {
		return currentUserMeet;
	}
});

app.service('GroupMeetsService', function ($http) {

    var pre = [];
    var current = [];
    var next = [];
    var currentTime;
    var groupId;
    this.resolve = function (id) {
        groupId = id;
        currentTime = moment();
        var prevM = currentTime.clone().add(-1, 'month');
        var nextM = currentTime.clone().add(1, 'month');
        this.load(groupId, startOfMonth(currentTime), endOfMonth(currentTime), current);
        this.load(groupId, startOfMonth(prevM), endOfMonth(prevM), pre);
        this.load(groupId, startOfMonth(nextM), endOfMonth(nextM), next);
        return current;
    };

    this.load = function (groupId, start, end, data, success, error) {
        $http.get("/api/groups/" + groupId + "/usermeets/?start=" + start + "&end=" + end)
            .success(function (meets) {
                data.length = 0;
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
        this.load(groupId, startOfMonth(temp), endOfMonth(temp), next);
        return current;
    };

    this.previous = function () {
        next.length = 0;
        clone(current, next);
        current.length = 0;
        clone(pre, current);
        currentTime.add(-1, 'month');
        var temp = currentTime.clone().add(-1, 'month');
        this.load(groupId, startOfMonth(temp), endOfMonth(temp), pre);
        return current;
    };
});

function endOfMonth(date) {
    return date.utc().endOf('month').valueOf();
}

function startOfMonth(date) {
    return date.utc().date(1).hour(0).minute(0).millisecond(0).valueOf();
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
    this.upload = function (file, id, success, error, progress) {
        if (!file) {
            return;
        }
        Upload.upload({
            url: '/api/files',
            data: {file: file, id: id}
        }).then(function (resp) {
            //success
            success && success(resp.data);
        }, function (resp) {
            error && error(resp.data);
        }, function (evt) {
            progress && progress(evt.loaded / evt.total * 100);
        });
    }
});

app.service('UserInfoService', function (Entity) {
    var userInfo = {};
    var state = {loading: true};
    this.resolve = function (userId) {
        state.loading = true;
        Entity.get({entity: 'users', id: userId, d_entity: 'info'},
            function (data) {
                clone(data, userInfo);
                state.loading = false;
            })
    };
    this.get = function () {
        return userInfo;
    };

    this.getState = function () {
        return state;
    };

    this.update = function (success, error) {
        Entity.update({entity: 'users', id: userInfo.user.userId, d_entity: 'info'}, userInfo,
            function (data) {
                clone(data, userInfo);
                success && success();
            },
            function (resp) {
                error && error(resp.data.message);
            });
    }
});


