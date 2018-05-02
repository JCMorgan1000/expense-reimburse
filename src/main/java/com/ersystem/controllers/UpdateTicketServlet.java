package com.ersystem.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;

import com.ersystem.beans.Ticket;
import com.ersystem.services.TicketUpdater;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateTicketServlet extends DefaultServlet {
	
	private Logger log = Logger.getRootLogger();
	private TicketUpdater updater = new TicketUpdater();
	
	@Override
    public void init() throws ServletException {
        log.info("servlet initialzed");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.trace("method = " + req.getMethod());
        log.info("request serviced");
        super.service(req, resp);
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		resp.addHeader("Access-Control-Allow-Headers",
				"Origin, Methods, Credentials, X-Requested-With, Content-Type, Accept");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
		resp.setContentType("application/json");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException {
    	String json = request.getReader().lines().reduce((acc, cur) -> acc + cur).get();
    	log.trace("json " + json);
    	ObjectMapper om = new ObjectMapper();
    	Ticket t = om.readValue(json, Ticket.class);
    	updater.update(t.getStatusID(), t.getId());
    }

}
