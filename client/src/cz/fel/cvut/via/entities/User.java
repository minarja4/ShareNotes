package cz.fel.cvut.via.entities;

public class User {

	private Integer id;
	private String username;
	private String email;
	private String password;
	private String token;
	public User(Integer id, String email, String password, String token) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.token = token;
	}
	public User() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
	
	
	
	
	
}
