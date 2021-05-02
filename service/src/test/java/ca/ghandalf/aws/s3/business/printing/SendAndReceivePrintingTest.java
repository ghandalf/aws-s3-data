package ca.ghandalf.aws.s3.business.printing;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ca.ghandalf.aws.s3.BaseTest;
import ca.ghandalf.aws.s3.domain.Print;
import ca.ghandalf.aws.s3.domain.User;
import ca.ghandalf.aws.s3.domain.UserType;

public class SendAndReceivePrintingTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(SendAndReceivePrintingTest.class);

	@Inject
	private PrintingSender sender;
	
	@Inject
	private PrintingReceiver receiver;	
	
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
			Print expected = build("Printer_0" + i, BaseTest.MAX_TO_SEND + 1);
	
			logger.info("Sending this message: [{}]", expected.toString());
			
			Print actual = this.sender.send(expected);
			Assert.assertEquals(actual, expected);
		}
	}

	@Test(dependsOnMethods = { "sendMessage" })
	public void receiveMessage() {
		for (int i = 0; i < BaseTest.MAX_TO_RECEIVED; i++) {
			Print actual = receiver.receive();
			logger.info("Receiving this message: [{}]", actual.toString());
			Assert.assertNotNull(actual);
		}
	}
	
	private Print build(String printer, int copies) {
		Print print = new Print();
		
		User user = new User();
		user.setCompany("Fresche");
		user.setDistrict("Montreal");
		user.setName("fouellet");
		user.setUserType(UserType.System);
		
		print.setUser(user);
		print.setHold(false);
		print.setCopies(copies);
		print.setCreatedDate(new Date());
		print.setPrinter(printer);
		print.setCreatedDate(new Date());
		
		return print;
	}

}
