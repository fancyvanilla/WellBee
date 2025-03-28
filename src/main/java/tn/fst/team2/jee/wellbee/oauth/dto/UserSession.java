package tn.fst.team2.jee.oauth.dto;

public class UserSession {
    public String accessToken;
    public Long expiresIn;

    public UserSession() {
    }

    public UserSession(String accessToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }


}
