package toby.dao;

import java.sql.SQLException;

import org.junit.Test;

import toby.domain.User;

public class UserDaoTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {
		UserDao userDao = new DaoFactory().userDao();
		userDao.drop();
		userDao.create();
		
		User insertUser = new User();
		insertUser.setId("moregorenine");
		insertUser.setName("한진석");
		insertUser.setPassword("testpass");
		
		userDao.add(insertUser);
		
		userDao.get("moregorenine");
	}

}
