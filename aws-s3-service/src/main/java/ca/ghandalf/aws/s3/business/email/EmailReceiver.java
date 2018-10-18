package ca.ghandalf.aws.s3.business.email;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import ca.ghandalf.aws.s3.domain.Email;

@Service
public class EmailReceiver {

	private static final Logger logger = LoggerFactory.getLogger(EmailReceiver.class);

	@Inject
	private QueueMessagingTemplate queueMessagingTemplate;

	@Inject
	private String emailQueueName;

	public Email receive() {
		Email result = this.queueMessagingTemplate.receiveAndConvert(emailQueueName, Email.class);
		logger.info("\t\t\n\n [Reveiving from queue:{}, email:{}]\n\n", emailQueueName, result);
		return result;
	}
}
