package ca.ghandalf.aws.s3.business.fax;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ca.ghandalf.aws.s3.BaseTest;
import ca.ghandalf.aws.s3.domain.Fax;
import ca.ghandalf.aws.s3.domain.User;
import ca.ghandalf.aws.s3.domain.UserType;

public class SendAndReceiveFaxTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(SendAndReceiveFaxTest.class);

	@Inject
	private FaxSender sender;
	
	@Inject
	private FaxReceiver receiver;	
	
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
			Fax expected = build("514-756-123" + i, "Fresche Fax .. " + 1 + " ..");
	
			logger.info("Sending this message: [{}}", expected.toString());
			
			Fax actual = this.sender.send(expected);
			Assert.assertEquals(actual, expected);
		}
	}

	@Test(dependsOnMethods = { "sendMessage" })
	public void receiveMessage() {
		for (int i = 0; i < BaseTest.MAX_TO_RECEIVED; i++) {
			Fax actual = receiver.receive();
			logger.info("Receiving this message: [{}]", actual.toString());
			Assert.assertNotNull(actual);
		}
	}
	
	private Fax build(String contact, String coverpage) {
		Fax fax = new Fax();
		
		List<String> to = new ArrayList<>();
		to.add(contact);
		
		User user = new User();
		user.setCompany("Fresche");
		user.setDistrict("Montreal");
		user.setName("fouellet");
		user.setUserType(UserType.System);
		
		fax.setUser(user);
		fax.setTo(to);
		fax.setCoverpage(coverpage);
		fax.setCreatedDate(new Date());
		
		return fax;
	}

}
