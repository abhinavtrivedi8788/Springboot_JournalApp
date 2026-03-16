package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.config.security.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.config.security.jwt.JwtUtility;
import net.engineeringdigest.journalApp.entity.JournalCacheEntity;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private UsersService usersService;


    // Code below is done for JWT both wiring
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtility jwtUtility;

    // Endpoint to create a new user without  password encoding
    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        return new ResponseEntity<>(usersService.saveUserAndPassword(users), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users users) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(users.getUserName(), users.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(users.getUserName());
        String token = jwtUtility.generateToken(userDetails);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    // This End point is for Inserting / storing the configuration in DB that will be utilized for future uses
    @PostMapping("/config")
    public ResponseEntity<?> addConfigurationInDB(@RequestBody JournalCacheEntity cacheEntity) {
        return new ResponseEntity<>(usersService.saveCacheInDB(cacheEntity), HttpStatus.CREATED);
    }
}
