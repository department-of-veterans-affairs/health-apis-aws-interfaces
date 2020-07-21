package gov.va.api.health.aws.interfaces.s3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** Test loading configuration from properties and forcefully testing a bad configuration. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = AmazonS3ClientConfigTest.TestConfiguration.class,
    initializers = ConfigFileApplicationContextInitializer.class)
public class AmazonS3ClientConfigTest {

  @Autowired private AmazonS3ClientConfig config;

  @Test(expected = IllegalArgumentException.class)
  public void testBadKeys() {
    config.setAccessKey(null);
    config.afterPropertiesSet();
  }

  /** Load test context with just the AmazonS3ClientConfig. */
  @EnableAutoConfiguration
  @EnableConfigurationProperties(value = {AmazonS3ClientConfig.class})
  public static class TestConfiguration {}
}
