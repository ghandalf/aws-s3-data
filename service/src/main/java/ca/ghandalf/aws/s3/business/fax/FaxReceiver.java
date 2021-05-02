package ca.ghandalf.aws.s3.business.fax;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import ca.ghandalf.aws.s3.domain.Fax;

@Service
public class FaxReceiver {

	private static final Logger logger = LoggerFactory.getLogger(FaxReceiver.class);

	@Inject
	private QueueMessagingTemplate queueMessagingTemplate;

	@Inject
	private String faxQueueName;

	public Fax receive() {
		Fax result = this.queueMessagingTemplate.receiveAndConvert(faxQueueName, Fax.class);
		logger.info("\t\t\n\n [Reveiving from queue:{}, fax:{}]\n\n", faxQueueName, result);
		return result;
	}
}
