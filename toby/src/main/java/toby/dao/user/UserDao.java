package toby.dao.user;

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
	
//	초기에 설정하면 사용 중에는 바뀌지 않는 읽기전용 인스턴스 변수
	private ConnectionMaker connectionMaker;
//	매번 새로운 값으로 바뀌는 정보를 담은 인스턴스 변수, 심각한 문제가 발생한다.
//	private Connection c;
//	private User user;
	

//	public UserDao(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	
	public void setConnectionMaker(ConnectionMaker connectionMaker) {
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
