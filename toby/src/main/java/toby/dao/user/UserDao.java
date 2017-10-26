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
	

//	초기에 설정하면 사용 중에는 바뀌지 않는 읽기전용 인스턴스 변수
//	private ConnectionMaker connectionMaker;
	
//	private DataSource dataSource;
//	매번 새로운 값으로 바뀌는 정보를 담은 인스턴스 변수, 심각한 문제가 발생한다.
//	private Connection c;
//	private User user;
	
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
	
	public int getCount() throws SQLException {
		return this.jdbcTemplate.queryForObject("select count(1) from users", Integer.class);
	}
}
