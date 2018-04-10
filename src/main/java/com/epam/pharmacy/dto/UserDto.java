package com.epam.pharmacy.dto;

public class UserDto implements Dto {

	private Long userId;
	private String userName;
	private String userLastname;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserLastname() {
		return userLastname;
	}
	
	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}
}
