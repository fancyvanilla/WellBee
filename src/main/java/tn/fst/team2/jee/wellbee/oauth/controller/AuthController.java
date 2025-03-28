package tn.fst.team2.jee.wellbee.oauth.controller;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import tn.fst.team2.jee.wellbee.oauth.dao.UserSession;
import tn.fst.team2.jee.wellbee.oauth.services.JwtService;
import tn.fst.team2.jee.wellbee.oauth.services.OauthClient;
import tn.fst.team2.jee.wellbee.oauth.dao.UserDao;
import tn.fst.team2.jee.wellbee.oauth.services.SessionStorageService;

//TODO: add jwt class and handle authorization
//TODO: add class mappers and link to database
//TODO: add profile form logic and controllers
@Controller
public class AuthController {
    private final OauthClient oauthClient ;
    private final JwtService jwtService;
    private final SessionStorageService sessionStorageSession;

    @Autowired
    public AuthController(OauthClient oauthClient, JwtService jwtService, SessionStorageService sessionStorageSession) {
        this.oauthClient = oauthClient;
        this.jwtService = jwtService;
        this.sessionStorageSession = sessionStorageSession;
    }
    @GetMapping("/login")
    public String login(Model model) throws OAuthSystemException {
        String url= oauthClient.createAuthorizationUrl();
        model.addAttribute("url", url);
        return "login";
    }
    @GetMapping("/login/oauth2/code/linkedIn")
    public ResponseEntity<String> handleCallBack(@RequestParam("code") String code, Model model) throws OAuthSystemException, OAuthProblemException, JsonProcessingException {
        UserSession session= oauthClient.getOauthToken(code);
        String response= oauthClient.requestProtectedRessource(session.getAccessToken());
        ObjectMapper objectMapper = new ObjectMapper();
        UserDao user = objectMapper.readValue(response, UserDao.class);
        String jwtToken= jwtService.createJwtToken(user);
        //store user session in redis
        UserSession userSession=new UserSession(
                session.getAccessToken(),
                session.getExpiresIn()
        );
        sessionStorageSession.setUserSession(user.getSub(),userSession);
        return ResponseEntity.ok(jwtToken);
    }
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserDao user, Model model) {
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        return "user";
    }
}
