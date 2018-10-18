package ca.ghandalf.aws.s3.business.simple;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class SimpleSender {

	private static final Logger logger = LoggerFactory.getLogger(SimpleSender.class);
	
	@Inject private QueueMessagingTemplate queueMessagingTemplate;
	
	@Inject private String sqsQueueName;

	public String send(String message) {
		logger.info("\t\t\n\n Sending a simple string to {}...\n\n", sqsQueueName);
		this.queueMessagingTemplate.send(sqsQueueName, MessageBuilder.withPayload(message).build());
		
		return message;
	}

}
