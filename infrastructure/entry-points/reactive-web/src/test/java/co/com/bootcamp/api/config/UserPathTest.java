package co.com.bootcamp.api.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserPathTest {
    @Test
    @DisplayName("Should set and get users path")
    void shouldSetAndGetUsersPath() {
        UserPath userPath = new UserPath();
        userPath.setUsers("/api/v1/users");
        assertThat(userPath.getUsers()).isEqualTo("/api/v1/users");
    }

    @Test
    @DisplayName("Should handle null users path")
    void shouldHandleNullUsersPath() {
        UserPath userPath = new UserPath();
        userPath.setUsers(null);
        assertThat(userPath.getUsers()).isNull();
    }

    @Test
    @DisplayName("Should handle empty users path")
    void shouldHandleEmptyUsersPath() {
        UserPath userPath = new UserPath();
        userPath.setUsers("");
        assertThat(userPath.getUsers()).isEmpty();
    }
}
