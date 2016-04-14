package dto;


public class UserDto {
	String id, prenom, nom, email, password;
	
	public UserDto(){
		
	}
	
	
	public UserDto(String prenom, String nom, String email, String password){
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
}
