package com.ersystem.services;

import org.apache.log4j.Logger;

import com.ersystem.dao.ERSystemJDBC;

public class TicketUpdater {
	
	private Logger log = Logger.getRootLogger();
	private ERSystemJDBC ersDAO = new ERSystemJDBC();
	
	public void update(int stat, int tic) {
		try {
			ersDAO.changeStat(stat, tic);
		} catch (Exception e) {
			log.info("failed to change ticket status");
		}
	}

}
