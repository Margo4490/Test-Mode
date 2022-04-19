package data;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class RegistrationInfo {
    private String login;
    private String password;
    private String status;
}
