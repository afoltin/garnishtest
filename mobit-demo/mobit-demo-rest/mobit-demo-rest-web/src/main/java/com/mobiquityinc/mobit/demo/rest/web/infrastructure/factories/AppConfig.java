package com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories;

import com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.dao.DaoConfig;
import com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.infrastructure.InfrastructureConfig;
import com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.service.ServiceConfig;
import com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.web.WebConfig;
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
