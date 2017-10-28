package toby.dao.user;

import java.sql.SQLException;
import java.util.List;

import toby.domain.User;

public interface UserDao {
	/**
	 * USERS 테이블에 user param insert
	 * @param user
	 * @throws SQLException
	 */
	void add(User user);
	
	/**
	 * USERS 테이블 create
	 * @throws SQLException
	 */
	void create();
	
	/**
	 * USERS 테이블의 모든 데이터 삭제
	 * @throws SQLException
	 */
	void deleteAll();
	
	/**
	 * USERS 테이블 drop
	 * @throws SQLException
	 */
	void drop();
	
	/**
	 * USERS 테이블에서 id에 해당하는 User정보 조회 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	User get(String id);
	
	/**
	 * USERS 테이블에서 모든 User정보 조회
	 * @return
	 */
	public List<User> getAll();
	
	/**
	 * USER 테이블에 데이터 갯수 반환
	 * @return
	 * @throws SQLException
	 */
	int getCount();
	
}
