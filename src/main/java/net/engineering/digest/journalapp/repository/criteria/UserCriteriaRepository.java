package net.engineering.digest.journalapp.repository.criteria;

import net.engineering.digest.journalapp.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserCriteriaRepository {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    @Autowired
    private MongoTemplate mongoTemplate;

    // This class can be used to implement custom query methods for the User entity using Criteria API.
    // For example, you can create methods to find users by specific criteria such as username, email, or registration date.

    public List<Users> getUserForSentimentsAnalysis() {
        Query query = new Query();
        /*query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("email").ne(null).ne(""));*/

        query.addCriteria(Criteria.where("email").regex(EMAIL_REGEX));
        query.addCriteria(Criteria.where("sentimentAnalysisEnabled").is(true));
       return mongoTemplate.find(query, Users.class);

    }
}
