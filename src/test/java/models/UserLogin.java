package models;


public class UserLogin {

	private String userLoginEmail;
	private String loginStatus;
	
	
	public String getuserLoginEmail() {
		return userLoginEmail;
	}
	public String getloginStatus() {
		return loginStatus;
	}
	
	public void setuserLoginEmail(String userLoginEmail) {
		this.userLoginEmail = userLoginEmail;
	}
	public void setloginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
}
