package toby.dao.user;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import toby.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserDaoTest {

	@Autowired
	UserDao userDao;
	
	@Test
	public void test() throws ClassNotFoundException, SQLException {
//		@SuppressWarnings("resource")
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
		
//		UserDao userDao = new DaoFactory().userDao();
//		UserDao userDao = context.getBean("userDao", UserDao.class);
		
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
