package gov.va.api.health.aws.interfaces.s3.junit;

import static org.junit.Assert.assertEquals;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;

/** Utilities to facilitate testing the mocked Amazon S3 interface. */
@UtilityClass
public final class AmazonS3BucketUtilities {

  /**
   * Obtain a string of a result from the mock Amazon S3. Note that only one result is expected per
   * test as the object is deleted after reading.
   *
   * @return String containing the result.
   */
  @SneakyThrows
  public static String getResultFromS3(final AmazonS3 s3Client, final String bucketName) {
    final ListObjectsV2Result result = s3Client.listObjectsV2(bucketName);
    final List<S3ObjectSummary> objects = result.getObjectSummaries();
    assertEquals(1, objects.size());
    final String objectKey = objects.get(0).getKey();
    S3Object obj = s3Client.getObject(bucketName, objectKey);
    final String resultString = IOUtils.toString(obj.getObjectContent(), StandardCharsets.UTF_8);
    obj.close();
    s3Client.deleteObject(bucketName, objectKey);
    return resultString;
  }
}
