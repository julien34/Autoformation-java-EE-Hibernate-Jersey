//////////////////////////
//Controleur mon compte//
////////////////////////

(function(){
	var app = angular.module('mon-compte-controller', []);

	app.controller("ControleurMonCompte", ['$scope','$http','$window', function($scope, $http, $window){

		$scope.user = {};
		
		//Méthode qui récupère l'user s'il est présent dans la variable de session coté serveur
		$scope.getUser = function(){

			//On créer notre requete qui récupère l'user connecté
			var requete = {
					method : 'POST',
					headers : {'Content-Type' : 'application/json;charset=UTF-8'},
					url : '/RestProject/Rest/userService/getUser'
			};

			//On exécute la requête
			$http(requete).then(

					//Succès de la requete
					function(succes){

						//Data reçu vide
						if(succes.data==null){
							console.log("Erreur dans la récupération de l'user connecté. Vérifiez qu'il soit connecté.");
							$window.location.href = 'connexion.html';
						}
						else{
							$scope.user = succes.data;
						}    		
					},

					//Erreur
					function(erreur){
						console.log("Erreur dans la requête.");
					}
			);

		};
		
		
		//Méthode qui met à jour les informations de l'utilisateur
		$scope.majUser = function(){
			
			//On modifie le mot de passe à envoyer en SHA512
			$scope.user.password = sha512($scope.password);
			
			//On créer notre requete qui envoie l'user modifié
			var requete = {
					method : 'POST',
					headers : {'Content-Type' : 'application/json;charset=UTF-8'},
					data : $scope.user,
					url : '/RestProject/Rest/userService/majUser'
			};
			
			//On exécute la requête
			$http(requete).then(

					//Succès de la requete
					function(succes){
						console.log("Compte modifié avec succès.")
					},

					//Erreur
					function(erreur){
						console.log("Erreur de modification du compte.");
					}
			);
		};

	}]);
})();