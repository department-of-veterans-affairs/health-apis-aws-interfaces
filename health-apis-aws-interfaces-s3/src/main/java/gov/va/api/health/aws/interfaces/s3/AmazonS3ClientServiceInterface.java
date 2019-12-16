package gov.va.api.health.aws.interfaces.s3;

import com.amazonaws.services.s3.AmazonS3;

/** Interface to wrap an Amazon S3 Client. */
public interface AmazonS3ClientServiceInterface {

  /**
   * Obtain an S3 Client instance.
   *
   * @return AmazonS3 Client.
   * @throws Exception Implementation specific exception that may occur when obtaining the S3Client
   *     instance.
   */
  public AmazonS3 s3Client() throws Exception;
}
