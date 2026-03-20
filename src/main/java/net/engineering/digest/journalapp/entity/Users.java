package net.engineering.digest.journalapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class Users {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String userName;
    private String password;
    private List<String> roles;
    private String email;
    private boolean sentimentAnalysisEnabled;


    @DBRef
    private List<JournalEntries> journalEntries = new ArrayList<>();

}

