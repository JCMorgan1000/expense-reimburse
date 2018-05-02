package com.ersystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ersystem.beans.Ticket;
import com.ersystem.beans.User;
import com.ersystem.util.ConnectionUtil;

public class ERSystemJDBC implements ERSystemDAO {
	private Logger log = Logger.getRootLogger();
	private ConnectionUtil connUtil = ConnectionUtil.getConnectionUtil();
	private static User user = new User();
	
	@Override
	public void addTicket(Ticket tick) {		  		
		log.trace("method called to insert new ticket");
		log.trace("Attempting to get connection to db");
		log.trace(tick);
		try (Connection conn = connUtil.getConnection()) {
			log.trace("connection established with db, creating prepared statement");
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ers_reimbursement (reimb_amount, reimb_description, "
					+ "reimb_author, reimb_status_id, reimb_type_id) VALUES (?,?,?,?,?)");
            
			ps.setDouble(1, tick.getAmount());
            ps.setString(2, tick.getDescription());
            ps.setInt(3, user.getId());
            ps.setInt(4, 1);
            ps.setInt(5, tick.getTypeID());

			int rowsInserted = ps.executeUpdate();
			log.debug("query inserted " + rowsInserted + " rows into the db");		
		} catch (SQLException e) {
			log.warn("failed to insert new ticket");
		}  
	}

	@Override
	public void changeStat(int stat, int tic) {
		log.trace("method called to update ticket #" + tic);
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			log.trace("connection established with db, creating prepared statement");
			PreparedStatement ps = conn.prepareStatement("UPDATE ers_reimbursement "
					+ "SET reimb_status_id = ?, reimb_resolver = ?"
					+ "WHERE reimb_id = ?");	
			
			ps.setInt(1, stat);
			ps.setInt(2, user.getId());
			ps.setInt(3, tic);
			
			int numRowsUpdated = ps.executeUpdate();
			log.trace("updated " + numRowsUpdated + " rows");		
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to update ticket");
		}
	}

	@Override
	public List<Ticket> findByAuthor(int id) {
		List<Ticket> ticketList = new ArrayList<>();
		log.trace("method called to select tickets by author id #" + id);
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursement WHERE reimb_author = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Ticket tick = new Ticket(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"), 
						rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getInt("reimb_author"), 
						rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				ticketList.add(tick);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to retreive reimbursement tickets");
		}
		return ticketList;
	}

	@Override
	public List<Ticket> viewAll(int id) {
		List<Ticket> ticketList = new ArrayList<>();
		log.trace("method called to select all tickets");
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursement"); //WHERE reimb_author = !?");
			//ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Ticket tick = new Ticket(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"), 
						rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getInt("reimb_author"), 
						rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				ticketList.add(tick);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to retreive reimbursement tickets");
		}
		return ticketList;
	}

	@Override
	public List<Ticket> findByStatus(int stat) {
		List<Ticket> ticketList = new ArrayList<>();
		log.trace("method called to select tickets by status id #" + stat);
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_reimbursement WHERE reimb_status_id = ?");
			ps.setInt(1, stat);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Ticket tick = new Ticket(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"), 
						rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getInt("reimb_author"), 
						rs.getInt("reimb_resolver"), rs.getInt("reimb_status_id"), rs.getInt("reimb_type_id"));
				ticketList.add(tick);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to retreive reimbursement tickets");
		}
		return ticketList;
	}

	@Override
	public User getUser(String username, String password) {
		log.trace("method called to select the user named: " + username);
		log.trace("Attempting to get connection to db");
		try (Connection conn = connUtil.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				user = new User(rs.getInt("ers_users_id"), rs.getString("ers_username"), rs.getString("ers_password"), 
						rs.getString("user_first_name"), rs.getString("user_last_name"), rs.getString("user_email"), rs.getInt("user_role_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed to retreive user");
		}
		
		return user;
	}

}
