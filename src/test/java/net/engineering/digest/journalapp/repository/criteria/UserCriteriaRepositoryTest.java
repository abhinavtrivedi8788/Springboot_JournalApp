package net.engineering.digest.journalapp.repository.criteria;

import net.engineering.digest.journalapp.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserCriteriaRepositoryTest {

      @Autowired
      UserCriteriaRepository userCriteriaRepository;

        @Test
        void testGetUserForSentimentsAnalysis() {
            List<Users> users = userCriteriaRepository.getUserForSentimentsAnalysis();
            assertNotNull(users);
            assertFalse(users.isEmpty());
            for (Users user : users) {
                assertTrue(user.getEmail().matches(UserCriteriaRepository.EMAIL_REGEX));
                assertTrue(user.isSentimentAnalysisEnabled());
            }
        }
}
