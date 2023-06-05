package gov.va.api.health.aws.interfaces.s3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Test a nominal simple client is instantiated using the high level client service config class.
 */
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@ContextConfiguration(
    classes = {AmazonS3ClientServiceConfig.class},
    initializers = ConfigDataApplicationContextInitializer.class)
public class AmazonS3ClientServiceConfigTest {

  @Autowired private AmazonS3ClientServiceInterface service;

  @Test
  public void testServiceInstantiatedViaConfiguration() {
    Assertions.assertTrue(service instanceof AmazonS3SimpleClientService);
  }
}
