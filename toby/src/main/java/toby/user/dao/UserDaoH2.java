package toby.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import toby.user.domain.Level;
import toby.user.domain.User;

/**
 * @author moregorenine
 *
 */
public class UserDaoH2 implements UserDao {
	
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource); 
	}
	
	/**
	 * USERS 테이블에 user param insert
	 * @param user
	 * @throws SQLException
	 */
	public void add(User user) {
		this.jdbcTemplate.update(
				"insert into users(id, name, password, level, login, recommend, email)"
				+ " values(?, ?, ?, ?, ?, ?, ?)"
				, user.getId(), user.getName(), user.getPassword()
				, user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
	}
	
	/**
	 * USERS 테이블 create
	 * @throws SQLException
	 */
	public void create() {
		this.jdbcTemplate.update(
					"create table users("
						+ " id			varchar(20) primary key"
						+ ",name		varchar(20) not null"
						+ ",password	varchar(10) not null"
						+ ",level		int not null"
						+ ",login		int not null"
						+ ",recommend	int not null"
						+ ",email		varchar(50)"
						+ ")");
	}
	
	/**
	 * USERS 테이블의 모든 데이터 삭제
	 * @throws SQLException
	 */
	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}
	
	/**
	 * USERS 테이블 drop
	 * @throws SQLException
	 */
	public void drop() {
		this.jdbcTemplate.update("drop table users");
	}
	
	
	
	/**
	 * USERS 테이블에서 id에 해당하는 User정보 조회 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[]{id},
				this.userMapper);
	}
	
	/**
	 * USERS 테이블에서 모든 User정보 조회
	 * @return
	 */
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id",
				this.userMapper);
	}
	
	/**
	 * USER 테이블에 데이터 갯수 반환
	 * @return
	 * @throws SQLException
	 */
	public int getCount() {
		return this.jdbcTemplate.queryForObject(
				"select count(1) from users", Integer.class);
	}
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	};

	public void update(User user) {
		this.jdbcTemplate.update("update users set name = ?, password= ?"
				+ ", level = ?, login = ?, recommend = ?, email = ? where id = ?"
				, user.getName(), user.getPassword(), user.getLevel().intValue()
				, user.getLogin(), user.getRecommend(), user.getEmail(), user.getId());
	}
		
}
