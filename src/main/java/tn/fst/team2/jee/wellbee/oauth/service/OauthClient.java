package tn.fst.team2.jee.wellbee.oauth.service;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OauthClient {

    @Value("${oauth.token-request-url}")
    public static String TOKEN_REQUEST_URL;

    @Value("${oauth.authorization-url}")

    public static String Authorization_URL;

    @Value("${oauth.client-id}")
    public static String CLIENT_ID;

    @Value("${oauth.client-secret}")
    public static String CLIENT_SECRET;

    @Value("${oauth.resource-uri}")
    public static String RESSOURCE_URI;

    @Value("${oauth.redirect-uri}")
    public static String REDIRECT_URI;

    @Value("${oauth.scopes}")
    public static String SCOPES;

    public String createAuthorizationUrl() throws OAuthSystemException {
        OAuthClientRequest request= OAuthClientRequest
                .authorizationLocation(Authorization_URL)
                .setClientId(CLIENT_ID)
                .setRedirectURI(REDIRECT_URI)
                .setScope(SCOPES)
                .setResponseType("code")
                .buildQueryMessage();
        return request.getLocationUri();
    }
    public String getAccessToken(String code) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest request= OAuthClientRequest
                .tokenLocation(TOKEN_REQUEST_URL)
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectURI(REDIRECT_URI)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setCode(code)
                .buildBodyMessage();
        OAuthClient oAuthClient=new OAuthClient(new URLConnectionClient());
        OAuthJSONAccessTokenResponse response=oAuthClient.accessToken(request);
        return response.getAccessToken();
    }

    public String requestProtectedRessource(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(RESSOURCE_URI, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}