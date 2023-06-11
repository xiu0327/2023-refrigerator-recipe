package refrigerator.back.authentication.application.port.in;

public interface IssueTemporaryAccessTokenUseCase {
    String issueTemporaryAccessToken(String email);
}
