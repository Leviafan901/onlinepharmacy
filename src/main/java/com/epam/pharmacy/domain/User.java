package com.epam.pharmacy.domain;

import java.io.Serializable;

import com.epam.pharmacy.dao.Identified;
import com.epam.pharmacy.domain.enumeration.Role;

public class User implements Serializable, Identified<Long> {

	private Long id;
	private String name;
	private String lastname;
	private String login;
	private String password;
	private Role role;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object object) {//
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		User otherUser = (User) object;
		if (id == null) {
			if (otherUser.id != null)
				return false;
		} else if (!id.equals(otherUser.id))
			return false;
		if (lastname == null) {
			if (otherUser.lastname != null)
				return false;
		} else if (!lastname.equals(otherUser.lastname))
			return false;
		if (login == null) {
			if (otherUser.login != null)
				return false;
		} else if (!login.equals(otherUser.login))
			return false;
		if (name == null) {
			if (otherUser.name != null)
				return false;
		} else if (!name.equals(otherUser.name))
			return false;
		if (password == null) {
			if (otherUser.password != null)
				return false;
		} else if (!password.equals(otherUser.password))
			return false;
		if (role != otherUser.role)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastname=" + lastname + ", login=" + login + ", password="
				+ password + ", role=" + role + "]";
	}
}
