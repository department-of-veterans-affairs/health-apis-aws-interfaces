package gov.va.api.health.aws.interfaces.s3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** Test a badly configured session client configuration. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {AmazonS3SessionClientServiceTest.TestConfiguration.class},
    initializers = ConfigFileApplicationContextInitializer.class)
@TestPropertySource(
    locations = "classpath:application.yml",
    properties = {"amazon.s3.clientType: session", "amazon.s3.profileName: testprofile"})
public class AmazonS3SessionClientServiceBadConfigTest {

  @Autowired private AmazonS3ClientConfig config;

  @Test(expected = IllegalArgumentException.class)
  public void testBadSessionConfig() {
    config.setClientRegion(null);
    AmazonS3SessionClientService service = new AmazonS3SessionClientService(config);
    service.afterPropertiesSet();
  }

  /** Load test context with just the AmazonS3ClientConfig. */
  @EnableAutoConfiguration
  @EnableConfigurationProperties(value = {AmazonS3ClientConfig.class})
  public static class TestConfiguration {}
}
