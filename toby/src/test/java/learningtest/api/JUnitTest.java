package learningtest.api;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
public class JUnitTest {
	private static final Logger log = LoggerFactory.getLogger(JUnitTest.class);
	
	@Autowired ApplicationContext context;
	
	static Set<JUnitTest> jUnitTests = new HashSet<JUnitTest>();
	static ApplicationContext contextObject = null;

	@Test
	public void test1() {
		log.debug("context :" + context + ", contextObject : " + contextObject);
		assertThat(contextObject == null || contextObject == this.context, is(true));
		contextObject = context;
		log.debug("jUnitTest : " + jUnitTests);
		log.debug("test1() : " + this);
		assertThat(jUnitTests, not(hasItem(this)));
		jUnitTests.add(this);
		log.debug("jUnitTest : " + jUnitTests);
	}
	
	@Test
	public void test2() {
		log.debug("context :" + context + ", contextObject : " + contextObject);
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = context;
		log.debug("jUnitTest : " + jUnitTests);
		log.debug("test2() : " + this);
		assertThat(jUnitTests, not(hasItem(this)));
		jUnitTests.add(this);
		log.debug("jUnitTest : " + jUnitTests);
	}
	
	@Test
	public void test3() {
		log.debug("context :" + context + ", contextObject : " + contextObject);
		assertTrue(contextObject == null || contextObject == this.context);
		contextObject = context;
		log.debug("jUnitTest : " + jUnitTests);
		log.debug("test3() : " + this);
		assertThat(jUnitTests, not(hasItem(this)));
		jUnitTests.add(this);
		log.debug("jUnitTest : " + jUnitTests);
	}

}
