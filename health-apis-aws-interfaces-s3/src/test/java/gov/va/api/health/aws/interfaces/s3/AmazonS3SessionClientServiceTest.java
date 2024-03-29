package gov.va.api.health.aws.interfaces.s3;

import static org.junit.Assert.assertNotNull;

import gov.va.api.health.aws.interfaces.s3.mock.AmazonS3SessionClientServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/** Test a session client can be created via configuration properties. */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {AmazonS3SessionClientServiceTest.TestConfiguration.class},
    initializers = ConfigDataApplicationContextInitializer.class)
@TestPropertySource(
    locations = "classpath:application.yml",
    properties = {"amazon.s3.clientType: session", "amazon.s3.sessionDuration: 7200"})
public class AmazonS3SessionClientServiceTest {

  @Autowired private AmazonS3ClientConfig config;

  private AmazonS3SessionClientServiceMock service;

  @BeforeEach
  public void setUp() {
    service = new AmazonS3SessionClientServiceMock(config);
    service.afterPropertiesSet();
  }

  @Test
  public void testSessionS3Config() throws Exception {
    assertNotNull(service.s3Client());
  }

  /** Load test context with just the AmazonS3ClientConfig. */
  @EnableAutoConfiguration
  @EnableConfigurationProperties(value = {AmazonS3ClientConfig.class})
  public static class TestConfiguration {}
}
