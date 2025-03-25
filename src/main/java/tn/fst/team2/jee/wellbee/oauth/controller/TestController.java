package tn.fst.team2.jee.wellbee.oauth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.fst.team2.jee.wellbee.oauth.service.OauthClient;
import tn.fst.team2.jee.wellbee.oauth.dao.UserDao;

@Controller
public class TestController {
    private final OauthClient oauthClient ;

    @Autowired
    public TestController(OauthClient oauthClient) {
        this.oauthClient = oauthClient;
    }

    @GetMapping("/test")
    public String index() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) throws OAuthSystemException {
        String url= oauthClient.createAuthorizationUrl();
        model.addAttribute("url", url);
        return "login";
    }

    @GetMapping("/login/oauth2/code/linkedIn")
    public String handleCallBack(@RequestParam("code") String code, Model model) throws OAuthSystemException, OAuthProblemException, JsonProcessingException {
        String accessToken= oauthClient.getAccessToken(code);
        String response= oauthClient.requestProtectedRessource(accessToken);
        ObjectMapper objectMapper = new ObjectMapper();
        UserDao user = objectMapper.readValue(response, UserDao.class);
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        System.out.println(user.getName());
        return "redirect:/user?name=" + user.getName() + "&email=" + user.getEmail();
    }

    @GetMapping("/user")
    public String user(@RequestParam("name") String name, @RequestParam("email") String email, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        return "user";
    }
}
