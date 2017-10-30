package toby.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import toby.user.dao.UserDao;
import toby.user.domain.Level;
import toby.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
public class UserServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Autowired
	UserService userServie;
	
	@Autowired
	UserDao userDao;
	
	private List<User> users; 
	
	@Before
	public void before() {
		users = Arrays.asList(
				new User("userId1", "userName1", "pass1", Level.BASIC, 49, 0),
				new User("userId2", "userName2", "pass2", Level.BASIC, 50, 0),
				new User("userId3", "userName3", "pass3", Level.SILVER, 60, 29),
				new User("userId4", "userName4", "pass4", Level.SILVER, 60, 30),
				new User("userId5", "userName5", "pass5", Level.GOLD, 100, 100)
				);
	}
	
	/**
	 * USERS 테이블의 사용자 레벨 업그레이드
	 */
	@Test
	public void upgradeLevels() {
		userDao.deleteAll();
		for(User user : users) {
			userDao.add(user);
		}
		
		userServie.upgradeLevels();
		List<User> updatedUsers = userDao.getAll();
		log.debug("updatedUsers.get(0).getLevel() : " + updatedUsers.get(0).getLevel());
		assertThat(updatedUsers.get(0).getLevel(), is(Level.BASIC));
		assertThat(updatedUsers.get(1).getLevel(), is(Level.SILVER));
		assertThat(updatedUsers.get(2).getLevel(), is(Level.SILVER));
		assertThat(updatedUsers.get(3).getLevel(), is(Level.GOLD));
		assertThat(updatedUsers.get(4).getLevel(), is(Level.GOLD));
	}

}
