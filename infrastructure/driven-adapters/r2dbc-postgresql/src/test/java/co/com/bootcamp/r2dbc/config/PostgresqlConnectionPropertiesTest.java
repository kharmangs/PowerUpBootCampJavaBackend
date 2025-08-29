package co.com.bootcamp.r2dbc.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostgresqlConnectionPropertiesTest {

    @Test
    void should_return_email_address() {
        PostgresqlConnectionProperties properties = new PostgresqlConnectionProperties(
                "localhost",
                5432,
                "mydatabase",
                "public",
                "user",
                "password"
        );

        assertThat(properties.host()).isEqualTo("localhost");
        assertThat(properties.port()).isEqualTo(5432);
        assertThat(properties.database()).isEqualTo("mydatabase");
        assertThat(properties.schema()).isEqualTo("public");
        assertThat(properties.username()).isEqualTo("user");
        assertThat(properties.password()).isEqualTo("password");
    }
}
