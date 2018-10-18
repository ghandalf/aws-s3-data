package ca.ghandalf.aws.s3.business.data.repository;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ca.ghandalf.aws.s3.BaseTest;

public class S3RepositoryTest extends BaseTest {

  private static final Logger logger = LoggerFactory.getLogger(S3RepositoryTest.class);

  private final String txtFile = "test_01.txt";
  private final String pngFile = "Explorer.png";

  @Inject
  private S3Repository repository;
  private String[] directories = {"jason", "ajeet", "sales", "procurement"};

  @BeforeClass
  public void setUp() {
    MDC.remove("thread");
    MDC.put("thread", this.getClass().getSimpleName());
    Assert.assertNotNull(this.repository);
    logger.info("\t\t\n\n Set Up repository {}\n\n", this.repository.getClass().getName());
  }

  @Test
  public void save() throws IOException {
    logger.info("In save method...");

    for (String directory : directories) {
      logger.info("directory:{}", directory);

      File file = new File("src/test/resources/report/" + txtFile);
      logger.info("Save will upload the directory/file {}/{} to S3 Amazon", directory,
          file.getName());

      Assert.assertTrue(this.repository.save(directory + "/" + txtFile, file));

      file = new File("src/test/resources/report/" + pngFile);
      logger.info("Save will upload the directory/file {}/{} to S3 Amazon", directory,
          file.getName());

      Assert.assertTrue(this.repository.save(directory + "/" + pngFile, file));
    }
  }

  /**
   * Due to Amazon slow writing process in the save() method above, we need to set a timeout before
   * we call the load() method. In this case 12 seconds
   * 
   * @throws IOException
   */
  @Test(dependsOnMethods = {"save"})
  public void load() throws IOException {
    logger.info("In load method...");

    for (String directory : directories) {

      logger.info("Looking in the Amazon S3 directory/file: {}/{}", directory, txtFile);

      OutputStream actual = this.repository.load(directory + "/" + txtFile);
      Assert.assertNotNull(actual);

      actual = this.repository.load(directory + "/" + pngFile);
      Assert.assertNotNull(actual);
    }
  }

  @Test(dependsOnMethods = "load")
  public void update() throws IOException {
    logger.info("In update method...");

    for (String directory : directories) {
      logger.info("directory:{}", directory);

      File file = new File("src/test/resources/report/" + txtFile);
      logger.info("Update will upload the directory/file {}/{} to S3 Amazon", directory,
          file.getName());

      Assert.assertTrue(this.repository.save(directory + "/" + txtFile, file));
    }

  }

  /**
   * Utility method, the test is in deleteExpectedAmazonS3Exception below.
   * @throws IOException
   */
  @Test(dependsOnMethods = "update")
  public void delete() throws IOException {
    logger.info("In delete method... we will erease all data from S3");

    for (String directory : directories) {
      logger.info("directory:{}", directory);

      /*logger.info("Deleting filename [directory/file]: [{}] from S3 Amazon",
          directory + "/" + txtFile);
      this.repository.delete(directory + "/" + txtFile);

      logger.info("Deleting filename [directory/file]: [{}] from S3 Amazon",
          directory + "/" + pngFile);
      this.repository.delete(directory + "/" + pngFile);*/
    }
  }
  
  /*@Test(dependsOnMethods = "delete", expectedExceptions = AmazonS3Exception.class)
  public void deleteExpectedAmazonS3Exception() throws IOException {
    try (OutputStream actual = this.repository.load(directories[0] + "/" + txtFile)) {
      Assert.assertNull(actual);
    }
  }
*/
  /**
   * Retrieve the keys in the bucket is not trivial
   * We need to look at the amazon sdk implementation to understand the behavior of the method called.
   */
  public void findKeys() {
    logger.info("In findKeys method...");

    // We had saved 8 elements in the bucket (4 directories with two files in each), the keys
    // available are 8
    // FIXME (FO) I don't like this AWS approach it is errors prone, they didn't give us a way to
    // extract what ever happen the numbers of keys
    // To fix this problem we will need to subscribe to amazon as a developer and fix it, or open a
    // ticket.
    int nbKeys = 8;

    logger.info("Retrieving from Amazon S3 this number of keys: {}", nbKeys);

    List<String> actual = this.repository.findKeys(nbKeys);

    Assert.assertFalse(actual.isEmpty());
  }

}
