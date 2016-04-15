app.controller('chatController', function ($scope, appConst, $http, GroupService, UserService, Entity, UserEntity, GroupChatService) {
    $scope.user = UserService.get();
    $scope.group = GroupService.get();
    $scope.messages = GroupChatService.get();
    $scope.chat = GroupChatService.getChat();
    $scope.status = GroupChatService.getStatus();
    $scope.glue=true;


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
        Entity.save({entity: "messages"}, message,
            function (value) {
                $scope.messages.push({message: value, viewed: true});
                $scope.glue=true;
            });
    };

    $scope.update = function (message) {
        message.viewed = true;
        UserEntity.update({entity: "messages", id: message.message.messageId}, message);
    };

    $scope.$on('message', function (event, data) {
        if (data.action == appConst.ACTION.CREATE) {
            UserEntity.get({entity: "messages", id: data.id},
                function (message) {
                    $scope.messages.push(message);
                    $scope.glue=true;
                });
        }
    });

    var today = moment();
    $scope.time = function (time) {
        time = moment(time).local();
        return time.isSame(today, 'day') ? time.format('HH:mm') : time.format('MM-DD-YY');
    };

    $scope.onLoad = function () {
        $scope.scroll();
    };

});
