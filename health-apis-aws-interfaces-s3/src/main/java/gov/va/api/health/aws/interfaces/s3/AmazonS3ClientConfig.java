package gov.va.api.health.aws.interfaces.s3;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/** Configuration class to define an Amazon S3 Client. */
@Configuration
@ConfigurationProperties("amazon.s3")
@Data
public class AmazonS3ClientConfig implements InitializingBean {

  // Required.
  private String clientRegion;

  // Optional.
  private boolean pathStyleAccess = true;

  // Optional.
  private String profileName;

  // Optional.
  private String serviceEndpoint;

  // Optional.
  private String accessKey;

  // Optional.
  private String secretKey;

  // Optional.
  private Integer sessionDuration;

  @Override
  public void afterPropertiesSet() throws IllegalArgumentException {
    Assert.notNull(clientRegion, "AmazonS3ClientConfig clientRegion must not be null.");
    // If configured to use credentials then both accessKey and secretKey must be specified.
    if (accessKey == null ^ secretKey == null) {
      throw new IllegalArgumentException(
          "AmazonS3ClientConfig has invalid credential configuration.");
    }
  }
}
