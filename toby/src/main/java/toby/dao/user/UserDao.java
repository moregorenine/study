package toby.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import toby.domain.User;

/**
 * @author moregorenine
 *
 */
public class UserDao {
	
//	초기에 설정하면 사용 중에는 바뀌지 않는 읽기전용 인스턴스 변수
//	private ConnectionMaker connectionMaker;
	
	private DataSource dataSource;
//	매번 새로운 값으로 바뀌는 정보를 담은 인스턴스 변수, 심각한 문제가 발생한다.
//	private Connection c;
//	private User user;
	

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void create() throws SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement(""
				+ "create table users("
				+ "	id		varchar(20) primary key,"
				+ "	name	varchar(20) not null,"
				+ "	password	varchar(10) not null"
				+ ")");
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public void drop() throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("drop table users");
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public void add(User user) throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public void deleteAll() throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("delete from users");
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public int getCount() throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("select count(1) from users");
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		
		rs.close();
		ps.close();
		c.close();
		
		return count;
	}
	
	public User get(String id) throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);
		
		ResultSet rs = ps.executeQuery();
		User searchUser = new User();
		if(rs.next()) {
			searchUser.setId(rs.getString("id"));
			searchUser.setName(rs.getString("name"));
			searchUser.setPassword(rs.getString("password"));
		} else {
			throw new EmptyResultDataAccessException(1);
		}
		
		rs.close();
		ps.close();
		c.close();
		
		return searchUser;
	}
	
}
