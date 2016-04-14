(function(){
	var app = angular.module("myApp", []);
	
	//Header
	app.controller("ControllerGeneralHead", ['$scope','$http', function($scope, $http){
		
		//Nom de la page
		$scope.controller = {}
		$scope.controller.name = "Accueil"
	}]);
	
	//Body
	app.controller("ControllerGeneral", ['$scope','$http', function($scope, $http){
		
	}]);
	
})();