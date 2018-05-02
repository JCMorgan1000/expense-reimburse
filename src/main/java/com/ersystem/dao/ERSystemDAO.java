package com.ersystem.dao;

import java.util.List;

import com.ersystem.beans.Ticket;
import com.ersystem.beans.User;

public interface ERSystemDAO {
	
	//C
	void addTicket(Ticket tick);
	
	//U
	void changeStat(int stat, int tic);

	//R
	List<Ticket> findByAuthor(int id);
	
	//R
	List<Ticket> viewAll(int id);
	
	//R
	List<Ticket> findByStatus(int stat);
	
	User getUser(String username, String password);
}
