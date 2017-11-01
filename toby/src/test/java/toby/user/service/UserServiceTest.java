package toby.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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
import org.springframework.transaction.PlatformTransactionManager;

import toby.user.dao.UserDao;
import toby.user.domain.Level;
import toby.user.domain.User;

import static toby.user.service.UserService.MIN_LOGINCOUNT_FOR_SILVER;
import static toby.user.service.UserService.MIN_RECOMMEND_FOR_GOLD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
public class UserServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	UserDao userDao;
	
	private List<User> users; 
	
	@Before
	public void before() {
		users = Arrays.asList(
				new User("userId1", "userName1", "pass1", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER-1, 0, "userId1@dood.net"),
				new User("userId2", "userName2", "pass2", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER, 0, "userId2@dood.net"),
				new User("userId3", "userName3", "pass3", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER+1, MIN_RECOMMEND_FOR_GOLD-1, "userId3@dood.net"),
				new User("userId4", "userName4", "pass4", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER+1, MIN_RECOMMEND_FOR_GOLD, "userId4@dood.net"),
				new User("userId5", "userName5", "pass5", Level.GOLD, Integer.MAX_VALUE, Integer.MAX_VALUE, "userId5@dood.net")
				);
	}
	
	@Test
	public void add() {
		userDao.deleteAll();
		
		User userWithLevel = users.get(3);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);
		
		userService.add(userWithLevel);
		userService.add(userWithoutLevel);
		
		User userWithLevelDB = userDao.get(userWithLevel.getId());
		assertThat(userWithLevelDB.getLevel(), is(Level.SILVER));
		User userWithoutLevelDB = userDao.get(userWithoutLevel.getId());
		assertThat(userWithoutLevelDB.getLevel(), is(Level.BASIC));
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
		
		userService.upgradeLevels();
		List<User> updatedUsers = userDao.getAll();
		log.debug("updatedUsers.get(0).getLevel() : " + updatedUsers.get(0).getLevel());
		assertThat(updatedUsers.get(0).getLevel(), is(Level.BASIC));
		assertThat(updatedUsers.get(1).getLevel(), is(Level.SILVER));
		assertThat(updatedUsers.get(2).getLevel(), is(Level.SILVER));
		assertThat(updatedUsers.get(3).getLevel(), is(Level.GOLD));
		assertThat(updatedUsers.get(4).getLevel(), is(Level.GOLD));
	}

	static class TestUserServiceExeption extends RuntimeException {
	}
	
	static class TestUserService extends UserService {

		private String id;
		
		private TestUserService(String id) {
			this.id = id;
		}
		
		private List<User> users;
		
		@Override
		protected void upgradeLevel(User user) {
			if(user.getId().equals(this.id)){
				throw new TestUserServiceExeption();
			}
			super.upgradeLevel(user);
		}
	}
	
	@Test
	public void upgradeAllOrNothing() {
		UserService testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
		testUserService.setTransactionManager(transactionManager);
		userDao.deleteAll();
		for(User user : users){
			userDao.add(user);
		}
		
		try {
			testUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (Exception e) {
		}
		
		User userUpgraded = userDao.get(users.get(1).getId()); 
		log.debug(""+users.get(1).getLevel());
		log.debug(""+userUpgraded.getLevel());
		assertThat(userUpgraded.getLevel(), is(users.get(1).getLevel()));
	}
}
