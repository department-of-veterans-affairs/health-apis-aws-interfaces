# health-apis-aws-interfaces-s3

Configuration class to connect to Amazon S3.  Supports simple or session based client.  Client service is instantiated conditionally based on the property value `amazon.s3.client.type` which by default is `simple`.

### Amazon S3 Client Config Usage

1. Application must import the configuration class.
   
   ```
   @SpringBootApplication
   @Import({
     AmazonS3ClientServiceConfig.class
   })
   public class MyApplication {
     public static void main(String[] args) {
       SpringApplication.run(MyApplication.class, args);
     }
   }
   ```

2. Application must provide appropriate properties in application.yml:  

   The `clientRegion` property is required at a minimum. 

* Example of a simple S3 Client configuration:
   ```
   amazon.s3:
     clientRegion: "us-east-1"
     serviceEndpoint: "http://localhost:9090"
     accessKey: foo
     secretKey: bar
   ```
* Example of a session S3 Client configuration:
   ```
   amazon.s3:
     clientRegion: "us-east-1"
     profileName: "default"
     sessionDuration: 7200
     clientType: "session"
   ```
   NOTES: additional explanation for each property as follows:  

   * clientRegion - (Required for Any) Amazon S3 Client Region.
   * serviceEndpoint - (Simple) Not required unless needed for mocking Amazon S3 or specifying endpoint for some specific purpose.
   * pathStyleAccess - (Simple) Default true. Configures the client to use path-style access for all requests.
   * accessKey - (Any) Optional, but if specified must also specify secretKey.
   * secretKey - (Any) Optional, but if specified must also specify accessKey.
   * clientType - (Any) Optional, `simple` client will be instantiated if not specified.  
     Supported values are:
     * `simple`: To use a simple client.
     * `session`: To use a session client.
   * profileName - (Session) Default value is `default`.
   * sessionDuration - (Session) Optional, seconds that the credentials should remain valid.


3. A bean of type AmazonS3 can then be Autowired within the application.  
   For example:  
   `private final AmazonS3ClientServiceInterface s3ClientService;`

4. The session can be acquired by calling the method `s3Client()`.  This method may throw an Exception depending on client or session type client that the caller should handle.  
   ```
   try {
     AmazonS3 s3Client = s3ClientService.s3Client();
   } catch (Exception e) {
     // Handle exception.
   }
   ```
