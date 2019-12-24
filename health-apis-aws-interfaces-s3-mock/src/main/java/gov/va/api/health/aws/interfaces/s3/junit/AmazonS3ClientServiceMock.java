package gov.va.api.health.aws.interfaces.s3.junit;

import com.amazonaws.services.s3.AmazonS3;
import gov.va.api.health.aws.interfaces.s3.AmazonS3ClientServiceInterface;

/** Mock service to encapsulate the mocked Amazon S3 Client. */
public class AmazonS3ClientServiceMock implements AmazonS3ClientServiceInterface {

  private final AmazonS3 s3Client;

  public AmazonS3ClientServiceMock(AmazonS3 s3Client) {
    this.s3Client = s3Client;
  }

  public AmazonS3 s3Client() {
    return s3Client;
  }
}
