package ca.ghandalf.aws.s3.business.simple;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ca.ghandalf.aws.s3.BaseTest;

public class SendAndReceiveSimpleTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(SendAndReceiveSimpleTest.class);
	
	@Inject
	private SimpleSender sender;
	
	@Inject
	private SimpleReceiver receiver;	
	
	@BeforeClass
	public void setUp() {
		MDC.remove("thread");
		MDC.put("thread", this.getClass().getSimpleName());
		logger.info("\t\t\n\n Test setUp sender {}\n\n", this.sender);
		Assert.assertNotNull(sender);
		Assert.assertNotNull(receiver);
	}

	@Test
	public void sendMessage() {
		for (int i = 0; i < BaseTest.MAX_TO_SEND; i++) {
			String expected = "SimpleMessage_0" + i;
	
			logger.info("Sending this message: [{}]", expected.toString());
			
			String actual = this.sender.send(expected);
			Assert.assertEquals(actual, expected);
		}
	}

	@Test(dependsOnMethods = { "sendMessage" })
	public void receiveMessage() {
		for (int i = 0; i < BaseTest.MAX_TO_RECEIVED
				;i++) {
			String actual = receiver.receive();
			logger.info("Receiving this message: [{}]", actual.toString());
			Assert.assertNotNull(actual);
		}
	}

}
