package ca.ghandalf.aws.s3.business.simple;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SimpleReceiver {

	private static final Logger logger = LoggerFactory.getLogger(SimpleReceiver.class);

	@Inject
	private QueueMessagingTemplate queueMessagingTemplate;

	@Inject
	private String sqsQueueName;

	public String receive() {
		String result = this.queueMessagingTemplate.receiveAndConvert(sqsQueueName, String.class);
		logger.info("\t\t\n\n [Reveiving from queue:{}, string:{}]\n\n", sqsQueueName, result);
		return result;
	}

}
