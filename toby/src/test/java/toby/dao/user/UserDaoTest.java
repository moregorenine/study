package toby.dao.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import toby.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
//@DirtiesContext //테스트 메소드애서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트 프레임워크에 알려준다.
public class UserDaoTest {

	private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Autowired
	private UserDao userDao;
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void before() throws SQLException {
		log.debug("userDao : " + userDao);
		log.debug("test object : " + this);
		this.user1 = new User("userId1", "userName1", "pass1");
		this.user2 = new User("userId2", "userName2", "pass2");
		this.user3 = new User("userId3", "userName3", "pass3");
	}
	
	@After
	public void after() throws SQLException {
	}
	
	@Test
	public void test() throws ClassNotFoundException, SQLException {
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
	
	@Test
	public void drop() throws SQLException {
		userDao.drop();
		userDao.create();
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));
		
		userDao.get("unknown_id");
	}
}
