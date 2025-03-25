package models;

import java.util.List;

public class User {

	private String userid;
	private String staffid;
    private String studentid;
 
	
	public String getuserid() {
	return userid;
	}
	public String getstaffid() {
		return staffid;
	}

	public String getstudentid() {
		return studentid;
	}
	public void setuserid(String userid) {
		this.userid = userid;
	}
	public void setstaffid(String staffid) {
		this.staffid = staffid;
	}

	public void setstudentid(String studentid) {
		this.studentid = studentid;
	}
		
	private String userComments;
	private String userEduPg;
	private String userEduUg;
	private String userFirstName;
	private String userLastName;
	private String userLinkedinUrl;
	private String userLocation;
	private String userMiddleName;
	private String userPhoneNumber;
	private List<UserRoleMap> userRoleMaps;
	private String userTimeZone;
	private String userVisaStatus;
	private String hibernateLazyInitializer;
	private String roleName;
	private String roleDesc;
	private String creationTime;
	private String lastModTime;
	private UserLogin userLogin;
	
	
	
	public String getUserid() {
		return userid;
	}
	public String getStaffid() {
		return staffid;
	}
	public String getStudentid() {
		return studentid;
	}
	public String getUserComments() {
		return userComments;
	}
	public String getUserEduPg() {
		return userEduPg;
	}
	public String getUserEduUg() {
		return userEduUg;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public String getUserLinkedinUrl() {
		return userLinkedinUrl;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public String getUserMiddleName() {
		return userMiddleName;
	}
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public List<UserRoleMap> getUserRoleMaps() {
		return userRoleMaps;
	}
	public String getUserTimeZone() {
		return userTimeZone;
	}
	public String getUserVisaStatus() {
		return userVisaStatus;
	}
	public String getHibernateLazyInitializer() {
		return hibernateLazyInitializer;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public String getLastModTime() {
		return lastModTime;
	}
	public UserLogin getUserLogin() {
		return userLogin;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}
	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	public void setUserEduPg(String userEduPg) {
		this.userEduPg = userEduPg;
	}
	public void setUserEduUg(String userEduUg) {
		this.userEduUg = userEduUg;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public void setUserLinkedinUrl(String userLinkedinUrl) {
		this.userLinkedinUrl = userLinkedinUrl;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public void setUserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
	public void setUserRoleMaps(List<UserRoleMap> userRoleMaps) {
		this.userRoleMaps = userRoleMaps;
	}
	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}
	public void setUserVisaStatus(String userVisaStatus) {
		this.userVisaStatus = userVisaStatus;
	}
	public void setHibernateLazyInitializer(String hibernateLazyInitializer) {
		this.hibernateLazyInitializer = hibernateLazyInitializer;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public void setLastModTime(String lastModTime) {
		this.lastModTime = lastModTime;
	}
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}


	
	
	
	

	    
}
