package ca.ghandalf.aws.s3.business.email;

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
import ca.ghandalf.aws.s3.domain.Email;
import ca.ghandalf.aws.s3.domain.User;
import ca.ghandalf.aws.s3.domain.UserType;

public class SendAndReceiveEmailTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(SendAndReceiveEmailTest.class);

	@Inject
	private EmailSender sender;
	
	@Inject
	private EmailReceiver receiver;	
	
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
			Email expected = build("email_0" + i + "@freschesolutions.com", "Email salut .. " + 1 + " ..");
	
			logger.info("Sending this message: [{}]", expected.toString());
			
			Email actual = this.sender.send(expected);
			Assert.assertEquals(actual, expected);
		}
	}

	@Test(dependsOnMethods = { "sendMessage" })
	public void receiveMessage() {
		for (int i = 0; i < BaseTest.MAX_TO_RECEIVED; i++) {
			Email actual = receiver.receive();
			logger.info("Receiving this message: [{}]", actual.toString());
			Assert.assertNotNull(actual);
		}
	}
	
	private Email build(String contact, String message) {
		Email email = new Email();
		
		List<String> to = new ArrayList<>();
		to.add(contact);
		
		User user = new User();
		user.setCompany("Fresche");
		user.setDistrict("Montreal");
		user.setName("fouellet");
		user.setUserType(UserType.System);
		
		email.setUser(user);
		email.setTo(to);
		email.setMessage(message);
		email.setCreatedDate(new Date());
		email.setOverlayFile("overlayFile");
		email.setCreatedDate(new Date());
		
		return email;
	}

}
