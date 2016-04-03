app.controller('chatController', function($scope, $q, $rootScope, $http, $mdDialog, UserService, Entity) 
{ 
		$scope.messages;
		$scope.currentUser = {};
		$scope.messageText = "";
		$scope.chat = {};
		$scope.isLoad;
			
		RECONNECT_TIMEOUT = 1*1000;
		SOCKET_URL = "/ws";
		CHAT_TOPIC = "/topic/chat/";
		CHAT_BROKER = "/app/chat/";
		
		
		var id = window.location.pathname.split("/")[2];
		var socket;
		var stompClient;
		
		var deferred = $q.defer();
		
		
		UserService.load(function (user) 
		{
			if (user.email) 
			{
				$scope.currentUser = user;
				
				$http.get('/api/groups/'+id).success(function (group) 
				{
					id = 1;
					
					$scope.chat = Entity.get({entity: "chats", id: id}, function () 
					{
						$scope.users = Entity.query({entity: "chats", id: id, d_entity: "users"},
						function (users) 
							{
								$scope.chat.users = users;
								$scope.load = true;
								if (chatIsContaisUser($scope.currentUser))
								{
									startListen();
								}
								else 
								{
									alert("You are not a member of this chat");
								}
							});
					});
				});
			}
			else 
			{
				alert("Not autorize");
			}
		});
		
		$scope.sendMessage = function ()
		{
            var jsonstr = JSON.stringify({'text': $scope.messageText, 'user' : $scope.currentUser, 'chat': $scope.chat });
            stompClient.send(CHAT_BROKER+ $scope.chat.chatId +"/send", {}, jsonstr);
			$scope.messageText = "";
		};
		
		$scope.showUsers = function () {
                    $mdDialog.show({
                            controller: ChatShowUsersController,
                            templateUrl: '../static/chat/chat_dialog.html',
                            parent: angular.element(document.body)
			}).then(function () {}).then(function (user) {});
		};
		
		var startListen = function() 
		{
			socket = new SockJS(SOCKET_URL);
			stompClient = Stomp.over(socket);
			stompClient.connect("guest", "guest", subscribe, stompErrorCallBack);
			stompClient.onclose = reconnect;
		};
		
		var stompErrorCallBack = function (error)
		{
			console.log('STOMP: ' + error);
			console.log('STOMP: Reconecting in '+RECONNECT_TIMEOUT/1000 +' seconds');
			reconnect();
		};
		
		
		var reconnect = function() 
		{
		  setTimeout(startListen, RECONNECT_TIMEOUT);
		};
		
		var subscribe = function()
		{
			stompClient.subscribe(CHAT_TOPIC + $scope.chat.chatId, function(data) {
			deferred.notify(getAllMessagesFromServer(data));
			});
			stompClient.send(CHAT_BROKER+ $scope.chat.chatId +"/get", {});
			$scope.isLoad = true;
		};
		
		var receive = function() 
		{
		  return deferred.promise;
		};
		
		var chatIsContaisUser = function(user)
		{
			for (i = 0; i < $scope.chat.users.length; i++) 
			{
				if ($scope.chat.users[i].email === user.email) 
				{
					return true;
				}
			}
			
			return false;
		}
		
		function getAllMessagesFromServer(frame) 
		{
                    var messages = JSON.parse(frame.body);
                    if (Array.isArray(messages))
                    {
                        $scope.messages = messages;
                    }
                    else
                    {
                        $scope.messages.push(messages);
                    }    
                };
			
		receive().then(null, null, function(message) 
		{
		  $scope.messages.push(message);
		});		
			
});


function ChatShowUsersController($scope, $state, $mdDialog) {
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
}