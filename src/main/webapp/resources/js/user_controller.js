app.controller('userCtrl', function (UserService, appConst, $scope) {
    var startListen = function () {
        var user = UserService.get();
        var stompClient = Stomp.over(new SockJS(appConst.WS.URL));
        stompClient.connect({},

            //on success -> subscribe on topic
            function () {
                stompClient.subscribe(appConst.WS.TOPIC + user.userId, function (data) {
                    var message = JSON.parse(data.body);
                    $scope.$broadcast(message.entity, message);
                });
            },

            //on error -> reconnect
            function (error) {
                setTimeout(startListen, appConst.WS.RECONNECT_TIMEOUT);
            });

        stompClient.onClose = function () {
        }
    };
    startListen();
});