package com.example.weatherinfo;


public class JASONDataObject {
//	ArrayList<Boolean> user; 
	String Users;
	String firstname;
	String lastname;
	String username;
	public String getUser() {
		return Users;
	}
	public void setUser(String user) {
		Users = user;
	}
	/*public ArrayList<Boolean> getUser() {
		return user;
	}
	public void setUser(ArrayList<Boolean> user) {
		this.user = user;
	}*/
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
