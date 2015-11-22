
var app = angular.module('whamApp', []);

app.controller('currentUser', function ($scope) {
	
	$scope.loggedInUser = "Howdy user!";
	$scope.loginShow = true;
	
	var users = [{
        username: "Roop",
        password: "Roop"
    },
        {
            username: "Sandeep",
            password: "Sandeep"
        },
    {
        username: "Karthik",
        password: "Karthik"
    },
    {
        username: "Joy",
        password: "Joy"
    }
    ];
	
	$scope.login = function () {
		
		var validUser = false;
		var uname = $scope.username;
		var pass = $scope.password;
		
		for (var u in users) {
				if(users[u].username === uname && users[u].password === pass)
					{
						$scope.loggedInUser = users[u].username;
						$scope.loginShow = false;
						validUser = true;
					}
            }
		
		if(validUser === false)
			{
				$scope.username = "";
				$scope.password = "";
				$scope.loggedInUser = "Please login again"; 
			}
		
	}
	
	

});
