package api;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitTest {
	private static final Logger log = LoggerFactory.getLogger(JUnitTest.class);
	
	static Set<JUnitTest> jUnitTests = new HashSet<JUnitTest>();

	@Test
	public void test1() {
		log.debug("jUnitTest : " + jUnitTests);
		log.debug("test1() : " + this);
		assertThat(jUnitTests, not(hasItem(this)));
		jUnitTests.add(this);
		log.debug("jUnitTest : " + jUnitTests);
	}
	
	@Test
	public void test2() {
		log.debug("jUnitTest : " + jUnitTests);
		log.debug("test2() : " + this);
		assertThat(jUnitTests, not(hasItem(this)));
		jUnitTests.add(this);
		log.debug("jUnitTest : " + jUnitTests);
	}
	
	@Test
	public void test3() {
		log.debug("jUnitTest : " + jUnitTests);
		log.debug("test3() : " + this);
		assertThat(jUnitTests, not(hasItem(this)));
		jUnitTests.add(this);
		log.debug("jUnitTest : " + jUnitTests);
	}

}
