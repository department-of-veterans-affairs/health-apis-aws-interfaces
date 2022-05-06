package gov.va.api.health.aws.interfaces.s3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** Forcefully test a badly configured simple client. */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {AmazonS3SimpleClientServiceBadConfigTest.TestConfiguration.class},
    initializers = ConfigDataApplicationContextInitializer.class)
public class AmazonS3SimpleClientServiceBadConfigTest {

  @Autowired private AmazonS3ClientConfig config;

  @Test
  public void testBadConfig() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          config.setServiceEndpoint(null);
          config.setClientRegion(null);
          AmazonS3SimpleClientService service = new AmazonS3SimpleClientService(config);
          service.afterPropertiesSet();
        });
  }

  /** Load test context with just the AmazonS3ClientConfig. */
  @EnableAutoConfiguration
  @EnableConfigurationProperties(value = {AmazonS3ClientConfig.class})
  public static class TestConfiguration {}
}
