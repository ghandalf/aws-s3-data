package ca.ghandalf.aws.s3.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.context.config.annotation.EnableContextInstanceData;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;

@Configuration
@EnableJms
@EnableDiscoveryClient
@EnableContextInstanceData
@PropertySource("classpath:sqs.properties")
public class ApplicationConfig {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

  static {
    MDC.remove("thread");
    MDC.put("thread", ApplicationConfig.class.getSimpleName());
  }

  @Value("${sqs.access.key}") private String accessKey;
  @Value("${sqs.secret.key}") private String secretKey;
  @Value("${sqs.queue.region}") private String currentRegion;
  @Value("${sqs.queue.name}") private String sqsQueueName;
  @Value("${email.queue.name}") private String emailQueueName;
  @Value("${printing.queue.name}") private String printingQueueName;
  @Value("${fax.queue.name}") private String faxQueueName;
  @Value("${label.queue.name}") private String labelQueueName;

  @Value("${s3.user.access.key}") private String s3accessKey;
  @Value("${s3.user.secret.key}") private String s3secretKey;

  @Value("${s3.rmi.email.bucket}") private String emailBucket;
  @Value("${s3.rmi.report.bucket}") private String reportBucket;

  @Bean
  public Regions getRegions() {
    return Regions.fromName(currentRegion);
  }
  
  @Bean
  public String labelQueueName() {
	  return this.labelQueueName;
  }

  @Bean
  public String sqsQueueName() {
    return this.sqsQueueName;
  }

  @Bean
  public String emailQueueName() {
    return this.emailQueueName;
  }

  @Bean
  public String printingQueueName() {
    return this.printingQueueName;
  }

  @Bean
  public String faxQueueName() {
    return this.faxQueueName;
  }

  @Bean
  public String s3UserAccessKey() {
    return this.s3accessKey;
  }

  @Bean
  public String s3UserSecretKey() {
    return this.s3secretKey;
  }

  @Bean
  public String emailBucket() {
    return this.emailBucket;
  }

  @Bean
  public String reportBucket() {
    return this.reportBucket;
  }

  @Bean
  public ClientConfiguration clientConfiguration() {
    logger.info("Creation of amazonaws ClientConfiguration bean ...");

    return new ClientConfiguration();
  }

  @Bean
  public AWSCredentialsProvider sqsCredential() {
    logger.info("Creation of amazonaws sqs credential on queue name: {}, and region: {}", sqsQueueName, currentRegion);

    AWSCredentialsProvider credentialProvider;
    if (accessKey != null && secretKey != null) {

      AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
      credentialProvider = new AWSStaticCredentialsProvider(credentials);

      logger.info("Creation of sqs credential done with accessKey: {}",
          credentialProvider.getCredentials().getAWSAccessKeyId());

    } else {
      logger.info("Creation of default credential when the accessKey and secretKey are empty or null");
      credentialProvider = new DefaultAWSCredentialsProviderChain();

      logger.info("Creation of default credential done with accessKey: {}",
          credentialProvider.getCredentials().getAWSAccessKeyId());
    }

    return credentialProvider;
  }

  @Bean
  public AWSCredentialsProvider s3Credential() {
    logger.info("Creation of amazonaws s3 credential on bucket name: {}", reportBucket);

    AWSCredentialsProvider credentialProvider;
    if (s3accessKey != null && s3secretKey != null) {
      AWSCredentials credentials = new BasicAWSCredentials(s3accessKey, s3secretKey);
      credentialProvider = new AWSStaticCredentialsProvider(credentials);

      logger.info("Creation of s3 credential done with accessKey: {}",
          credentialProvider.getCredentials().getAWSAccessKeyId());

    } else {
      logger.info("Creation of default credential when the s3accessKey and s3secretKey are empty or null");

      credentialProvider = new DefaultAWSCredentialsProviderChain();

      logger.info("Creation of default credential done with accessKey: {}",
          credentialProvider.getCredentials().getAWSAccessKeyId());
    }

    return credentialProvider;
  }

  @Bean
  public AmazonSQSAsync amazonSqs() {
    logger.info("Creation of amazonaws AmazonSQSAsync bean ...");

    return AmazonSQSAsyncClient.asyncBuilder().withRegion(getRegions()).withClientConfiguration(clientConfiguration())
        .withCredentials(sqsCredential()).build();
  }

  @Bean
  public AmazonS3 amazonS3() {
    logger.info("Creation of amazonaws AmazonS3 bean ...");

    return AmazonS3ClientBuilder.standard().withRegion(getRegions()).withClientConfiguration(clientConfiguration())
        .withCredentials(s3Credential()).build();
  }

  @Bean
  public QueueMessagingTemplate queueMessagingTemplate() {
    logger.info("Creation of spring QueueMessagingTemplate bean ...");

    return new QueueMessagingTemplate(amazonSqs());
  }

}
