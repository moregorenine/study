package learningtest.spring.factorybean;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/FactoryBeanTest-context.xml")
public class FactoryBeanTest {

	@Autowired
	ApplicationContext applicationContext;
	
	@Test
	public void getMessageFromFactoryBean() {
		Object message = applicationContext.getBean("message");
//		assertThat(message, is(Message.class));
		assertThat(((Message)message).getText(), is("Factory Bean"));
		
//		Object factory = applicationContext.getBean("&message");
//		assertThat(factory, MessageFactoryBean.class);
//		assertThat(factory, is(MessageFactoryBean.class));
	}
}
