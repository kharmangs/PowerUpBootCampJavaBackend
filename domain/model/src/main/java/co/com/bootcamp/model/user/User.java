package co.com.bootcamp.model.user;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String id;
    private String names;
    private String surnames;
    private String identification;
    private String cellphone;
    private String email;
    private String salary;
    private String role;
}
