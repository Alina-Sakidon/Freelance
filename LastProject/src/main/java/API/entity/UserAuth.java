package API.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserAuth {
    private String username;
    private String password;
    private String confirmPassword;
    private String lastname;
    private String name;
    private int id;
}
