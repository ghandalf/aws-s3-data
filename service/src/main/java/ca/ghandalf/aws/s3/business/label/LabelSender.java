package ca.ghandalf.aws.s3.business.label;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;
import ca.ghandalf.aws.s3.domain.Label;;

@Service
public class LabelSender {

	private static final Logger logger = LoggerFactory.getLogger(LabelSender.class);
	
	@Inject 
	private QueueMessagingTemplate queueMessagingTemplate;
	
	@Inject 
	private String labelQueueName;

	public Label send(Label message) {
		logger.info("Sending an object Label... {}", message);
		this.queueMessagingTemplate.convertAndSend(labelQueueName, message);
		
		return message;
	}
}
