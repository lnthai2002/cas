package info.darkportal.cas.server.support.jdbc.custom;

public class User {
	private String email;
	private String encryptedPassword;
	private String salt;

	public String getEmail(){
		return this.email;
	}

	public String getEncryptedPassword(){
		return this.encryptedPassword;
	}

	public String getSalt(){
		return this.salt;
	}

	public void setSalt(String salt){
		this.salt = salt;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public void setEncryptedPassword(String encryptedPassword){
		this.encryptedPassword = encryptedPassword;
	}
}
