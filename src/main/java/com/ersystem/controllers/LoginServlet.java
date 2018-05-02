package com.ersystem.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.log4j.Logger;
import com.ersystem.beans.User;
import com.ersystem.services.Authenticator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginServlet extends DefaultServlet  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1588925831362436303L;
	private Logger log = Logger.getRootLogger();
	private Authenticator authentic = new Authenticator();
	
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String url = request.getPathInfo();
		log.trace("Get request made with path " + url);
		request.getRequestDispatcher("/index.html").forward(request, response);
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        log.info("post request made");
        
        String json = request.getReader().lines().reduce((acc, cur) -> acc + cur).get();
		log.trace("json " + json);
        ObjectMapper om = new ObjectMapper();
		User credentials = (User) om.readValue(json, User.class);
		log.trace(credentials);
        User user = authentic.validate(credentials.getUsername(), credentials.getPassword()); 
        
        if (user != null) {
        		HttpSession sess = request.getSession();
        		sess.setAttribute("user", user);
        		String respJson = om.writeValueAsString(user);
    			response.getWriter().write(respJson);
        		if(user.getUserRoleId() == 1) {
        			response.sendRedirect(request.getContextPath() + "/employee-home.html");
        		} else if(user.getUserRoleId() == 2) {
        			response.sendRedirect(request.getContextPath() + "/manager-home.html");
        		}
        } else {
    			request.getRequestDispatcher("/index.html").forward(request, response);
        }
    }
	
}
