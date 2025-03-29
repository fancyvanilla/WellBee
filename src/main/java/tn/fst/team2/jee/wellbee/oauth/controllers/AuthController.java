package tn.fst.team2.jee.wellbee.oauth.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.fst.team2.jee.wellbee.oauth.dto.UserDto;
import tn.fst.team2.jee.wellbee.mappers.UserMapper;
import tn.fst.team2.jee.wellbee.oauth.dto.UserSession;
import tn.fst.team2.jee.wellbee.oauth.services.JwtService;
import tn.fst.team2.jee.wellbee.oauth.services.OauthClient;
import tn.fst.team2.jee.wellbee.oauth.services.SessionStorageService;
import tn.fst.team2.jee.wellbee.users.entities.User;
import tn.fst.team2.jee.wellbee.users.services.UserService;

@Controller
public class AuthController {
    private final OauthClient oauthClient ;
    private final JwtService jwtService;
    private final SessionStorageService sessionStorageSession;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public AuthController(OauthClient oauthClient, JwtService jwtService, SessionStorageService sessionStorageSession, UserService userService, UserMapper userMapper) {
        this.oauthClient = oauthClient;
        this.jwtService = jwtService;
        this.sessionStorageSession = sessionStorageSession;
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @GetMapping("/login")
    public String login(Model model) throws OAuthSystemException {
        String url= oauthClient.createAuthorizationUrl();
        model.addAttribute("url", url);
        return "login";
    }
    @GetMapping("/login/oauth2/code/linkedIn")
    public ResponseEntity<String> handleCallBack(@RequestParam("code") String code, Model model) throws OAuthSystemException, OAuthProblemException, JsonProcessingException {
        UserSession session = oauthClient.getOauthToken(code);
        String response = oauthClient.requestProtectedRessource(session.getAccessToken());
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto user = objectMapper.readValue(response, UserDto.class);
        String jwtToken = jwtService.createJwtToken(user);

        //store user in database if he doesn't exist
        User userDb=userService.getUserByLinkedinId(user.getSub());
        if(userDb==null){
            userService.addOrUpdateUser(userMapper.toUser(user));
        }
        else{
            int updates=0;
            if (!userDb.getFullName().equals(user.getName())){ //if user changed his name
                userDb.setFullName(user.getName());
                updates++;
            }
            if (!userDb.getEmail().equals(user.getEmail())){ //if user changed his email
                userDb.setEmail(user.getEmail());
                updates++;
            }
             userDb.setImageUrl(user.getPicture());
            // we check before updating to avoid unnecessary writes operations
            if (updates > 0) {
                userService.addOrUpdateUser(userDb);
            }
        }
        //store user session in redis
        UserSession userSession=new UserSession(
                session.getAccessToken(),
                session.getExpiresIn()
        );
        sessionStorageSession.setUserSession(user.getSub(),userSession);
        return ResponseEntity.ok(jwtToken);
    }
}
