package tn.fst.team2.jee.wellbee.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.fst.team2.jee.wellbee.oauth.dto.UserDto;
import tn.fst.team2.jee.wellbee.users.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source="fullName", target="name")
    @Mapping(source="linkedinId", target="sub")
    @Mapping(source="imageUrl", target="picture")
    UserDto toUserDto(User user);

    @Mapping(source="name", target="fullName")
    @Mapping(source="sub", target="linkedinId")
    @Mapping(source="picture", target="imageUrl")
    User toUser(UserDto userDto);
}
