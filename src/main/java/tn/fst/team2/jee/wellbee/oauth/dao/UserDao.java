package tn.fst.team2.jee.wellbee.oauth.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDao {
    private String name;
    private String email;
    private String picture;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
