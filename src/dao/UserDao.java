package dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import dto.UserDto;

public class UserDao {
	
	/**
	 * Méthode qui enregistre l'user sur la base de donnée s'il n'existe pas déjà.
	 * @param usr, l'utilisateur à enregistrer sur la base de données.
	 */
	public static void newUser(UserDto usr){
		//On envoie sur la bdd
		Configuration con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory sessFact = con.buildSessionFactory();
		Session s = sessFact.openSession();
		
		//On créer le nouvel utilisateur
		UserDto newUser = new UserDto(usr.getPrenom(),usr.getNom(),usr.getEmail(), usr.getPassword());
		
		//On démarre la transation
		Transaction tr = s.beginTransaction();
		
		//On enregistre l'utilisateur sur la bdd
		s.save(newUser);
		
		//On commit le reste et ferme la transaction et session
		tr.commit();
		s.close();
		sessFact.close();
	}
	
	
	/**
	 * Méthode qui vérifie que le mot de passe tapé correspond bien à l'utilisateur.
	 * @return, vrai ou faux dans le cas ou le login et le mot de passe correspondent.
	 */
	public static UserDto connexion(String email, String password){
		//On envoie sur la bdd
		Configuration con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory sessFact = con.buildSessionFactory();
		Session s = sessFact.openSession();
		
		//On recherche l'utilisateur avec l'email
		Query q = s.createQuery("from dto.UserDto where email = ? and password = ?");
		q.setParameter(0, email);
		q.setParameter(1, password);
		
		//L'utilisateur trouvé dans la base de données
		UserDto userTrouve = (UserDto) q.list().get(0);
		
		//On ferme les connexions 
		s.close();
		sessFact.close();
		
		//On retourne l'utilisateur trouvé (null si aucun)
		return userTrouve;
	}
	
	
	/**
	 * Méthode qui retourne un utilisateur trouvé en fonction de son id (stocké en session).
	 * @param id, l'id de l'utilisateur stocké en variable de session.
	 * @return, un utilisateur trouvé.
	 */
	public static UserDto getUserById(String id){
		
		//On envoie sur la bdd
		Configuration con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory sessFact = con.buildSessionFactory();
		Session s = sessFact.openSession();
		
		//On recherche l'utilisateur avec son id
		Query q = s.createQuery("from dto.UserDto where id = ?");
		q.setParameter(0, id);
		
		//On récupère l'utilisateur
		UserDto userTrouve = (UserDto) q.list().get(0);
		
		//On ferme les connexions 
		s.close();
		sessFact.close();
		
		return userTrouve;
	}
	
	/**
	 * Méthode qui met à jour un utilisateur dans la base de données.
	 * @param usr, l'utilisateur mit à jour.
	 */
	public static void majUser(UserDto userOld, UserDto userNew){
		
		//On modifie les données
		userOld.setNom(userNew.getNom());
		userOld.setPrenom(userNew.getPrenom());
		userOld.setPassword(userNew.getPassword());
		
		//On envoie sur la bdd
		Configuration con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory sessFact = con.buildSessionFactory();
		Session s = sessFact.openSession();
		
		//On démarre la transation
		Transaction tr = s.beginTransaction();
		
		//On enregistre l'utilisateur sur la bdd
		s.update(userOld);
		
		//On commit le reste et ferme la transaction et session
		tr.commit();
		s.close();
		sessFact.close();
	}

}
