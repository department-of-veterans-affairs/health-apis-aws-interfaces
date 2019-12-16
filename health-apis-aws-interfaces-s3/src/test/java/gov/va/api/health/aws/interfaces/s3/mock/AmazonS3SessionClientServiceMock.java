package gov.va.api.health.aws.interfaces.s3.mock;

import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import gov.va.api.health.aws.interfaces.s3.AmazonS3ClientConfig;
import gov.va.api.health.aws.interfaces.s3.AmazonS3SessionClientService;
import java.util.Calendar;
import java.util.Date;

/** Mock an Amazon Session client by returning a fake session token. */
public class AmazonS3SessionClientServiceMock extends AmazonS3SessionClientService {

  public AmazonS3SessionClientServiceMock(AmazonS3ClientConfig config) {
    super(config);
  }

  @Override
  protected GetSessionTokenResult getSessionToken(GetSessionTokenRequest getSessionTokenRequest) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(2000, 1, 2, 0, 0, 0);
    Date expireDate1 = calendar.getTime();
    String sessionId = "sessionId";
    Credentials credential1 = new Credentials("keyid", "key", sessionId, expireDate1);
    return new GetSessionTokenResult().withCredentials(credential1);
  }
}
