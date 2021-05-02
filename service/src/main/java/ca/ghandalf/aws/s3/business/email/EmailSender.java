package ca.ghandalf.aws.s3.business.email;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import ca.ghandalf.aws.s3.domain.Email;

@Service
public class EmailSender {

	private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
	
	@Inject private QueueMessagingTemplate queueMessagingTemplate;
	
	@Inject private String emailQueueName;

	public Email send(Email message) {
		logger.info("Sending an object Email... {}", message);
		this.queueMessagingTemplate.convertAndSend(emailQueueName, message);
		
		return message;
	}
}
