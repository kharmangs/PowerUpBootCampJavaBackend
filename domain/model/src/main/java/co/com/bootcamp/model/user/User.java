package co.com.bootcamp.model.user;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class User {
    private String id;
    private String names;
    private String surnames;
    private String identification;
    private String phone;
    private String email;
    private BigDecimal salary;
    private int roleId;
    private LocalDate birthdate;
}
