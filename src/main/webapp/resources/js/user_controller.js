app.controller('userCtrl', function (UserService, appConst, $scope) {
    var startListen = function () {
        var user = UserService.get();
        var stompClient = Stomp.over(new SockJS(appConst.WS.URL));
        stompClient.connect({},

            //on success -> subscribe on topic
            function () {
                stompClient.subscribe(appConst.WS.TOPIC + user.userId, function (data) {
                    var message = JSON.parse(data.body);
					if (message.entity)
					{
						$scope.$broadcast(message.entity, message);
					}
					// TODO:
					else (message.type)
					{
						$scope.$broadcast('rtc/'+message.meetId, JSON.parse(message.data));
					}
                    
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