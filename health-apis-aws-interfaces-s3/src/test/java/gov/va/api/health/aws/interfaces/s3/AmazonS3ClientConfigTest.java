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

/** Test loading configuration from properties and forcefully testing a bad configuration. */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = AmazonS3ClientConfigTest.TestConfiguration.class,
    initializers = ConfigDataApplicationContextInitializer.class)
public class AmazonS3ClientConfigTest {

  @Autowired private AmazonS3ClientConfig config;

  @Test
  public void testBadKeys() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          config.setAccessKey(null);
          config.afterPropertiesSet();
        });
  }

  /** Load test context with just the AmazonS3ClientConfig. */
  @EnableAutoConfiguration
  @EnableConfigurationProperties(value = {AmazonS3ClientConfig.class})
  public static class TestConfiguration {}
}
