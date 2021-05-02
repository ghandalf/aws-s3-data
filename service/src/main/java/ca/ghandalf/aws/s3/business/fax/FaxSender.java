package ca.ghandalf.aws.s3.business.fax;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;
import ca.ghandalf.aws.s3.domain.Fax;

@Service
public class FaxSender {

	private static final Logger logger = LoggerFactory.getLogger(FaxSender.class);
	
	@Inject private QueueMessagingTemplate queueMessagingTemplate;
	
	@Inject private String faxQueueName;

	public Fax send(Fax message) {
		logger.info("Sending an object Fax... {}", message);
		this.queueMessagingTemplate.convertAndSend(faxQueueName, message);
		
		return message;
	}
}
