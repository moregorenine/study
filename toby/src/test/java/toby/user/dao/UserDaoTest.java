package toby.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static toby.user.service.UserServiceImpl.MIN_LOGINCOUNT_FOR_SILVER;
import static toby.user.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import toby.user.domain.Level;
import toby.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
//@DirtiesContext //테스트 메소드애서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트 프레임워크에 알려준다.
public class UserDaoTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Autowired
	private UserDaoH2 userDao;
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void before() {
		userDao.drop();
		userDao.create();
		log.debug("userDao : " + userDao);
		log.debug("test object : " + this);
		this.user1 = new User("userId1", "userName1", "pass1", Level.BASIC, 1, 0, "userId1@dood.net");
		this.user2 = new User("userId2", "userName2", "pass2", Level.SILVER, MIN_LOGINCOUNT_FOR_SILVER+1, MIN_RECOMMEND_FOR_GOLD-1, "userId2@dood.net");
		this.user3 = new User("userId3", "userName3", "pass3", Level.GOLD, Integer.MAX_VALUE, Integer.MAX_VALUE, "userId3@dood.net");
	}
	
	@After
	public void after() {
	}
	
	@Test
	public void add_deleteAll_get_getCount() {
		//add(User) 테스트
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
		
		User User1Test = userDao.get(user1.getId());
		assertThat(User1Test.getId(), is(user1.getId()));
		assertThat(User1Test.getName(), is(user1.getName()));
		assertThat(User1Test.getPassword(), is(user1.getPassword()));
		assertThat(User1Test.getLevel(), is(user1.getLevel()));
		assertThat(User1Test.getLogin(), is(user1.getLogin()));
		assertThat(User1Test.getRecommend(), is(user1.getRecommend()));
		
	}
	
	@Test
	public void addFailure() {
		//add(User) 테스트
		int cnt = 0;
		userDao.deleteAll();
		cnt = userDao.getCount();
		assertThat(cnt, is(0));
		userDao.add(user1);
		cnt = userDao.getCount();
		assertThat(cnt, is(1));
		log.debug("DB Key 중복 테스트");
		exception.expect(DuplicateKeyException.class);
		userDao.add(user1);
	}
	
	@Test
	public void drop_create() {
		userDao.drop();
		userDao.create();
		int cnt = userDao.getCount();
		assertThat(cnt, is(0));
	}
	
	@Test
	public void getUserFailure() {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));
		
		exception.expect(EmptyResultDataAccessException.class);
		userDao.get("unknown_id");
		
	}
	
	@Test
	public void getAll() {
		userDao.deleteAll();
		List<User> users = userDao.getAll();
		assertThat(users.size(), is(0));
		userDao.add(user1);
		userDao.add(user2);
		userDao.add(user3);
		
		users = userDao.getAll();
		assertThat(users.size(), is(3));
		log.debug("user1 id : " + users.get(0).getId());
		assertTrue(user1.equals(users.get(0)));
		assertTrue(user2.equals(users.get(1)));
		assertTrue(user3.equals(users.get(2)));
		
	}
	
	/**
	 * USERS 테이블에서 User정보 update
	 */
	@Test
	public void update() {
		userDao.deleteAll();
		userDao.add(user1);
		userDao.add(user2);
		
		user1.setName("nameUpdate");
		user1.setPassword("pass4");
		user1.setLevel(Level.GOLD);
		user1.setLogin(789);
		user1.setRecommend(987);
		
		userDao.update(user1);
		
		User user1Update = userDao.get(user1.getId());
		assertThat(user1Update.equals(user1), is(true));
		log.debug("user2's id : " + user2.getId());
		User user2Same = userDao.get(user2.getId());
		assertThat(user2Same, is(user2));
	}
}
