app.controller("rtcController", function ($scope, Entity, $state, UserService, MeetService, appConst, EventHandler, $http) {
        $scope.meets = [];
		
		var PeerConnection = window.mozRTCPeerConnection || window.webkitRTCPeerConnection;
		var IceCandidate = window.mozRTCIceCandidate || window.RTCIceCandidate;
		var SessionDescription = window.mozRTCSessionDescription || window.RTCSessionDescription;
		navigator.getUserMedia = navigator.getUserMedia || navigator.mozGetUserMedia || navigator.webkitGetUserMedia;

		var pc; 
		var isOffer = false;
		
		var meetId = 180;
		
		$http.get('/api/users/meets/'+meetId)
            .success(function (meets) {
                $scope.meets=meets;
            });

		navigator.getUserMedia(
		  { audio: true, video: true }, 
		  gotStream, 
		  function(error) { console.log(error) }
		);

		function gotStream(stream) {
		  document.getElementById("callButton").style.display = 'inline-block';
		  document.getElementById("localVideo").src = URL.createObjectURL(stream);
		  pc = new PeerConnection(null);
		  pc.addStream(stream);
		  pc.onicecandidate = gotIceCandidate;
		  pc.onaddstream = gotRemoteStream;
		}

		$scope.createOffer = function() {
			
		  isOffer=true;	
		  pc.createOffer(
			gotLocalDescription, 
			function(error) { console.log(error) }, 
			{'mandatory': { 'OfferToReceiveAudio': true, 'OfferToReceiveVideo': true }}
		  );
		}

		function createAnswer() {		
		  pc.createAnswer(
			gotLocalDescription,
			function(error) { console.log(error) }, 
			{ 'mandatory': { 'OfferToReceiveAudio': true, 'OfferToReceiveVideo': true } }
		  );
		}

		function gotLocalDescription(description){
		  pc.setLocalDescription(description);
		  sendMessage(description);
		}

		function gotIceCandidate(event){
		  if (event.candidate) {
			sendMessage(
			{
			  type: 'candidate',
			  label: event.candidate.sdpMLineIndex,
			  id: event.candidate.sdpMid,
			  candidate: event.candidate.candidate
			}
			);
		  }
		}

		function gotRemoteStream(event){
		  document.getElementById("remoteVideo").src = URL.createObjectURL(event.stream);
		}
		
		// Stomp
		
		
		function sendMessage(data)
		{
			var message = {};
			message.data = JSON.stringify(data);
			message.type = data.type;
			message.meetId = meetId;
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
	

		$scope.$on('rtc/'+meetId, function (event, message) {

			if (message.type === 'offer' && !isOffer) {
				pc.setRemoteDescription(new SessionDescription(message));
				createAnswer()
			} 
			else if (message.type === 'answer') {
				pc.setRemoteDescription(new SessionDescription(message));
			} 
			else if (message.type === 'candidate' && !isOffer) {
				var candidate = new IceCandidate({sdpMLineIndex: message.label, candidate: message.candidate});
				pc.addIceCandidate(candidate);
			}
			});

			start();

    }
);