package ca.ghandalf.aws.s3.business.label;


import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;
import ca.ghandalf.aws.s3.domain.Label;;

@Service
public class LabelReceiver {

	private static final Logger logger = LoggerFactory.getLogger(LabelReceiver.class);

	@Inject
	private QueueMessagingTemplate queueMessagingTemplate;

	@Inject
	private String labelQueueName;

	public Label receive() {
		Label result = this.queueMessagingTemplate.receiveAndConvert(labelQueueName, Label.class);
		logger.info("\t\t\n\n [Reveiving from queue:{}, label:{}]\n\n", labelQueueName, result);
		return result;
	}
}