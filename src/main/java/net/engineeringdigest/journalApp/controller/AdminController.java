package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Admin API's")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/all-users")
    @Operation(summary = "Admin to fetch all users")
    public ResponseEntity<?> fetchAllUser() {
        List<Users> allUsers = usersService.getAllUsers();
        if (allUsers != null || ! allUsers.isEmpty()) {
           return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
