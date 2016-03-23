
app.controller('chatController', function($scope, $q, $rootScope, $http, $mdDialog) 
{ 
		$scope.messages = [];
		$scope.currentUser = {};
		$scope.messageText = "";
		$scope.chat = {};
			
		RECONNECT_TIMEOUT = 2*1000;
		SOCKET_URL = "/ws";
		CHAT_TOPIC = "/topic/chat/";
		CHAT_BROKER = "/app/chat/";
		
		var socket;
		var stompClient;
		
		var deferred = $q.defer();
		
		$scope.$on('user', function (event, user) 
		{
			if (user) 
			{
				$scope.currentUser = user;
			}
			else 
			{
				
			}
		});
		
		$scope.sendMessage = function ()
		{
			var jsonstr = JSON.stringify({'text': $scope.messageText, 'user' : $scope.currentUser });
			stompClient.send(CHAT_BROKER+ $scope.chat.chatId +"/send", {}, jsonstr);
		}
		
		$scope.showUsers = function () {
			$mdDialog.show({
					controller: SignUpController,
					templateUrl: '../static/chat/chat_dialog.html',
					parent: angular.element(document.body)
				})
				.then(function () {

				})
				.then(function (user) {

				});
		}
		
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
			deferred.notify(getMessages(data));
			});
			stompClient.send(CHAT_BROKER+ $scope.chat.chatId +"/get", {});
		};
		
		var receive = function() 
		{
		  return deferred.promise;
		};
		
		function getMessages(frame) 
		{
          var messages = JSON.parse(frame.body);
		  $scope.messages = messages;
        };
			
		receive().then(null, null, function(message) 
		{
		  $scope.messages.push(message);
		});
		
		$http.get('/api/chats/1').success(function (chat) 
		{
			$scope.chat = chat;
			$scope.load = true;
			
			startListen();
		});
		
			
});


function ChatShowUsersController($scope, $state, $mdDialog) {
    $scope.cancel = function () {
        $mdDialog.cancel();
    };
}