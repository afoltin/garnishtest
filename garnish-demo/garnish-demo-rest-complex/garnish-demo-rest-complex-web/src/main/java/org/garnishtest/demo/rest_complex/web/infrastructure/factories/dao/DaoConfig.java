package org.garnishtest.demo.rest_complex.web.infrastructure.factories.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        DaoInfrastructureConfig.class,
        DaoMappersConfig.class,
})
public class DaoConfig { }
