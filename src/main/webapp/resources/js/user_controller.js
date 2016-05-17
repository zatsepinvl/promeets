app.controller('userCtrl', function (UserService, appConst, $scope) {
    
	var stompClient = Stomp.over(new SockJS(appConst.WS.URL));
	
	var startListen = function () {
        var user = UserService.get();
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
					else if (message.type)
					{
						$scope.$broadcast('rtc/'+message.meetId, message);
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
	$scope.$on('rtc', function (event, data) {
	  stompClient.send(appConst.WS.BROKER+"rtc/"+data.meetId, {}, data.message);
	});
	
    startListen();
});