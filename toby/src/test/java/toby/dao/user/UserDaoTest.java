package toby.dao.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import toby.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
		this.user1 = new User("userId1", "userName1", "pass1");
		this.user2 = new User("userId2", "userName2", "pass2");
		this.user3 = new User("userId3", "userName3", "pass3");
	}
	
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
		
		User searchUser = new User();
		searchUser = userDao.get("moregorenine");
		
		assertThat(searchUser.getId(), is(insertUser.getId()));
		assertThat(searchUser.getName(), is(insertUser.getName()));
		assertThat(searchUser.getPassword(), is(insertUser.getPassword()));
	}
	
	@Test
	public void getCount() throws SQLException {
		int cnt = 0;
		userDao.deleteAll();
		cnt = userDao.getCount();
		
		assertThat(cnt, is(0));
		
		userDao.add(user1);
		cnt = userDao.getCount();
		assertThat(cnt, is(1));
		
		userDao.add(user2);
		cnt = userDao.getCount();
		assertThat(cnt, is(2));
		
		userDao.add(user3);
		cnt = userDao.getCount();
		assertThat(cnt, is(3));
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));
		
		userDao.get("unknown_id");
	}
}
