package ca.ghandalf.aws.s3;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import ca.ghandalf.aws.s3.business.data.repository.S3RepositoryImpl;
import ca.ghandalf.aws.s3.business.email.EmailReceiver;
import ca.ghandalf.aws.s3.business.email.EmailSender;
import ca.ghandalf.aws.s3.business.fax.FaxReceiver;
import ca.ghandalf.aws.s3.business.fax.FaxSender;
import ca.ghandalf.aws.s3.business.label.LabelReceiver;
import ca.ghandalf.aws.s3.business.label.LabelSender;
import ca.ghandalf.aws.s3.business.printing.PrintingReceiver;
import ca.ghandalf.aws.s3.business.printing.PrintingSender;
import ca.ghandalf.aws.s3.business.simple.SimpleReceiver;
import ca.ghandalf.aws.s3.business.simple.SimpleSender;
import ca.ghandalf.aws.s3.config.ApplicationConfig;

@ContextConfiguration(classes = {ApplicationConfig.class, 
        EmailSender.class, EmailReceiver.class, 
		FaxSender.class, FaxReceiver.class, LabelSender.class, LabelReceiver.class, PrintingSender.class, PrintingReceiver.class,
		SimpleSender.class, SimpleReceiver.class,
		S3RepositoryImpl.class})
@ComponentScan("ca.ghandalf.aws.s3")
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

	public static final int MAX_TO_SEND = 4;
	public static final int MAX_TO_RECEIVED = MAX_TO_SEND - 1;
}
