package com.ersystem.services;

import org.apache.log4j.Logger;

import com.ersystem.beans.User;
import com.ersystem.dao.ERSystemJDBC;

public class Authenticator {
	private Logger log = Logger.getRootLogger();
	private ERSystemJDBC ersDAO = new ERSystemJDBC();
	private User user;
	
	public User validate(String username, String password) {
		user = ersDAO.getUser(username, password);
		if(user == null) {
			log.info("Invalid Username or Password");
		}
		
		return user;	
	}
}
