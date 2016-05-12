app.controller("rtcController", function ($scope, UserEntity, UserService, MeetService, appConst, $http, $window) {
		
	//////////////////    WEB RTC CONFIG   //////////////////////////////////////

		var PeerConnection = window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
		var IceCandidate = window.mozRTCIceCandidate || window.RTCIceCandidate;
		var SessionDescription = window.mozRTCSessionDescription || window.RTCSessionDescription;
		navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
		
		
		var config = {
			iceServers : [
				{
					urls:['stun:stun01.sipphone.com','stun:stun.ekiga.net','stun:stun.fwdnet.net','stun:stun.l.google.com:19302',
					'stun:stun1.l.google.com:19302','stun:stun2.l.google.com:19302', 'stun:stun2.l.google.com:19302', 'stun:stun4.l.google.com:19302']
				},
				{
					urls:['turn:192.158.29.39:3478?transport=udp', 'turn:192.158.29.39:3478?transport=tcp'],
					username:'28224511:1379330808',
					credential:'JZEOEt2V3Qb0y27GRntt2u2PAYA='
				},
				{
					urls:['turn:numb.viagenie.ca'],
					username:'muazkh',
					credential:'webrtc@live.com'
				}
					
			] || { iceServers:[{url:'stun:stun01.sipphone.com'},
					{url:'stun:stun.ekiga.net'},
					{url:'stun:stun.fwdnet.net'},
					{url:'stun:stun.ideasip.com'},
					{url:'stun:stun.iptel.org'},
					{url:'stun:stun.rixtelecom.se'},
					{url:'stun:stun.schlund.de'},
					{url:'stun:stun.l.google.com:19302'},
					{url:'stun:stun1.l.google.com:19302'},
					{url:'stun:stun2.l.google.com:19302'},
					{url:'stun:stun3.l.google.com:19302'},
					{url:'stun:stun4.l.google.com:19302'},
					{url:'stun:stunserver.org'},
					{url:'stun:stun.softjoys.com'},
					{url:'stun:stun.voiparound.com'},
					{url:'stun:stun.voipbuster.com'},
					{url:'stun:stun.voipstunt.com'},
					{url:'stun:stun.voxgratia.org'},
					{url:'stun:stun.xten.com'},
					{
						url: 'turn:numb.viagenie.ca',
						credential: 'muazkh',
						username: 'webrtc@live.com'
					},
					{
						url: 'turn:192.158.29.39:3478?transport=udp',
						credential: 'JZEOEt2V3Qb0y27GRntt2u2PAYA=',
						username: '28224511:1379330808'
					},
					{
						url: 'turn:192.158.29.39:3478?transport=tcp',
						credential: 'JZEOEt2V3Qb0y27GRntt2u2PAYA=',
						username: '28224511:1379330808'
					}]}
		};

	//////////////////    SCOPE   //////////////////////////////////////
	
		var peerConnections = [];
		
		$scope.meet = MeetService.get();
		var meetId = $scope.meet.meetId;
		
		$scope.user = UserService.get();
		$scope.meetUsers = [];
		$scope.currentUserMeet = MeetService.getUserMeet();
		
		$scope.voiceEnable;
		$scope.connected;
		
		var localStream;
		
		
	//////////////////    HELPERS   //////////////////////////////////////
			
		function getPeerConnection(userId)
		{
			for (var i=0; i<peerConnections.length; i++)
			{
				if (peerConnections[i].duserId==userId)
					return peerConnections[i];
			}
			
			return null;
		}
		
		function resetPeerConnection(userId)
		{
			for (var i=0; i<peerConnections.length; i++)
			{
				if (peerConnections[i].duserId==userId)
				{
					peerConnections[i].close();
					peerConnections[i] = createPeerConnection(config, i);
					peerConnections[i].addStream(localStream);
					return peerConnections[i];
				}
			}
			
			return null;
		}
		
		function createPeerConnection (config, index)
		{
			pc = new PeerConnection(config);
			pc.duserId = $scope.meetUsers[index].user.userId;
			pc.pmId = index;
			
			pc.onicecandidate = gotIceCandidate;
			pc.ontrack = gotRemoteStream;
			pc.onremovestream = function(event) {
				console.log('stream removed'); 
				};
			pc.oniceconnectionstatechange = function(ev) {};
			
			return pc;
		}

		
		$scope.muteRemoteVideo = function (index)
		{
			var video = document.getElementById('remoteVideo-' + index);
			video.muted = !video.muted;
		}
		
		$scope.isMutedRemoteVideo = function (index) {
			return false;
			var video = document.getElementById('remoteVideo-' + index);
			return video.muted;
		}
			
		$scope.connect = function () {
			console.log("Connecting to Audio/Video");
			$scope.currentUserMeet.connected = true;
			UserEntity.update({entity: "meets", id: meetId}, $scope.currentUserMeet);
			createOffer();
		}
		
	//////////////////    RTC   //////////////////////////////////////
		
		var createPeerConnections = function () {
			console.log('CREATING PeerConnection');
			$http.get('/api/meets/'+meetId+'/info')
				.success(function (meetUsers) 
				{
					$scope.meetUsers=meetUsers;
					var i = 0;
					while (i<$scope.meetUsers.length)
					{
						var newPc = new createPeerConnection(config, i);
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
					  { audio: true, video: true}, 
					  gotStream, 
					  function(error) { console.log(error) }
					);
				});
        };

		function gotStream(stream) {
			
			localStream = stream;
			document.getElementById("localVideo").src = URL.createObjectURL(stream);
			
			var audioTracks = stream.getAudioTracks();
				  if (audioTracks.length > 0) {
					console.log('Using Audio device: ' + audioTracks[0].label);
				  }
			for (var i=0; i<peerConnections.length; i++)
			{
				peerConnections[i].addStream(stream);
			}
			
			$scope.$apply();
		}

		var createOffer = function() 
		{
			$scope.connected = true;

			console.log('Start creating offer');
			peerConnections.forEach(function(pc, i, arr)
			{
				if ($scope.meetUsers[pc.pmId].connected) {
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
			document.getElementById("remoteVideo-"+event.currentTarget.pmId).srcObject = event.streams[0];
			document.getElementById("remoteVideo-"+event.currentTarget.pmId).reload();
			$scope.$apply();
		}
		
	//////////////////    CONNECTION   //////////////////////////////////////
		
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
		
	////////////////////////////////////
	
			$scope.$on('meetinfo', function (event, message) 
			{
				if (message.action == appConst.ACTION.UPDATE) 
				{
					if (!message.data.online || !message.data.connected)
					{
						resetPeerConnection(message.data.user.userId);
					}
					if (message.data.user.userId == $scope.user.userId)
						return;
					for (var i = 0; i < $scope.meetUsers.length; i++)
					{
						if ($scope.meetUsers[i].user.userId === message.data.user.userId) {
							$scope.meetUsers[i] = message.data;
							$scope.$apply();
							return;
						}
					}
					
					$scope.meetUsers.push(message.data);
					$scope.$apply();
				}
				
			});
			
			$window.onbeforeunload = function () {
				$scope.userMeet.online = false;
				$scope.userMeet.connected = false;
				$http.put('/api/users/meets/'+$scope.userMeet.meet.meetId, $scope.userMeet)
					.success(function (data, status, headers, config) {
					})
			}	
			
			start();

    }
);
