package co.com.bootcamp.api.dto;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Builder
public record UserRequest(
        String id,
        @NonNull String names,
        @NonNull String surnames,
        @NonNull String identification,
        @NonNull String email,
        @NonNull String phone,
        @NonNull BigDecimal salary,
        @NonNull Integer roleId,
        @NonNull LocalDate birthdate) {

    private static final BigDecimal SALARY_LIMIT = new BigDecimal(15_000_000);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public UserRequest {

        if (names.isBlank() || surnames.isBlank() || email.isBlank()) {
            throw new IllegalArgumentException("The fields cannot be blank");
        }

        if (salary.compareTo(BigDecimal.ZERO) <= 0 || salary.compareTo(SALARY_LIMIT) > 0) {
            throw new IllegalArgumentException("The salary is not within the allowed range");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
