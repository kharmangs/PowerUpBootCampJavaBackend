package co.com.bootcamp.api.dto;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        Map<String, String> requireFields = Map.of(
                "names", names,
                "surnames", surnames,
                "identification", identification,
                "email", email
        );

        String blankFields = requireFields.entrySet().stream()
                .filter(e -> e.getValue().isBlank())
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));

        if (!blankFields.isEmpty()) {
            throw new IllegalArgumentException("The following fields cannot be blank: " + blankFields);
        }

        if (salary.compareTo(BigDecimal.ZERO) <= 0 || salary.compareTo(SALARY_LIMIT) > 0) {
            throw new IllegalArgumentException("The salary must be greater than 0 and less than or equal to $15.000.000");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
