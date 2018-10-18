package ca.ghandalf.aws.s3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.testng.TestNG;

@ContextConfiguration
@ComponentScan("ca.ghandalf.aws.s3") 
public abstract class BaseTest extends TestNG {

	public static final int MAX_TO_SEND = 4;
	public static final int MAX_TO_RECEIVED = MAX_TO_SEND - 1;
}
