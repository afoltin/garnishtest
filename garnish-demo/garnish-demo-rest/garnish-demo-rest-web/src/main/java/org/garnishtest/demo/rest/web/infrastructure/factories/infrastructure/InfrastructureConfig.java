package org.garnishtest.demo.rest.web.infrastructure.factories.infrastructure;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Import(SecurityConfig.class)
public class InfrastructureConfig {

    @Bean
    public PropertyPlaceholderConfigurer placeHolderConfigurer() {
        final PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();

        placeholderConfigurer.setLocation(
                new ClassPathResource("garnish-demo-rest-web.properties")
        );
        placeholderConfigurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);

        return placeholderConfigurer;
    }

}
