app.controller("rtcController", function ($scope, Entity, $state, UserService, MeetService, appConst, EventHandler, $http) {
        $scope.userMeets = [];
		$scope.user = UserService.get();
		
		var PeerConnection = window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
		var IceCandidate = window.mozRTCIceCandidate || window.RTCIceCandidate;
		var SessionDescription = window.mozRTCSessionDescription || window.RTCSessionDescription;
		navigator.getUserMedia = navigator.getUserMedia ||  navigator.mozGetUserMedia || navigator.webkitGetUserMedia;

		var peerConnections = []; 
		var meetId = 188;
		
		
		$http.get('/api/users/meets/'+meetId+'/all')
            .success(function (userMeets) 
			{
                $scope.userMeets=userMeets;
				var i = 0;
				while (i<$scope.userMeets.length)
				{
					var newPc = new PeerConnection(null);
					newPc.duserId = $scope.userMeets[i].user.userId;
					newPc.pmId = i;
					if (newPc.duserId!=$scope.user.userId)
					{
						peerConnections.push(newPc);
						i++;
					}
					else 
					{
						userMeets.splice (i, 1);
					}
				}
				
				navigator.getUserMedia(
				  { audio: true, video: true }, 
				  gotStream, 
				  function(error) { console.log(error) }
				);
            });

////    HELPERS   //////////////////////////////////////
			
		function getPeerConnection(userId)
		{
			for (var i=0; i<peerConnections.length; i++)
			{
				if (peerConnections[i].duserId==userId)
					return peerConnections[i];
			}
			
			return null;
		}

////    RTC   //////////////////////////////////////

		function gotStream(stream) {
			document.getElementById("callButton").style.display = 'inline-block';
			document.getElementById("localVideo").src = URL.createObjectURL(stream);
			for (var i=0; i<peerConnections.length; i++)
			{
				peerConnections[i].addStream(stream);
				peerConnections[i].onicecandidate = gotIceCandidate;
				peerConnections[i].onaddstream = gotRemoteStream;
				peerConnections[i].onremovestream = function(event) {
					console.log('stream removed'); 
					};
				peerConnections[i].oniceconnectionstatechange = function(ev) {
				};
			}
		}

		$scope.createOffer = function(duserId) 
		{
			peerConnections.forEach(function(pc, i, arr)
			{
				pc.createOffer(
					gotLocalDescriptions, 
					function(error) { console.log(error) }, 
					{ 'OfferToReceiveAudio': true, 'OfferToReceiveVideo': true }
				);
				function gotLocalDescriptions(description)
				{
				  pc.setLocalDescription(description);
				  sendMessage(description,pc.duserId);
				}
			});
		}

		function createAnswer(userId) 
		{
			currentPc = getPeerConnection(userId);
			currDuserId=userId;
			currentPc.createAnswer(
				function(answer) 
				{
					gotLocalDescription(answer, userId); 
				},
				function(error) { console.log(error) }, 
				{ 'mandatory': { 'OfferToReceiveAudio': true, 'OfferToReceiveVideo': true } }
		  );
		}

		function gotLocalDescription(description, userId)
		{
		  currentPc.setLocalDescription(description);
		  sendMessage(description,userId);
		}

		function gotIceCandidate(event){
		  if (event.candidate) {
			sendMessage(
			{
			  type: 'candidate',
			  label: event.candidate.sdpMLineIndex,
			  id: event.candidate.sdpMid,
			  candidate: event.candidate.candidate
			}, event.currentTarget.duserId
			);
		  }
		}

		function gotRemoteStream(event)
		{
			$scope.userMeets[event.currentTarget.pmId].online = true;
			document.getElementById("remoteVideo-"+event.currentTarget.pmId).src = URL.createObjectURL(event.stream);
			$scope.$apply();
		}
		
		////    CONNECTION   //////////////////////////////////////
		
		
		function sendMessage(data, duserId)
		{
			var message = {};
			message.data = JSON.stringify(data);
			message.type = data.type;
			message.duserId = duserId;
			message.meetId = meetId
			stompClient.send(appConst.WS.BROKER+"rtc/"+meetId, {}, JSON.stringify(message));
		}

		var start = function() 
		{
			stompClient = Stomp.over(new SockJS(appConst.WS.URL));
			stompClient.connect("guest", "guest", {}, stompErrorCallBack);
			stompClient.onclose = reconnect;
		};
		
		var stompErrorCallBack = function (error)
		{
			console.log('STOMP: ' + error);
			console.log('STOMP: Reconecting in '+appConst.RECONNECT_TIMEOUT/1000 +' seconds');
			reconnect();
		};
		
		var reconnect = function() 
		{
		  setTimeout(start, appConst.WS.RECONNECT_TIMEOUT);
		};
	

		$scope.$on('rtc/'+meetId, function (event, data) 
		{
			message = JSON.parse(data.data);
			var pc;
			if (message.type === 'offer') 
			{
				pc = getPeerConnection(data.suserId);
				pc.setRemoteDescription(new SessionDescription(message));
				createAnswer(data.suserId)
			} 
			else if (message.type === 'answer') 
			{
				pc = getPeerConnection(data.suserId);
				
				pc.setRemoteDescription(new SessionDescription(message));
			} 
			else if (message.type === 'candidate') 
			{
				pc = getPeerConnection(data.suserId);
				
				var candidate = new IceCandidate({sdpMLineIndex: message.label, candidate: message.candidate});
				pc.addIceCandidate(candidate);
			}
		});

			start();

    }
);