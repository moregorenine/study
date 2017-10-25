package api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import toby.dao.user.UserDao;
import toby.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
@DirtiesContext //테스트 메소드애서 애플리케이션 컨텍스트의 구성이나 상태를 변경한다는 것을 테스트 컨텍스트 프레임워크에 알려준다.
public class H2Test {

	private static final Logger log = LoggerFactory.getLogger(H2Test.class);
	
	@Autowired
	UserDao userDao;
	
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
		log.debug("userDao : " + userDao);
		log.debug("test object : " + this);
		this.user1 = new User("userId1", "userName1", "pass1");
		this.user2 = new User("userId2", "userName2", "pass2");
		this.user3 = new User("userId3", "userName3", "pass3");
	}
	
	@Test
	public void h2Connection () throws SQLException {
		DataSource dataSource = new SingleConnectionDataSource("jdbc:h2:~/toby", "sa", null, true);
		assertNotNull(dataSource);
		userDao.setDataSource(dataSource);
		
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
		
		dataSource = new SingleConnectionDataSource("jdbc:h2:~/test", "sa", null, true);
		assertNotNull(dataSource);
		
		userDao.setDataSource(dataSource);
		
		cnt = 0;
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
}
