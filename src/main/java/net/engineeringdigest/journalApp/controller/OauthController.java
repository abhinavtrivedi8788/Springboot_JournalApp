package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// to replicate front end we are using https://developers.google.com/oauthplayground/
// and putting - https://www.googleapis.com/auth/userinfo.email in Step one that will generate
// the auth token which we rae going to use in postman to hit this controller but

// In real world FE will hit the Google / facebook Auth url , that auth server will call this controller ( url)
// which us configured in redirect url values under details (like client id , scope, state ) )when FE call the Google auth
// that that auth code will be used and later generates the JWT token that we can use to  call the secured endpoints,

@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    @Autowired
    private OauthService oauthService;

    @GetMapping("/callback")
     public ResponseEntity<?> handleCallBackUrl(@RequestParam String authorizationCode){
        ResponseEntity<Map<String, String>> mapResponseEntity = oauthService.processOauthLogin(authorizationCode);
        if (mapResponseEntity.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(mapResponseEntity.getBody(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
}
