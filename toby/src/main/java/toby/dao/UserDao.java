package toby.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import toby.domain.User;

/**
 * @author moregorenine
 *
 */
public class UserDao {
	
	private ConnectionMaker connectionMaker;

	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void create() throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement(""
				+ "create table users("
				+ "	id		varchar(20) primary key,"
				+ "	name	varchar(20) not null,"
				+ "	password	varchar(10) not null"
				+ ")");
		
		ps.execute();
		
		ps.close();
		c.close();
	}
	
	public void drop() throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("drop table users");
		
		ps.execute();
		
		ps.close();
		c.close();
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public void get(String id) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		User searchUser = new User();
		searchUser.setId(rs.getString("id"));
		searchUser.setName(rs.getString("name"));
		searchUser.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		c.close();
	}
	
}
