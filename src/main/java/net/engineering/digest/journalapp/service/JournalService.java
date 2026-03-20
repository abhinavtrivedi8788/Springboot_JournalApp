package net.engineering.digest.journalapp.service;


import net.engineering.digest.journalapp.entity.JournalEntries;
import net.engineering.digest.journalapp.entity.Users;
import net.engineering.digest.journalapp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JournalService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JournalRepository journalRepository;

    @Transactional
    // for Transactional management,
    // if something wrong happen with user after persist the journal entry, it should Roll back the transaction
    public JournalEntries createJournalEntries(JournalEntries journalEntries, String userName) {
        try {
            Users user = usersService.getUsersByUsername(userName);
            JournalEntries entries = journalRepository.save(journalEntries);
            user.getJournalEntries().add(entries);
            //user.setUserName(null);
            //just to replicate  if something wrong happen with user after persist the journal entry,
            // it should Roll back the transaction
            usersService.saveUserEntity(user);
            return entries;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<JournalEntries> getJournalEntries() {
        return journalRepository.findAll();
    }

    public Optional<JournalEntries> getJournalEntryById(ObjectId id) {
        return journalRepository.findById(id);
    }

    // only delete the journal entry
    public void deleteJournalEntryById(ObjectId id) {
        journalRepository.deleteById(id);
    }

    @Transactional
    public void deleteJournalEntryById(String userName, ObjectId id) {
        try {
            Users user = usersService.getUsersByUsername(userName);
            boolean ifRemoved = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
            if (ifRemoved) {
                usersService.saveUserEntity(user);
                journalRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JournalEntries updateJournalEntryById(String userName,ObjectId id,JournalEntries journalEntries) {
        Users usersByUsername = usersService.getUsersByUsername(userName);
        List<JournalEntries> collect = usersByUsername.getJournalEntries().stream().filter(entry -> entry.getId().equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntries> existingEntry = journalRepository.findById(id);
            if (existingEntry.isPresent()) {
                JournalEntries entryToUpdate = existingEntry.get();
                entryToUpdate.setTitle(journalEntries.getTitle());
                entryToUpdate.setContent(journalEntries.getContent());
                return journalRepository.save(entryToUpdate);
            } else {
                return null;
            }
        }
        return null;
    }
}
