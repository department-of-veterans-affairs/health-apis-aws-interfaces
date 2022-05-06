package gov.va.api.health.aws.interfaces.s3;

import static org.junit.Assert.assertNotNull;

import gov.va.api.health.aws.interfaces.s3.mock.AmazonS3SessionClientServiceMock;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** Test a nominally configured session client configuration using profiles. */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {AmazonS3SessionClientServiceTest.TestConfiguration.class},
    initializers = ConfigDataApplicationContextInitializer.class)
@TestPropertySource(
    locations = "classpath:application.yml",
    properties = {"amazon.s3.clientType: session"})
public class AmazonS3SessionClientServiceProfileTest {

  @Autowired private AmazonS3ClientConfig config;

  @Test
  @SneakyThrows
  public void testSessionConfigCustomProfile() {
    config.setAccessKey(null);
    config.setSecretKey(null);
    config.setProfileName("testprofile");
    AmazonS3SessionClientService service = new AmazonS3SessionClientServiceMock(config);
    service.afterPropertiesSet();
    assertNotNull(service.s3Client());
  }

  @Test
  @SneakyThrows
  public void testSessionConfigDefaultProfile() {
    config.setAccessKey(null);
    config.setSecretKey(null);
    config.setProfileName(null);
    AmazonS3SessionClientService service = new AmazonS3SessionClientServiceMock(config);
    service.afterPropertiesSet();
    assertNotNull(service.s3Client());
  }

  /** Load test context with just the AmazonS3ClientConfig. */
  @EnableAutoConfiguration
  @EnableConfigurationProperties(value = {AmazonS3ClientConfig.class})
  public static class TestConfiguration {}
}
