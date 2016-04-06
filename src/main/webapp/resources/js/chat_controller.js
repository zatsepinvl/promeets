app.controller('chatController', function($scope, $q, $rootScope, $http, $mdDialog, UserService, Entity, $window) 
{ 
		$scope.messages;
		$scope.currentUser = {};
		$scope.messageText = "";
		$scope.chat = {};
		$scope.isLoad;
		$scope.glued = true;
		$scope.isAllChat = false;
			

		PAGE_LENGHT=20;
		
		var groupId = window.location.pathname.split("/")[2];
		var id;
		var socket;
		var stompClient;
		var messagePageCount = 0;
		
		var deferred = $q.defer();
		
		
		UserService.load(function (user) 
		{
			if (user.email) 
			{
				$scope.currentUser = user;
				
				$http.get('/api/groups/'+groupId).success(function (group) 
				{
					id = group.groupId;
					
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
				$scope.glued = !$scope.glued;
			}
			else 
			{
				alert("Not autorize");
			}
		});
		
		$scope.getMoreMessages = function ()
		{
			messagePageCount+=1;
			$http.get('/api/chats/'+$scope.chat.chatId+'/messages/'+messagePageCount).success(function (newMessages) 
			{
				$scope.messages.push.apply($scope.messages, newMessages);
				if (newMessages.length < PAGE_LENGHT)
					$scope.isAllChat = true;
			});
		}
		
		$scope.sendMessage = function ()
		{
            var message = {};
			message.text = $scope.messageText;
			message.user = $scope.currentUser;
			
			$http.post('/api/chats/'+id +'/messages', message)
            .success(function (data, status, headers, config) 
			{
				$scope.messageText="";
            })
            .error(function (data, status, header, config) {});
		};
		
		$scope.showUsers = function () 
		{
			
                    $mdDialog.show({
                            controller: ChatShowUsersController,
                            templateUrl: '../static/chat/chat_dialog.html',
                            parent: angular.element(document.body)
					})
					.then(function () {})
					.then(function (user) {});
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
		
		// STOMP
		RECONNECT_TIMEOUT = 1*1000;
		SOCKET_URL = "/ws";
		SOCKET_TOPIC = "/topic/";
		SOCKET_BROKER = "/app/";
		
		var startListen = function() 
		{
			socket = new SockJS(SOCKET_URL);
			stompClient = Stomp.over(socket);
			stompClient.connect($scope.currentUser.email, $scope.currentUser.email, subscribeToTopic, stompErrorCallBack);
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
		
		var subscribeToTopic = function()
		{
			stompClient.subscribe(SOCKET_TOPIC + $scope.currentUser.userId, function(data) {
			deferred.notify(listenAppSocket(data));
			});
			stompClient.send(SOCKET_BROKER+ $scope.currentUser.userId +"/init", {});
			$scope.isLoad = true;
		};
		
		function listenAppSocket(frame) 
		{
            var socketMessage = JSON.parse(frame.body);
            if (socketMessage.status=="ready")
			{
				$http.get('/api/chats/'+$scope.chat.chatId+'/messages/'+messagePageCount).success(function (messages) 
				{
					$scope.messages = messages;
				});
			}
			if (socketMessage.action=="add_chat_message")
			{
				$http.get('/api/messages/'+message.body).success(function (message) 
				{
					$scope.messages.push(message);
				});
			}
        };    
});


function ChatShowUsersController($scope, $state, $mdDialog) {
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
}