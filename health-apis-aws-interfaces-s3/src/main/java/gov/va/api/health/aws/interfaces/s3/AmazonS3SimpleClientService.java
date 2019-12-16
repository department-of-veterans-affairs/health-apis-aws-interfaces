package gov.va.api.health.aws.interfaces.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Service class to wrap a simple Amazon S3 Client. Uses configuration class from application
 * properties to instantiate an Amazon S3 Client. This simple client is instantiated by default.
 */
@Service
@ConditionalOnProperty(
  value = "amazon.s3.clientType",
  matchIfMissing = true,
  havingValue = "simple"
)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class AmazonS3SimpleClientService
    implements AmazonS3ClientServiceInterface, InitializingBean {

  private static final Logger LOG = LoggerFactory.getLogger(AmazonS3SimpleClientService.class);

  private final AmazonS3ClientConfig config;

  private AmazonS3 amazonS3;

  @Override
  public void afterPropertiesSet() throws IllegalArgumentException {
    try {
      AmazonS3ClientBuilder builder =
          AmazonS3ClientBuilder.standard().withPathStyleAccessEnabled(config.isPathStyleAccess());
      // Use optional service endpoint if specified.
      if (config.getServiceEndpoint() != null) {
        builder.withEndpointConfiguration(
            new EndpointConfiguration(config.getServiceEndpoint(), config.getClientRegion()));
      } else {
        builder.withRegion(config.getClientRegion());
      }
      // Use optional access credentials if specified.
      if ((config.getAccessKey() != null) && (config.getSecretKey() != null)) {
        builder.withCredentials(
            new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey())));
      }
      amazonS3 = builder.build();
    } catch (Exception e) {
      log.error("AmazonS3SimpleClientService failed configuration: {}", e.getMessage());
    }
    Assert.notNull(amazonS3, "AmazonS3SimpleClientService failed instantiation.");

    LOG.info("Using AmazonS3SimpleClientService for Amazon S3 Interface.");
  }

  /**
   * Create a simple S3 Client or simply return existing client instance if already constructed.
   *
   * @return AmazonS3 Client.
   */
  public AmazonS3 s3Client() {
    return amazonS3;
  }
}
