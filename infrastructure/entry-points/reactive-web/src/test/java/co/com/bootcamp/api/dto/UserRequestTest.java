package co.com.bootcamp.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRequestTest {

    private static final String VALID_ID = "1";
    private static final String VALID_NAMES = "John";
    private static final String VALID_SURNAMES = "Doe";
    private static final String VALID_IDENTIFICATION = "123456";
    private static final String VALID_EMAIL = "john.doe@example.com";
    private static final String VALID_PHONE = "1234567890";
    private static final BigDecimal VALID_SALARY = new BigDecimal("1000000");
    private static final Integer VALID_ROLE_ID = 1;
    private static final LocalDate VALID_BIRTHDATE = LocalDate.of(1990, 1, 1);

    @Test
    void shouldCreateUserRequestWithValidData() {
        UserRequest request = UserRequest.builder()
                .id(VALID_ID)
                .names(VALID_NAMES)
                .surnames(VALID_SURNAMES)
                .identification(VALID_IDENTIFICATION)
                .email(VALID_EMAIL)
                .phone(VALID_PHONE)
                .salary(VALID_SALARY)
                .roleId(VALID_ROLE_ID)
                .birthdate(VALID_BIRTHDATE)
                .build();

        assertThat(request.names()).isEqualTo(VALID_NAMES);
    }

    @Test
    void shouldThrowWhenNamesIsBlank() {
        assertThatThrownBy(this::buildWithBlankNames)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be blank");
    }

    private void buildWithBlankNames() {
        UserRequest.builder()
                .id(VALID_ID)
                .names(" ")
                .surnames(VALID_SURNAMES)
                .identification(VALID_IDENTIFICATION)
                .email(VALID_EMAIL)
                .phone(VALID_PHONE)
                .salary(VALID_SALARY)
                .roleId(VALID_ROLE_ID)
                .birthdate(VALID_BIRTHDATE)
                .build();
    }

    @Test
    void shouldThrowWhenSurnamesIsBlank() {
        assertThatThrownBy(this::buildWithBlankSurnames)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be blank");
    }

    private void buildWithBlankSurnames() {
        UserRequest.builder()
                .id(VALID_ID)
                .names(VALID_NAMES)
                .surnames(" ")
                .identification(VALID_IDENTIFICATION)
                .email(VALID_EMAIL)
                .phone(VALID_PHONE)
                .salary(VALID_SALARY)
                .roleId(VALID_ROLE_ID)
                .birthdate(VALID_BIRTHDATE)
                .build();
    }

    @Test
    void shouldThrowWhenEmailIsBlank() {
        assertThatThrownBy(this::buildWithBlankEmail)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be blank");
    }

    private void buildWithBlankEmail() {
        UserRequest.builder()
                .id(VALID_ID)
                .names(VALID_NAMES)
                .surnames(VALID_SURNAMES)
                .identification(VALID_IDENTIFICATION)
                .email(" ")
                .phone(VALID_PHONE)
                .salary(VALID_SALARY)
                .roleId(VALID_ROLE_ID)
                .birthdate(VALID_BIRTHDATE)
                .build();
    }

    @Test
    void shouldThrowWhenSalaryIsZeroOrNegative() {
        assertThatThrownBy(this::buildWithZeroSalary)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("salary is not within the allowed range");

        assertThatThrownBy(this::buildWithNegativeSalary)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("salary is not within the allowed range");
    }

    private void buildWithZeroSalary() {
        UserRequest.builder()
                .id(VALID_ID)
                .names(VALID_NAMES)
                .surnames(VALID_SURNAMES)
                .identification(VALID_IDENTIFICATION)
                .email(VALID_EMAIL)
                .phone(VALID_PHONE)
                .salary(BigDecimal.ZERO)
                .roleId(VALID_ROLE_ID)
                .birthdate(VALID_BIRTHDATE)
                .build();
    }

    private void buildWithNegativeSalary() {
        UserRequest.builder()
                .id(VALID_ID)
                .names(VALID_NAMES)
                .surnames(VALID_SURNAMES)
                .identification(VALID_IDENTIFICATION)
                .email(VALID_EMAIL)
                .phone(VALID_PHONE)
                .salary(new BigDecimal("-1"))
                .roleId(VALID_ROLE_ID)
                .birthdate(VALID_BIRTHDATE)
                .build();
    }

    @Test
    void shouldThrowWhenSalaryIsAboveLimit() {
        assertThatThrownBy(this::buildWithHighSalary)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("salary is not within the allowed range");
    }

    private void buildWithHighSalary() {
        UserRequest.builder()
                .id(VALID_ID)
                .names(VALID_NAMES)
                .surnames(VALID_SURNAMES)
                .identification(VALID_IDENTIFICATION)
                .email(VALID_EMAIL)
                .phone(VALID_PHONE)
                .salary(new BigDecimal("20000000"))
                .roleId(VALID_ROLE_ID)
                .birthdate(VALID_BIRTHDATE)
                .build();
    }

    @Test
    void shouldThrowWhenEmailIsInvalid() {
        assertThatThrownBy(this::buildWithInvalidEmail)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid email format");
    }

    private void buildWithInvalidEmail() {
        UserRequest.builder()
                .id(VALID_ID)
                .names(VALID_NAMES)
                .surnames(VALID_SURNAMES)
                .identification(VALID_IDENTIFICATION)
                .email("invalid-email")
                .phone(VALID_PHONE)
                .salary(VALID_SALARY)
                .roleId(VALID_ROLE_ID)
                .birthdate(VALID_BIRTHDATE)
                .build();
    }
}