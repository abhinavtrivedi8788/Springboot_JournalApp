package net.engineering.digest.journalapp.service;


import lombok.extern.slf4j.Slf4j;
import net.engineering.digest.journalapp.entity.JournalCacheEntity;
import net.engineering.digest.journalapp.entity.Users;
import net.engineering.digest.journalapp.model.UserDTO;
import net.engineering.digest.journalapp.repository.JournalCacheRepository;
import net.engineering.digest.journalapp.repository.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsersService {

    @Autowired
    private JournalCacheRepository journalCacheRepository;

    @Autowired
    private UsersRepository usersRepository;

    // Create a static instance of BCryptPasswordEncoder to be used for password encoding
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Users saveUserAndPassword(Users userEntity) {
        try {
            String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
            userEntity.setPassword(encodedPassword);
            userEntity.setRoles(Collections.singletonList("USER"));
            return usersRepository.save(userEntity);
        } catch (Exception e) {
            log.error("Error occur due to {} : ", userEntity.getUserName(), e);
        }
        return userEntity;
    }


    public Users saveUserEntity(Users userEntity) {
        return usersRepository.save(userEntity);
    }

    public List<UserDTO> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setId(String.valueOf(user.getId()));
            dto.setUserName(user.getUserName());
            dto.setEmail(user.getEmail());
            dto.setRoles(user.getRoles());
            dto.setSentimentAnalysisEnabled(user.isSentimentAnalysisEnabled());
            return dto;
        }).toList();
    }

    public Optional<Users> getJUsersById(ObjectId id) {
        return usersRepository.findById(id);
    }

    public Users getUsersByUsername(String username) {
        return usersRepository.findByUserName(username);
    }

    public void deleteUsersById(ObjectId id) {
        usersRepository.deleteById(id);
    }

    public void deleteUsersByUsername(String username) {
       usersRepository.deleteByUserName(username);
    }

    public Users updateUsersEntryById(ObjectId id, Users usersEntity) {
        Optional<Users> existingUsers = usersRepository.findById(id);
        if (existingUsers.isPresent()) {
            Users entryToUpdate = existingUsers.get();
            return usersRepository.save(entryToUpdate);
        } else {
            return null;
        }
    }

    public JournalCacheEntity saveCacheInDB(JournalCacheEntity cache) {
        return journalCacheRepository.save(cache);
    }
}
