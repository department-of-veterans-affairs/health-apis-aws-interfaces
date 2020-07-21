package gov.va.api.health.aws.interfaces.s3;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test a nominal simple client is instantiated using the high level client service config class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@ContextConfiguration(
    classes = {AmazonS3ClientServiceConfig.class},
    initializers = ConfigFileApplicationContextInitializer.class)
public class AmazonS3ClientServiceConfigTest {

  @Autowired private AmazonS3ClientServiceInterface service;

  @Test
  public void testServiceInstantiatedViaConfiguration() {
    Assert.assertTrue(service instanceof AmazonS3SimpleClientService);
  }
}
