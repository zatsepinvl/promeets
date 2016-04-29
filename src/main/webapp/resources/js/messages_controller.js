app.controller('messagesCtrl', function ($scope, $state, appConst, UserEntity, UserChatsService, UserService, AppService) {
    $scope.chats = undefined;
    $scope.chats = UserChatsService.getChats();
    $scope.time = AppService.toTime;
    $scope.state = UserChatsService.getState();
    $scope.user = UserService.get();

    $scope.$on('usermessage', function (event, message) {
        if (message.action == appConst.ACTION.CREATE) {
            for (var i = 0; i < $scope.chats.length; i++) {
                if (message.data.message.chat.chatId = $scope.chats[i].chat.chatId) {
                    $scope.$apply(function () {
                        $scope.chats[i].lastUserMessage = message.data;
                        if (!message.data.sender) {
                            $scope.chats[i].newMessagesCount++;
                        }
                    });
                    return;
                }
            }
        }
        else if (message.action == appConst.ACTION.UPDATE) {
            for (var i = 0; i < $scope.chats.length; i++) {
                if (message.data.message.chat.chatId = $scope.chats[i].chat.chatId) {
                    if (message.data.sender && message.data.message.messageId == $scope.chats[i].lastUserMessage.message.messageId) {
                        $scope.chats[i].lastUserMessage = message.data;
                    }
                    else {
                        $scope.chats[i].newMessagesCount--;
                    }
                    $scope.$apply();
                    return;
                }
            }
        }
    });

    $scope.go = function (chat) {
        $state.transitionTo('user.group.chat', {groupId: chat.chat.group.groupId});
    }

});