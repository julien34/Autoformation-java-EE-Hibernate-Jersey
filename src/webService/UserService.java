package webService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.UserDao;
import dto.UserDto;


@Path("/userService")
public class UserService {
	
	/**
	 * Méthode qui créer un nouvel utilisateur si ce dernier n'existe pas déjà sur la BDD.
	 * @param usr, une String au format JSON qui sera parsée pour récupèrer les données.
	 */
	@POST
	@Path("/newUser")
	@Consumes(MediaType.APPLICATION_JSON)//Accepte en entrée
	@Produces(MediaType.APPLICATION_JSON)//Retourne au client
	public void newUser(String usr){
		
		//On parse l'objet reçu et récupère les champs
		JsonObject obj = new JsonParser().parse(usr).getAsJsonObject();
		
		String prenom = obj.get("prenom").getAsString();
		String nom = obj.get("nom").getAsString();
		String email = obj.get("email").getAsString();
		String password = obj.get("password").getAsString();
		
		UserDto e = new UserDto(prenom, nom, email, password);

		UserDao.newUser(e);
	}
	
	
	/**
	 * @param req, la requete envoyé au server.
	 * @param usr, l'utilisateur sous forme de JSON (mot de passe et email).
	 * @return, l'user au format JSON si le couple mdp/login est correct.
	 */
	@POST
	@Path("/connexion")
	public Response connexionUser(@Context HttpServletRequest req,String usr){
		
		//On parse l'objet reçu et récupère les champs
		JsonObject obj = new JsonParser().parse(usr).getAsJsonObject();
		
		String email = obj.get("email").getAsString();
		String password = obj.get("password").getAsString();
		
		
		//On cherche si la combinaise user/pass est correcte. La m�thdoe renvoie l'user si oui, null si non
		UserDto userTrouve = UserDao.connexion(email, password);
		
		//On teste avec la méthode de connexion si la combinaison est bonne
		if(userTrouve!=null){
			
			//On créer une session si elle n'est pas déjà crée
			HttpSession sess = req.getSession(true);
			
			//On attribut une variable de session ID avec l'id de l'user récupéré
			sess.setAttribute("user", userTrouve);
		}
		
		//mettre en JSON le user
		Gson gson = new Gson();
		String rep = new String(gson.toJson(userTrouve));
		
		//On retourne la réponse avec le user
		return Response.ok().entity(rep).build();
	}
	
	
	/**
	 * @param req, la requete envoyée au serveur.
	 * @return une Response avec dans le corps, un JSON de l'user.
	 */
	@POST
	@Path("/getUser")
	public Response getUser(@Context HttpServletRequest req){
		
		//On récupère les données de la session
		UserDto userConnecte = (UserDto) req.getSession().getAttribute("user");
		
		//On défini le user en JSON pour renvoyer la réponse au client
		Gson gson = new Gson();
		String rep = new String(gson.toJson(userConnecte));
		return Response.ok().entity(rep).build();
	}
	
	
	/**
	 * Méthode qui met à jour les données de l'utilisateur envoyé.
	 * @param req, la requete envoyée au serveur.
	 * @param usr, un User au format JSON envoyé.
	 */
	@POST
	@Path("/majUser")
	public void majUser(@Context HttpServletRequest req, String usr){
		
		//On parse l'objet reçu et récupère les champs authorisés (modifiables)
		JsonObject obj = new JsonParser().parse(usr).getAsJsonObject();

		String password = obj.get("password").getAsString();
		String prenom = obj.get("prenom").getAsString();
		String nom = obj.get("nom").getAsString();

		//On créer un nouvel utilisateur avec les données à changer
		UserDto usrNew = new UserDto();
		usrNew.setNom(nom);
		usrNew.setPrenom(prenom);
		usrNew.setPassword(password);
		
		//Je récupère ma session mais je n'en créer pas une
		HttpSession sess = req.getSession();
		
		//On récupère l'utilisateur en session
		UserDto usrEnSession = (UserDto) sess.getAttribute("user");
		
		//On effectue la mise à jour sur la base de données avec le nouvel utilisateur
		UserDao.majUser(usrEnSession,usrNew);
	}
}
