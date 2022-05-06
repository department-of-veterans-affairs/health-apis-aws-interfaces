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

/** Test a simple client can be instantiated without an endpoint. */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {AmazonS3SimpleClientServiceTest.TestConfiguration.class},
    initializers = ConfigDataApplicationContextInitializer.class)
public class AmazonS3SimpleClientServiceTest {

  @Autowired private AmazonS3ClientConfig config;

  @Test
  public void testNoEndpointConfig() {
    config.setServiceEndpoint(null);
    AmazonS3SimpleClientService testService = new AmazonS3SimpleClientService(config);
    testService.afterPropertiesSet();
    Assertions.assertNotNull(testService.s3Client());
  }

  /** Load test context with just the AmazonS3ClientConfig. */
  @EnableAutoConfiguration
  @EnableConfigurationProperties(value = {AmazonS3ClientConfig.class})
  public static class TestConfiguration {}
}
