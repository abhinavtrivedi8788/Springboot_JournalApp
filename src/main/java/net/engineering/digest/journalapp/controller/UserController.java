package net.engineering.digest.journalapp.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineering.digest.journalapp.entity.Users;
import net.engineering.digest.journalapp.externalApi.ExternalService;
import net.engineering.digest.journalapp.externalApi.WeatherModel;
import net.engineering.digest.journalapp.model.UserDTO;
import net.engineering.digest.journalapp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@Tag(name = "Users API's")
public class UserController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ExternalService externalService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllJournalEntries() {
        return new ResponseEntity<>(usersService.getAllUsers(), HttpStatus.OK);
    }


    /*@GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable String id) {
        Optional<Users> user = usersService.getJUsersById(new ObjectId(id));
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    @PutMapping
    public ResponseEntity<Users> updateUserById(@RequestBody UserDTO users) {
        // Get the currently authenticated user's username from the security context as we are using
        // basic authentication & we want to update the currently authenticated user's information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        Users updatedUser = usersService.getUsersByUsername(userName);
        updatedUser.setUserName(users.getUserName());
        updatedUser.setPassword(users.getPassword());
        updatedUser.setRoles(users.getRoles());
        updatedUser.setEmail(users.getEmail());
        updatedUser.setSentimentAnalysisEnabled(users.isSentimentAnalysisEnabled());
        usersService.saveUserAndPassword(updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        usersService.deleteUsersByUsername(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/external")
    public ResponseEntity<?> testExternalService() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        WeatherModel weatherResponse = externalService.getDataFromExternalApi("MUMBAI");

        return new ResponseEntity<>("Hello..!! " + userName+ " is calling external service to check  weather of :  "
                + weatherResponse.getLocation().getName() + " that is  "+ weatherResponse.getCurrent().getTemperature()
                + " and actually feels like : "+weatherResponse.getCurrent().getFeelslike() , HttpStatus.OK);
    }
}
