package toby.dao.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import toby.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
//@DirtiesContext //테스트 메소드애서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트 프레임워크에 알려준다.
public class UserDaoTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Autowired
	private UserDao userDao;
	
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void before() {
		log.debug("userDao : " + userDao);
		log.debug("test object : " + this);
		this.user1 = new User("userId1", "userName1", "pass1");
		this.user2 = new User("userId2", "userName2", "pass2");
		this.user3 = new User("userId3", "userName3", "pass3");
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
}
