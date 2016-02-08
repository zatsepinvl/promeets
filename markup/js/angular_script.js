
	var meetmodel = {
	group_id: 0, 
	meet_id: 0,
	meet_name: "default_name",
	meet_descr: "default_descr",
	meet_type: "remote",
	meet_date: "06/02/2016",
	meet_time: "18:00",
	meet_address: "default_address",	
	meet_goals: 
	[],
	meet_comments:"default_comments"
	};
	var creatingNewMeet = angular.module("creatingNewMeet",[])
	.controller("creatingNewMeetCtrl", function($scope){
		$scope.newMeet = meetmodel;
		$scope.addNewMeet = function(){			
			$scope.newMeet.meet_name = $scope.txtName;
			$scope.newMeet.meet_descr = $scope.txtDescr;
			$scope.newMeet.meet_type = $scope.selType;
			$scope.newMeet.meet_date = $scope.txtDate;
			$scope.newMeet.meet_time = $scope.txtTime;
			$scope.newMeet.meet_address = $scope.txtAddress;
			$scope.newMeet.meet_сomments = $scope.txtComm;
			var elem = angular.element(document.querySelector("#meet_goal"));
			var goals = elem.children().length;
			for(var i = 0; i < elem.children().length - 1; i++){
				var totalText = elem.children().eq(i).text();
				var startIndex = totalText.indexOf('grade');
				var endIndex = totalText.indexOf('clear');
				var goalText = totalText.substr(startIndex + 5,endIndex - startIndex - 5);
				alert(goalText);
				$scope.newMeet.meet_goals.push({
					goal: goalText
				})
			}
		}
  });	