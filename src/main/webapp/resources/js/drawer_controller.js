/**
 * Created by Vladimir on 15.03.2016.
 */
app.controller('drawerCtrl', function ($scope, $state, $rootScope, $http, EventHandler, UserService, UserMeetService, UserMessageService, $state, appConst) {
    $scope.user = UserService.get();
    $scope.logout = function () {
        $http.post('/logout')
            .success(function () {
                UserService.logout();
                $state.transitionTo('home.presentation');
            })
            .error(function (error) {
                console.log(error);
            });
    };

    $scope.go = function (state, params) {
        $state.transitionTo(state, params);
    };

    $scope.newMeets = UserMeetService.getNewMeets();
    $scope.newMessages = UserMessageService.getNewMessages();

    $scope.$on('meet', function (event, data) {
        if (data.action == appConst.ACTION.CREATE) {
            $scope.newMeets.push(data.id);
            $scope.$apply();
        }
    });

    $scope.$on('usermessage', function (event, data) {
        console.log(data.entity + ':DRAWER CONTROLLER:FROM SOCKET:' + data.action);
        onMessageReceive(data);

        $scope.$apply();
    });

    $rootScope.$on('usermessageLocal', function (event, data) {
        console.log(data.entity + ':DRAWER CONTROLLER:FROM ROOT SCOPE:' + data.action);
        onMessageReceive(data);
    });
	
	$scope.$on('usermeetinfo', function (event, data) 
	{
		console.log(data.entity + ':DRAWER CONTROLLER:FROM ROOT SCOPE:' + data.action);
        onMeetOnline(data);	
	});

    var onMessageReceive = function (data) {
        if (data.action == appConst.ACTION.CREATE) {
            $scope.newMessages.push(data.id);
            var sender = data.data.message.user;
            EventHandler.message('New message by ' + sender.firstName + ' ' + sender.lastName, sender.image.url);
        }
        else if (data.action == appConst.ACTION.UPDATE) {
            $scope.newMessages.splice($scope.newMessages.indexOf(data.id), 1);
        }
    }
	
	var onMeetOnline = function (data) {
		var sender = data.data.user;
		if (data.data.user.userId == $scope.user.userId)
			return;
		if (data.action == appConst.ACTION.UPDATE && data.data.online && data.data.connected) 
		{
			EventHandler.message(sender.firstName + ' ' + sender.lastName + ' join to chat', sender.image.url);
		}
		else if (data.action == appConst.ACTION.UPDATE && data.data.online && !data.data.connected) 
		{
			EventHandler.message(sender.firstName + ' ' + sender.lastName + ' join', sender.image.url);
		}
		else if (data.action == appConst.ACTION.UPDATE && !data.data.online) 
		{
			EventHandler.message(sender.firstName + ' ' + sender.lastName + ' leave meet', sender.image.url);
		}
	}
	
	
});