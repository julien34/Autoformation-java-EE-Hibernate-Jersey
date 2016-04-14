////////////////////////////////
//Controleur de l'inscription//
//////////////////////////////

(function(){
	var app = angular.module('inscription-controller', []);
	
	//Header
	app.controller("ControllerInscriptionHead", ['$scope', function($scope){
		//On définit les caractéristiques du controller
		$scope.controller = {};
		$scope.controller.name = "Inscription";
	}]);

	//Body
	app.controller("ControleurInscription", ['$scope','$http', function($scope, $http){

		$scope.vueInscriptionValid = false;//Message de validation en cas de succès d'inscription
		$scope.erreurInscription = false;//Message d'erreur en cas d'échec
		$scope.vueInscription = true;//Vue avec les champs d'inscription

		//Méthode qui envoie les éléments sur le webservice d'enregistrement users
		$scope.validerInscription = function(){

			//On charge l'animation de chargement
			$scope.chargement = true;
			$scope.vueInscription = false;
			$scope.erreurInscription = false;
			$scope.erreurMDP = false;
			
			//On récupère les données saisies par l'utilisateur
			var user = {
					email : $scope.email,
					prenom : $scope.prenom,
					nom : $scope.nom,
					password : sha512($scope.password)
			};

			//On créer notre requete
			var requete = {
					method : 'POST',
					headers : {'Content-Type' : 'application/json;charset=UTF-8'},
					data : JSON.stringify(user),
					url : '/RestProject/Rest/userService/newUser'
			};

			//Si les deux mots de passe correspondent
			if($scope.password == $scope.password2){
				
				//On exécute la requête
				$http(requete).then(

						//Succès
						function(succes){
							$scope.vueInscriptionValid = true;//On affiche un message de confirmation
							$scope.chargement = false;//On arrete l'animation du chargement
						},

						//Erreur
						function(erreur){
							$scope.chargement = false;
							$scope.erreurInscription = true;//On affiche le message d'erreur
							$scope.vueInscription = true;//On re-affiche la vue d'inscription
						}
				);
			}
			
			//Si non dans le cas ou les mots de passes ne correspondent pas
			else {
				$scope.vueInscription = true;
				$scope.erreurMDP = true;
				$scope.chargement = false;
			}
		};

	}]);

})();