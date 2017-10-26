package toby.dao.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import toby.domain.User;

/**
 * @author moregorenine
 *
 */
public class UserDao {
	
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource); 
	}
	
	/**
	 * USERS 테이블에 user param insert
	 * @param user
	 * @throws SQLException
	 */
	public void add(User user) throws SQLException {
		this.jdbcTemplate.update(
				"insert into users(id, name, password) values(?, ?, ?)"
				, user.getId(), user.getName(), user.getPassword());
	}
	
	/**
	 * USERS 테이블 create
	 * @throws SQLException
	 */
	public void create() throws SQLException {
		this.jdbcTemplate.update(
					"create table users("
						+ " id		varchar(20) primary key,"
						+ "	name	varchar(20) not null,"
						+ "	password	varchar(10) not null"
						+ ")");
	}
	
	/**
	 * USERS 테이블의 모든 데이터 삭제
	 * @throws SQLException
	 */
	public void deleteAll() throws SQLException {
		this.jdbcTemplate.update("delete from users");
	}
	
	/**
	 * USERS 테이블 drop
	 * @throws SQLException
	 */
	public void drop() throws SQLException {
		this.jdbcTemplate.update("drop table users");
	}
	
	
	
	/**
	 * USERS 테이블에서 id에 해당하는 User정보 조회 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public User get(String id) throws SQLException {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[]{id},
				new RowMapper<User>(){
					@Override
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});
	}
	
	/**
	 * USER 테이블에 데이터 갯수 반환
	 * @return
	 * @throws SQLException
	 */
	public int getCount() throws SQLException {
		return this.jdbcTemplate.queryForObject(
				"select count(1) from users", Integer.class);
	}
}
