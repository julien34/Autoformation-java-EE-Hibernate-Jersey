///////////////////////////////
//Controleur de la connexion//
/////////////////////////////

(function(){
	var app = angular.module('connexion-controller', []);

	app.controller("ControleurConnexionHead", ['$scope', function($scope){
		$scope.controller = {};
		$scope.controller.name = "Connexion";
	}]);
	
	app.controller("ControleurConnexion", ['$rootScope', '$scope','$http','$window', function($rootScope, $scope, $http, $window){

		//On affiche la vue de connexion
		$scope.vueConnexion = true;

		//Méthode qui envoie les données de connexion
		$scope.connexion = function(){
			
			$scope.vueConnexion = false;//On masque le formulaire
			$scope.chargement = true;//On affiche le chargement
			$scope.erreur = false;

			//On récupère les données du champs
			var user = {
					email : $scope.email,
					password : sha512($scope.password)
			};

			//On créer notre requete
			var requete = {
					method : 'POST',
					headers : {'Content-Type' : 'application/json;charset=UTF-8'},
					data : JSON.stringify(user),
					url : '/RestProject/Rest/userService/connexion'
			};

			//On exécute la requête
			$http(requete).then(

				//Succès
				function(succes){

					//On récupère l'user dans un tableau
					$rootScope.user = {};
					$rootScope.user = succes.data;

					//On redirige l'user
					$window.location.href = 'mon-compte.html';       		
				},

				//Erreur
				function(erreur){
					$scope.erreur = true;//On affiche une erreur
					$scope.chargement = false;//On enlève le chargement
					$scope.vueConnexion = true;//On re-affiche le formulaire
				}
			);
		};
	}]);

})();