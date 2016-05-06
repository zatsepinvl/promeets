app.controller("rtcController", function ($scope, Entity, $state, UserService, MeetService, appConst, EventHandler, $http) {
		$scope.user = UserService.get();
		
		var PeerConnection = window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
		var IceCandidate = window.mozRTCIceCandidate || window.RTCIceCandidate;
		var SessionDescription = window.mozRTCSessionDescription || window.RTCSessionDescription;
		navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;

		var peerConnections = [];
		$scope.meet = MeetService.get();
		var meetId = $scope.meet.meetId;
		
		$scope.meetUsers = [];
		$scope.currentUserMeet = MeetService.getUserMeet();
		
		var createPeerConnections = function () 
			{
				console.log('CREATING PeerConnection');
				$http.get('/api/meets/'+meetId+'/info')
					.success(function (meetUsers) 
					{
						$scope.meetUsers=meetUsers;
						var i = 0;
						while (i<$scope.meetUsers.length)
						{
							var newPc = new PeerConnection(null);
							newPc.duserId = $scope.meetUsers[i].user.userId;
							newPc.pmId = i;
							if (newPc.duserId!=$scope.user.userId)
							{
								peerConnections.push(newPc);
								i++;
							}
							else 
							{
								meetUsers.splice (i, 1);
							}
						}
						
						navigator.getUserMedia(
						  { audio: true, video: false}, 
						  gotStream, 
						  function(error) { console.log(error) }
						);
					});
            };

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
			
			var audioTracks = stream.getAudioTracks();
				  if (audioTracks.length > 0) {
					console.log('Using Audio device: ' + audioTracks[0].label);
				  }
			for (var i=0; i<peerConnections.length; i++)
			{
				peerConnections[i].addStream(stream);
				peerConnections[i].onicecandidate = gotIceCandidate;
				peerConnections[i].ontrack = gotRemoteStream;
				peerConnections[i].onremovestream = function(event) {
					console.log('stream removed'); 
					};
				peerConnections[i].oniceconnectionstatechange = function(ev) {
				};
			}
			
			$scope.createOffer();
		}

		$scope.createOffer = function() 
		{
			console.log('Start creating offer');
			peerConnections.forEach(function(pc, i, arr)
			{
				if ($scope.meetUsers[pc.pmId].online) {
					pc.createOffer(
						gotLocalDescriptions, 
						function(error) { console.log(error) }, 
						{ 'OfferToReceiveAudio': true, 'OfferToReceiveVideo': false }
					);
					function gotLocalDescriptions(description)
					{
					  pc.setLocalDescription(description);
					  sendMessage(description,pc.duserId);
					}
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
				{ 'mandatory': { 'OfferToReceiveAudio': true, 'OfferToReceiveVideo': false } }
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
			document.getElementById("remoteAudio-"+event.currentTarget.pmId).srcObject = event.streams[0];
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
			stompClient.connect("guest", "guest", createPeerConnections, stompErrorCallBack);
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