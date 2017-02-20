package org.garnishtest.demo.rest_complex.web.infrastructure.factories;

import org.garnishtest.demo.rest_complex.web.infrastructure.factories.dao.DaoConfig;
import org.garnishtest.demo.rest_complex.web.infrastructure.factories.infrastructure.InfrastructureConfig;
import org.garnishtest.demo.rest_complex.web.infrastructure.factories.service.ServiceConfig;
import org.garnishtest.demo.rest_complex.web.infrastructure.factories.web.WebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import({
        InfrastructureConfig.class,
        DaoConfig.class,
        ServiceConfig.class,
        WebConfig.class,
})
public class AppConfig { }
