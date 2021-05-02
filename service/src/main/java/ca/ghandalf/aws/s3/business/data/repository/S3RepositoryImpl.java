package ca.ghandalf.aws.s3.business.data.repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Repository
public class S3RepositoryImpl implements S3Repository {

  private static final Logger logger = LoggerFactory.getLogger(S3RepositoryImpl.class);

  static {
    MDC.remove("thread");
    MDC.put("thread", S3RepositoryImpl.class.getSimpleName());
  }
  
  @Inject
  private AmazonS3 amazonS3;

  @Inject
  private String reportBucket;

  @Override
  public boolean save(String filename, File file) throws IOException {
    logger.info("Current filename as a key:{}, to save", filename);

    FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false,
        file.getName(), (int) file.length(), file.getParentFile());

    // Lets use Try-catch-resource since java 7
    try (InputStream input = new FileInputStream(file)) {
      OutputStream os = fileItem.getOutputStream();
      IOUtils.copy(input, os);

      MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

      if (!multipartFile.isEmpty()) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentEncoding(StandardCharsets.UTF_8.name());
        objectMetadata.addUserMetadata("id", UUID.randomUUID().toString());

        PutObjectRequest request = new PutObjectRequest(reportBucket, filename,
            multipartFile.getInputStream(), objectMetadata);

        this.amazonS3.putObject(request);

        return true;

      } else {
        logger.info("Empty multipart file: {}, no need to upload", multipartFile.getName());
      }
    }
    return false;
  }

  @Override
  public OutputStream load(String filename) throws IOException {
    
    logger.info("Current filename as a key:{}, to load", filename);
    
    GetObjectRequest request = new GetObjectRequest(reportBucket, filename);
    S3Object value = this.amazonS3.getObject(request);

    OutputStream output = new ByteArrayOutputStream();
    IOUtils.copy(value.getObjectContent(), output);

    return output;
  }

  @Override
  public void update(String filename, File file) throws IOException {
    logger.info("Current filename as a key:{}, to update", filename);
    this.save(filename, file);
  }

  /*@Override
  public void delete(String filename) throws IOException {
    logger.info("Current filename as a key:{}, to delete", filename);
    this.amazonS3.deleteObject(reportBucket, filename);
  }*/

  @Override
  public List<String> findKeys(int nbKeys) {
    List<String> values = new ArrayList<>();
    final ListObjectsV2Request request =
        new ListObjectsV2Request().withBucketName(reportBucket).withMaxKeys(nbKeys);

    logger.info("Retrievve those numbers of keys (filename) from current bucket: {}", nbKeys, reportBucket);

    ListObjectsV2Result result;

    do {
      result = amazonS3.listObjectsV2(request);
      
      logger.info("Result maxKeys: {}, truncated: {}, keyCount: {}", result.getMaxKeys(),
          result.isTruncated(), result.getKeyCount());

      for (S3ObjectSummary value : result.getObjectSummaries()) {
        logger.info("Key: {}", value.getKey());
        values.add(value.getKey());
      }
      
      result.setNextContinuationToken(result.getNextContinuationToken());

    } while (!result.isTruncated());
    
    logger.info("Result maxKeys: {}, truncated: {}", result.getMaxKeys(), result.isTruncated());
    
    return values;
  }

}
