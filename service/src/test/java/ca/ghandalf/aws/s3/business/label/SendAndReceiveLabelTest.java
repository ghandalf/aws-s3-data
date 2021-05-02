package ca.ghandalf.aws.s3.business.label;

import java.util.Date;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ca.ghandalf.aws.s3.BaseTest;
import ca.ghandalf.aws.s3.domain.FormType;
import ca.ghandalf.aws.s3.domain.Label;
import ca.ghandalf.aws.s3.domain.User;
import ca.ghandalf.aws.s3.domain.UserType;

public class SendAndReceiveLabelTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(SendAndReceiveLabelTest.class);

	@Inject
	private LabelSender sender;

	@Inject
	private LabelReceiver receiver;

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
			Label expected = build();

			logger.info("Sending this message: [{}}", expected.toString());

			Label actual = this.sender.send(expected);
			Assert.assertEquals(actual, expected);
		}
	}

	@Test(dependsOnMethods = { "sendMessage" })
	public void receiveMessage() {
		for (int i = 0; i < BaseTest.MAX_TO_RECEIVED; i++) {
			Label actual = receiver.receive();
			logger.info("Receiving this message: [{}]", actual.toString());
			Assert.assertNotNull(actual);
		}
	}

	private Label build() {

		Label label = new Label();

		User user = new User();
		user.setCompany("Fresche");
		user.setDistrict("Montreal");
		user.setName("fouellet");
		user.setUserType(UserType.System);

		label.getMergeData().put("tagnumber", "40B123733");
		label.getMergeData().put("ITCUST", "1012");
		label.getMergeData().put("ITCTRY", "USA");
		label.getMergeData().put("ITDES", "48X96 HPP PLATE");
		label.getMergeData().put("ITWDTH", "48.0");
		label.getMergeData().put("ITLNTH", "96.0");
		label.getMergeData().put("ITHEAT", "L7yh991");
		label.getMergeData().put("PONumber", "1049838");
		label.getMergeData().put("ITCLS3", "HPP");
		label.getMergeData().put("ITVNDR", "118");
		label.getMergeData().put("ITV303", "TEST COMMENT");
		label.getMergeData().put("ITVQUM", "LB");
		label.getMergeData().put("ITTQTY", "980");
		label.getMergeData().put("ITTPCS", "3");
		label.getMergeData().put("ITLOCT", "BAY123");

		label.setMergeData(label.getMergeData());
		label.setUser(user);
		label.setCopies(1);
		label.setDrawer(1);
		label.setDuplex(false);
		label.setHold(false);
		label.setLabelTemplate("XYZ");
		label.setSave(true);
		label.setPrinter("Printer1");
		label.setFormType(FormType.PDF);
		label.setUserData("ABC");
		label.setCreatedDate(new Date());

		return label;
	}

}