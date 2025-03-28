package tn.fst.team2.jee.wellbee.oauth.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private String jobTitle;
    private String department;

    public ProfileDto() {
    }
}
