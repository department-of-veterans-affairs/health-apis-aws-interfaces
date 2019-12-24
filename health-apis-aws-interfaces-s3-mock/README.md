# health-apis-aws-interfaces-s3-mock

Mock Amazon S3 Client implementation and utilities to facilitate unit/integration tests.

* AmazonS3BucketUtilities - Utilities to faciliate S3 bucket testing.
* AmazonS3ClientServiceMock - Mock implementation of AmazonS3ClientServiceInterface.

### Amazon S3 Client Mock Usage

The Mock Amazon S3 Client implementation can be used in a unit test that uses an S3MockRule to obtain an AmazonS3 instance.

For example, in a unit test declare as follows:

```
  /** Class rule for Mock Amazon S3. */
  @ClassRule
  public static final S3MockRule S3_MOCK_RULE =
      S3MockRule.builder().silent().withSecureConnection(false).build();

  /** Mock Amazon S3 Client. */
  private AmazonS3 s3Client = S3_MOCK_RULE.createS3Client();

  /** Example of an implementation specific service that uses an s3Client. */
  @Autowired private AmazonS3ClientWriterService amazonS3ClientWriterService;

  @Before
  public void init() {
    amazonS3ClientWriterService.setS3ClientService(new AmazonS3ClientServiceMock(s3Client));
  }
```
