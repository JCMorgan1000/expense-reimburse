package com.ersystem.services;

import com.ersystem.beans.Ticket;
import com.ersystem.dao.ERSystemJDBC;

public class TicketSubmitter {
	private ERSystemJDBC ersDAO = new ERSystemJDBC();
	
	public void submit(Ticket t) {
		ersDAO.addTicket(t);
	}
}
