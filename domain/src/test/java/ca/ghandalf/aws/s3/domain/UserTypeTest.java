package ca.ghandalf.aws.s3.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ca.ghandalf.aws.s3.BaseTest;

public class UserTypeTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(UserTypeTest.class);
	
	@BeforeClass
	public void setUp() {
		MDC.remove("thread");
		MDC.put("thread", this.getClass().getSimpleName());
		logger.info("\t\t\n\n Type tests\n\n");
	}
	
	@Test
	public void userRegularName() {
		logger.info("\t\t\n\n In Info..{}\n\n", UserType.Regular.name());
	}
	
	@Test
	public void userSystemName() {
		logger.info("\t\t\n\n In Info..{}\n\n", UserType.System.name());
	}

}
