package tn.fst.team2.jee.wellbee.users.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.fst.team2.jee.wellbee.oauth.dto.ProfileDto;
import tn.fst.team2.jee.wellbee.oauth.dto.UserDto;
import tn.fst.team2.jee.wellbee.users.entities.User;
import tn.fst.team2.jee.wellbee.users.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUser(@AuthenticationPrincipal UserDto dtoUser) {
        User user= userRepository.findByLinkedinId(dtoUser.getSub());
        if (user!=null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/profile")
    public ResponseEntity<User> setUserProfile(@AuthenticationPrincipal UserDto dtoUser, @RequestBody ProfileDto profile) {
        User user = userRepository.findByLinkedinId(dtoUser.getSub());
        if (user!=null) {
            user.setJobTitle(profile.getJobTitle());
            user.setDepartment(profile.getDepartment());
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
