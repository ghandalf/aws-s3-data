package ca.ghandalf.aws.s3.business.printing;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

import ca.ghandalf.aws.s3.domain.Print;

@Service
public class PrintingReceiver {

	private static final Logger logger = LoggerFactory.getLogger(PrintingReceiver.class);

	@Inject
	private QueueMessagingTemplate queueMessagingTemplate;

	@Inject
	private String printingQueueName;

	public Print receive() {
		Print result = this.queueMessagingTemplate.receiveAndConvert(printingQueueName, Print.class);
		logger.info("\t\t\n\n [Reveiving from queue:{}, printing:{}]\n\n", printingQueueName, result);
		return result;
	}
}
