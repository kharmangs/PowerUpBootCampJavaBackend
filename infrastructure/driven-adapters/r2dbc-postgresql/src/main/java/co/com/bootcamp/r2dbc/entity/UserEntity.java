package co.com.bootcamp.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    @Id
    private UUID id;
    private String names;
    private String surnames;
    private String identification;
    private String email;
    private String cellphone;
    private String salary;
    private String role;
}
