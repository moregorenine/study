package learningtest.jdk;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Proxy;

import org.junit.Test;

public class SimpleProxyTest {

	@Test
	public void simpleProxy() {
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("moregore"), is("Hello moregore"));
		assertThat(hello.sayHi("moregore"), is("Hi moregore"));
		assertThat(hello.sayThankYou("moregore"), is("Thank You moregore"));
		
		Hello proxHello = (Hello) Proxy.newProxyInstance(
				getClass().getClassLoader()
				, new Class[] { Hello.class }
				, new UppercaseHandler(new HelloTarget()));
		
		assertThat(proxHello.sayHello("moregore"), is("HELLO MOREGORE"));
		assertThat(proxHello.sayHi("moregore"), is("HI MOREGORE"));
		assertThat(proxHello.sayThankYou("moregore"), is("THANK YOU MOREGORE"));
	}
}
