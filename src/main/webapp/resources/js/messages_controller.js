app.controller('messagesCtrl', function ($scope, $state, appConst, UserEntity, UserChatsService, AppService) {
    $scope.chats = UserChatsService.getChats();
    $scope.time = AppService.toTime;
    $scope.state = UserChatsService.getState();
    
    $scope.$on('message', function (event, data) {
        if (data.action == appConst.ACTION.CREATE) {
            UserEntity.get({entity: "messages", id: data.id},
                function (message) {
                    $scope.chats.forEach(function (chat) {
                        if (chat.chat.chatId = message.message.chat.chatId) {
                            chat.messages[0] = message;
                            chat.newMessages++;
                            return;
                        }
                    });
                });
        }
    });

    $scope.go = function (chat) {
        $state.transitionTo('user.group.chat', {groupId: chat.group.groupId});
    }
});