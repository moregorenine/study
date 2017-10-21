package toby.dao;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import toby.domain.User;

public class UserDaoTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {
		@SuppressWarnings("resource")
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		
//		UserDao userDao = new DaoFactory().userDao();
		UserDao userDao = context.getBean("userDao", UserDao.class);
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
