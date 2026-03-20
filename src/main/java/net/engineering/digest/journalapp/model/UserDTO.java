package net.engineering.digest.journalapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private String  id;
    private String userName;
    private String password;
    private List<String> roles;
    private String email;
    private boolean sentimentAnalysisEnabled;


    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id + '\'' +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", email='" + email + '\'' +
                ", sentimentAnalysisEnabled=" + sentimentAnalysisEnabled +
                '}';
    }
}
