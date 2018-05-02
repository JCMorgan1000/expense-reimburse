package com.ersystem.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ersystem.beans.Ticket;
import com.ersystem.dao.ERSystemJDBC;

public class TicketRetriever {
	private Logger log = Logger.getRootLogger();
	private ERSystemJDBC ersDAO = new ERSystemJDBC();
	private List<Ticket> tickets = new ArrayList<>();
	
	public List<Ticket> getAll(int id) {
		tickets = ersDAO.viewAll(id);
		if(tickets == null) {
			log.info("No reimbursements were found");
		} else {
			log.info(tickets.size() + " reimbursements found");
		}
		
		return tickets;
	}
	
	public List<Ticket> getByAuthor(int id) {
		tickets = ersDAO.findByAuthor(id);
		if(tickets == null) {
			log.info("No reimbursements were found");
		} else {
			log.info(tickets.size() + " reimbursements found");
		}
		
		return tickets;
	}
	
	public List<Ticket> getByStatus(int stat) {
		tickets = ersDAO.findByStatus(stat);
		if(tickets == null) {
			log.info("No reimbursements were found");
		} else {
			log.info(tickets.size() + " reimbursements found");
		}
		
		return tickets;
	}
}
