package ca.ghandalf.aws.s3.business.printing;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import ca.ghandalf.aws.s3.domain.Print;

@Service
public class PrintingSender {

	private static final Logger logger = LoggerFactory.getLogger(PrintingSender.class);
	
	@Inject private QueueMessagingTemplate queueMessagingTemplate;
	
	@Inject private String printingQueueName;

	public Print send(Print message) {
		logger.info("Sending an object Print... {}", message);
		this.queueMessagingTemplate.convertAndSend(printingQueueName, message);
		
		return message;
	}
}
