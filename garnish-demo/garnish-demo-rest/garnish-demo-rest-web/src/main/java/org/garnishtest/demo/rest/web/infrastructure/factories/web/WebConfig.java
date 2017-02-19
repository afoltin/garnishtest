package org.garnishtest.demo.rest.web.infrastructure.factories.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import({
        WebInfrastructureConfig.class,
        ControllersConfig.class,
})
public class WebConfig {}
