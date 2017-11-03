package learningtest.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
	
	private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);
	
	@Test
	public void invokeMethod() throws Exception {
		String name = "Spring";
		
		assertThat(name.length(), is(6));
		
		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer)lengthMethod.invoke(name), is(6));

		log.debug("name.charAt(0) : " + name.charAt(0));
		assertThat(name.charAt(0), is('S'));
		assertThat(name.charAt(5), is('g'));

		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat(charAtMethod.invoke(name, 0), is('S'));
		assertThat(charAtMethod.invoke(name, 5), is('g'));
		
	}
}
