package toby.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static toby.user.service.UserServiceImpl.MIN_LOGINCOUNT_FOR_SILVER;
import static toby.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import toby.mail.MailSender;
import toby.user.dao.UserDao;
import toby.user.domain.Level;
import toby.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
public class UserServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	MailSender mailSender;
	
	private List<User> users; 
	
	@Before
	public void before() {
		users = Arrays.asList(
				new User("userId1", "userName1", "pass1", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER-1, 0, "userId1@dood.net"),
				new User("userId2", "userName2", "pass2", Level.BASIC, MIN_LOGINCOUNT_FOR_SILVER, 0, "moregorenine@naver.com"),
				new User("userId3", "userName3", "pass3", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER+1, MIN_RECOMMEND_FOR_GOLD-1, "userId3@dood.net"),
				new User("userId4", "userName4", "pass4", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER+1, MIN_RECOMMEND_FOR_GOLD, "moregorenine@naver.com"),
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
	@DirtiesContext
	public void upgradeLevels() {
		userDao.deleteAll();
		for(User user : users) {
			userDao.add(user);
		}
		
		MockMailSender mockMailSender = new MockMailSender();
		userService.setMailSender(mockMailSender);
		
		userService.upgradeLevels();
		List<User> updatedUsers = userDao.getAll();
		log.debug("updatedUsers.get(0).getLevel() : " + updatedUsers.get(0).getLevel());
		this.checkLevelUpgraded(users.get(0), false);
		this.checkLevelUpgraded(users.get(1), true);
		this.checkLevelUpgraded(users.get(2), false);
		this.checkLevelUpgraded(users.get(3), true);
		this.checkLevelUpgraded(users.get(4), false);
		
		List<String> request = mockMailSender.getRequests();
		assertThat(request.size(), is(2));
		assertThat(request.get(0), is(users.get(1).getEmail()));
		assertThat(request.get(1), is(users.get(3).getEmail()));
	}
	
	/**
	 * USERS 테이블의 사용자 레벨 업그레이드
	 */
	@Test
	@DirtiesContext
	public void mockUpgradeLevels() {
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		
		UserDao mockUserDao = mock(UserDao.class);
		when(mockUserDao.getAll()).thenReturn(this.users);
		userServiceImpl.setUserDao(mockUserDao);
		
		MailSender mockMailSender = mock(MailSender.class);
		userServiceImpl.setMailSender(mockMailSender);
		
		userServiceImpl.upgradeLevels();
		
		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao, times(2)).update(any(User.class));
		verify(mockUserDao).update(users.get(1));
		assertThat(users.get(1).getLevel(), is(Level.SILVER));
		verify(mockUserDao).update(users.get(3));
		assertThat(users.get(3).getLevel(), is(Level.GOLD));
		
		ArgumentCaptor<SimpleMailMessage> mailMessageArg = 
				ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mockMailSender, times(2)).send(mailMessageArg.capture());
		List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
		assertThat(mailMessages.get(0).getTo()[0], is(users.get(1).getEmail()));
		assertThat(mailMessages.get(1).getTo()[0], is(users.get(3).getEmail()));
		
	}
	
	/**
	 * upgradeLevel Exception 발생 테스트
	 * @author moregorenine
	 *
	 */
	@SuppressWarnings("serial")
	static class TestUserServiceExeption extends RuntimeException {
	}
	
	/**
	 * upgradeLevel Exception 발생 테스트
	 * @author moregorenine
	 *
	 */
	static class TestUserService extends UserServiceImpl {

		private String id;
		
		private TestUserService(String id) {
			this.id = id;
		}
		
		@Override
		protected void upgradeLevel(User user) {
			if(user.getId().equals(this.id)){
				throw new TestUserServiceExeption();
			}
			super.upgradeLevel(user);
		}
	}
	
	/**
	 * MailSender Mock 테스트용
	 * @author moregorenine
	 *
	 */
	static class MockMailSender implements MailSender {
		private List<String> requests = new ArrayList<String>();
		
		public List<String> getRequests() {
			return requests;
		}
		
		@Override
		public void send(SimpleMailMessage simpleMessage) throws MailException {
			requests.add(simpleMessage.getTo()[0]);
		}

		@Override
		public void send(SimpleMailMessage[] simpleMessages) throws MailException {
		}
		
	}
	
	/**
	 * transaction 테스트
	 */
	@Test
	public void upgradeAllOrNothing() {
		UserServiceImpl testUserService = new TestUserService(users.get(3).getId());
		testUserService.setUserDao(this.userDao);
		
		UserServiceTx userServiceTx = new UserServiceTx();
		userServiceTx.setTransactionManager(transactionManager);
		userServiceTx.setUserService(testUserService);
		userDao.deleteAll();
		for(User user : users){
			userDao.add(user);
		}
		
		try {
			userServiceTx.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (Exception e) {
		}
		
		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), false);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), false);
		checkLevelUpgraded(users.get(4), false);
	}
	
	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		
		if(upgraded){
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
	}
}
