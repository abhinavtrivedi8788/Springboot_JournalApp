package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/all-users")
    public ResponseEntity<?> fetchAllUser() {
        List<Users> allUsers = usersService.getAllUsers();
        if (allUsers != null || ! allUsers.isEmpty()) {
           return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
