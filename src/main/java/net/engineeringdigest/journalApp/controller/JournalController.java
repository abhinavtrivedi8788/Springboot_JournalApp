package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.entity.JournalEntries;
import net.engineeringdigest.journalApp.entity.Users;
import net.engineeringdigest.journalApp.service.JournalService;
import net.engineeringdigest.journalApp.service.UsersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/journal")
@Tag(name = "Journal API's")
public class JournalController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JournalService journalService;


    @GetMapping
    @Operation(summary = "Get All Journal Entries for Logged in User")
    public ResponseEntity<List<JournalEntries>> getJournalEntriesOfUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Users usersByUsername = usersService.getUsersByUsername(userName);

        List<JournalEntries> journalEntries = usersByUsername.getJournalEntries();
        if (!journalEntries.isEmpty()) {
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Operation(summary = "Create  Journal Entries for Logged in User")
    public ResponseEntity <?>  createJournalEntries(@RequestBody JournalEntries journalEntries) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return  new ResponseEntity<>(journalService.createJournalEntries(journalEntries,userName), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Journal Entries by Journal Id")
    public ResponseEntity <?> getJournalEntryById(@PathVariable String id) {
        ObjectId objectId = new ObjectId(id);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Users userByUsername = usersService.getUsersByUsername(userName);
        List<JournalEntries> collect = userByUsername.getJournalEntries().stream().filter(journal -> journal.getId().equals(objectId)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntries> journalEntryById = journalService.getJournalEntryById(objectId);
            if (journalEntryById.isPresent()) {
                return new ResponseEntity<JournalEntries>(journalEntryById.get(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Requested Journal belongs to some other user ",HttpStatus.NOT_FOUND);
        }
        return null;
    }


    // In order to remove junk data in db for Journal.
   /* @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId id) {
        journalService.deleteJournalEntryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete  Journal Entries by Journal Id")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        journalService.deleteJournalEntryById(userName,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Journal Entries by Journal Id")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntries journalEntries) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        JournalEntries updatedJournal = journalService.updateJournalEntryById(userName,id, journalEntries);
        if (updatedJournal != null) {
            return new ResponseEntity<JournalEntries>(updatedJournal, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
