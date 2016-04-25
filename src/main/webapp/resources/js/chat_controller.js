app.controller('chatController', function ($document, $scope, appConst, AppService, $http, GroupService, UserService, Entity, UserEntity, GroupChatService) {
    $scope.user = UserService.get();
    $scope.group = GroupService.get();
    $scope.messages = undefined;
    $scope.messages = GroupChatService.get();
    $scope.chat = GroupChatService.getChat();
    $scope.status = GroupChatService.getState();
    $scope.glue = true;
    $scope.time = AppService.toTime;


    $scope.send = function () {
        if ($scope.text == "") {
            return;
        }
        var message = {
            text: $scope.text,
            user: $scope.user,
            chat: $scope.chat,
            time: moment().valueOf()
        };
        $scope.text = "";
        UserEntity.save({entity: "messages"},
            {
                message: message,
                user: $scope.user,
                viewed: false
            },
            function (value) {
                $scope.messages.push(value);
            });
    };

    $scope.update = function (message) {
        message.viewed = true;
        UserEntity.update({entity: "messages", id: message.message.messageId}, message);
    };

    $scope.$on('usermessage', function (event, message) {
        if (message.action == appConst.ACTION.CREATE && message.data.message.chat.chatId == $scope.chat.chatId) {
            $scope.messages.push(message.data);
            $scope.update(message.data);
            $scope.$apply();
        }
        else if (message.action == appConst.ACTION.UPDATE && message.data.message.chat.chatId == $scope.chat.chatId) {
            for (var i = 0; i < $scope.messages.length; i++) {
                if ($scope.messages[i].message.messageId == message.id) {
                    $scope.messages[i] = message.data;
                    $scope.$apply();
                    return;
                }
            }
        }
    });

    $scope.handleScrollToTop = function () {
        GroupChatService.loadNextPage();
    };

});

