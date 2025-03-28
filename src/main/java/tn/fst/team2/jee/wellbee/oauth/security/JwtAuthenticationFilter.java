package tn.fst.team2.jee.wellbee.oauth.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.fst.team2.jee.wellbee.oauth.dao.UserDao;
import tn.fst.team2.jee.wellbee.oauth.dao.UserSession;
import tn.fst.team2.jee.wellbee.oauth.services.JwtService;
import tn.fst.team2.jee.wellbee.oauth.services.SessionStorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final SessionStorageService sessionStorageService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, SessionStorageService sessionStorageService) {
        this.jwtService = jwtService;
        this.sessionStorageService = sessionStorageService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            if (jwtService.isTokenValid(jwt) && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDao user = jwtService.decodeToken(jwt);
                //checks if user session is still valid
                UserSession userSession= sessionStorageService.getUserSession(user.getSub());
                if (userSession==null){
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Session expired. Please log in again.");
                    return;
                }
                //we will need this when we add roles
                List<GrantedAuthority> authorities = new ArrayList<>();
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, jwt, authorities);
                //this marks the user as authenticated
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
