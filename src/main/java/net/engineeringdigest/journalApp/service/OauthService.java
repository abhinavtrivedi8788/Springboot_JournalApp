package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.config.security.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.config.security.jwt.JwtUtility;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class OauthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtility jwtUtility;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    private static final String TOKEN_ENDPOINT="https://oauth2.googleapis.com/token";
    private static final String USER_INFO_ENDPOINT="https://oauth2.googleapis.com/tokeninfo?id_token=";

    public ResponseEntity<Map<String, String>> processOauthLogin(String authorizationCodeFromThirdParty) {

        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code",authorizationCodeFromThirdParty);
            params.add("client_id",clientId);
            params.add("client_secret",clientSecret);
            params.add("redirect_uri","https://developers.google.com/oauthplayground");
            params.add("grant_type", "authorization_code");

            // We will tell the Oauth provider that which kind of request we are sending using headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Request
            HttpEntity<MultiValueMap<String, String>> requestObject = new HttpEntity<>(params,headers);

            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(TOKEN_ENDPOINT, requestObject, Map.class);
            String idToken = (String) tokenResponse.getBody().get("id_token");

            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(USER_INFO_ENDPOINT + idToken, Map.class);
            if(userInfoResponse.getStatusCode().is2xxSuccessful()){
                Map<String,Object> body = userInfoResponse.getBody();
                String username = (String) body.get("email");
                UserDetails userDetails = null;
                try {
                   userDetails = userDetailsService.loadUserByUsername(username);
                } catch (UsernameNotFoundException e) {
                    Users users = new Users();
                    users.setUserName(username);
                    users.setEmail(username);
                    users.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    users.setRoles(Collections.singletonList("USER"));
                    usersRepository.save(users);
                    userDetails = userDetailsService.loadUserByUsername(username);
                }

                String jwtToken = jwtUtility.generateToken(userDetails);
                return ResponseEntity.ok(Collections.singletonMap("jwt_token",jwtToken));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (RestClientException | UsernameNotFoundException e) {
            log.error(e.getMessage());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return null;
    }
}
