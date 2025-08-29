package co.com.bootcamp.api.config;

import org.springframework.aot.generate.Generated;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Generated
@Configuration
public class WebConfig {

    @Bean
    public WebProperties.Resources webPropertiesResources() {
        return new WebProperties().getResources();
    }
}