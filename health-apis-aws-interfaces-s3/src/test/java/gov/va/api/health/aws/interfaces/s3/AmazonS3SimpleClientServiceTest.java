package gov.va.api.health.aws.interfaces.s3;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** Test a simple client can be instantiated without an endpoint. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {AmazonS3SimpleClientServiceTest.TestConfiguration.class},
    initializers = ConfigFileApplicationContextInitializer.class)
public class AmazonS3SimpleClientServiceTest {

  @Autowired private AmazonS3ClientConfig config;

  @Test
  public void testNoEndpointConfig() {
    config.setServiceEndpoint(null);
    AmazonS3SimpleClientService testService = new AmazonS3SimpleClientService(config);
    testService.afterPropertiesSet();
    assertNotNull(testService.s3Client());
  }

  /** Load test context with just the AmazonS3ClientConfig. */
  @EnableAutoConfiguration
  @EnableConfigurationProperties(value = {AmazonS3ClientConfig.class})
  public static class TestConfiguration {}
}
