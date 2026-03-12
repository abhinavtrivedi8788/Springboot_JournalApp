package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalCacheEntity;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private UsersService usersService;

    // Endpoint to create a new user without  password encoding
    @PostMapping("/register")
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        return new ResponseEntity<>(usersService.saveUserAndPassword(users), HttpStatus.CREATED);
    }

    @PostMapping("/config")
    public ResponseEntity<?> addConfigurationInDB(@RequestBody JournalCacheEntity cacheEntity) {
        return new ResponseEntity<>(usersService.saveCacheInDB(cacheEntity), HttpStatus.CREATED);
    }
}
