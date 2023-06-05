package gov.va.api.health.aws.interfaces.s3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** Test a badly configured session client configuration. */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {AmazonS3SessionClientServiceTest.TestConfiguration.class},
    initializers = ConfigDataApplicationContextInitializer.class)
@TestPropertySource(
    locations = "classpath:application.yml",
    properties = {"amazon.s3.clientType: session", "amazon.s3.profileName: testprofile"})
public class AmazonS3SessionClientServiceBadConfigTest {

  @Autowired private AmazonS3ClientConfig config;

  @Test
  public void testBadSessionConfig() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          config.setClientRegion(null);
          AmazonS3SessionClientService service = new AmazonS3SessionClientService(config);
          service.afterPropertiesSet();
        });
  }

  /** Load test context with just the AmazonS3ClientConfig. */
  @EnableAutoConfiguration
  @EnableConfigurationProperties(value = {AmazonS3ClientConfig.class})
  public static class TestConfiguration {}
}
