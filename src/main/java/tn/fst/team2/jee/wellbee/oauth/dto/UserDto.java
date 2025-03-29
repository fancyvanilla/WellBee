package tn.fst.team2.jee.wellbee.oauth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private String sub;
    private String name;
    private String email;
    private String picture;

    public UserDto() {
    }
    public UserDto(String sub, String name, String email, String picture) {
        this.sub = sub;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
