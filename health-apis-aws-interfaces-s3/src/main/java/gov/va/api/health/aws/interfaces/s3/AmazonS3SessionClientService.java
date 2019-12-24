package gov.va.api.health.aws.interfaces.s3;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
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
 * Service class to wrap a session enabled Amazon S3 Client. Uses configuration class from
 * application properties to instantiate an Amazon S3 Client.
 */
@Service
@ConditionalOnProperty(value = "amazon.s3.clientType", havingValue = "session")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class AmazonS3SessionClientService
    implements AmazonS3ClientServiceInterface, InitializingBean {

  private static final Logger LOG = LoggerFactory.getLogger(AmazonS3SessionClientService.class);

  private final AmazonS3ClientConfig config;

  private AWSCredentialsProvider awsCredentialsProvider;

  private AWSSecurityTokenService stsClient;

  @Override
  public void afterPropertiesSet() throws IllegalArgumentException {
    try {
      // Use optional access credentials if specified.
      if ((config.getAccessKey() != null) && (config.getSecretKey() != null)) {
        awsCredentialsProvider =
            new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey()));
      } else {
        // Otherwise, attempt to use a profile (either specified or the default).
        awsCredentialsProvider =
            (config.getProfileName() != null)
                ? new ProfileCredentialsProvider(config.getProfileName())
                : new ProfileCredentialsProvider();
      }
      // Creating the STS client is part of your trusted code. It has
      // the security credentials you use to obtain temporary security credentials.
      stsClient =
          AWSSecurityTokenServiceClientBuilder.standard()
              .withCredentials(awsCredentialsProvider)
              .withRegion(config.getClientRegion())
              .build();
    } catch (Exception e) {
      log.error("AmazonS3SessionClientService failed configuration: {}", e.getMessage());
    }
    Assert.notNull(
        awsCredentialsProvider,
        "AmazonS3SessionClientService failed creation of awsCredentialsProvider.");
    Assert.notNull(stsClient, "AmazonS3SessionClientService failed creation of stsClient.");

    LOG.info("Using AmazonS3SessionClientService for Amazon S3 Interface.");
  }

  /**
   * Get session token.
   *
   * @param getSessionTokenRequest Session Token Request.
   * @return Session Token Result.
   */
  protected GetSessionTokenResult getSessionToken(GetSessionTokenRequest getSessionTokenRequest) {
    // credentials are requested by an IAM user rather than an account owner.
    return stsClient.getSessionToken(getSessionTokenRequest);
  }

  /**
   * Create an S3 Client that has an associated session duration.
   *
   * @return AmazonS3 Client.
   */
  public AmazonS3 s3Client() throws Exception {
    // Start a session.
    GetSessionTokenRequest sessionTokenRequest = new GetSessionTokenRequest();
    if (config.getSessionDuration() != null) {
      sessionTokenRequest.withDurationSeconds(config.getSessionDuration());
    }
    // The duration can be set to more than 3600 seconds only if temporary
    // credentials are requested by an IAM user rather than an account owner.
    final GetSessionTokenResult sessionTokenResult = getSessionToken(sessionTokenRequest);
    // Package the temporary security credentials as a BasicSessionCredentials object
    // for an Amazon S3 client object to use.
    final Credentials sessionCredentials = sessionTokenResult.getCredentials();
    final BasicSessionCredentials basicSessionCredentials =
        new BasicSessionCredentials(
            sessionCredentials.getAccessKeyId(),
            sessionCredentials.getSecretAccessKey(),
            sessionCredentials.getSessionToken());
    // Using the basicSessionCredentials object.
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials))
        .withRegion(config.getClientRegion())
        .build();
  }
}
