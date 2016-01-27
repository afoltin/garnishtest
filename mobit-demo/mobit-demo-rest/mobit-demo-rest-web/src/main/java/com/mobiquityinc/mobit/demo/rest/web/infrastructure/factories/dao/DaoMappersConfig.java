package com.mobiquityinc.mobit.demo.rest.web.infrastructure.factories.dao;

import com.mobiquityinc.mobit.demo.rest.web.dao.mappers.AddressesMapper;
import com.mobiquityinc.mobit.demo.rest.web.dao.mappers.AuthTokensMapper;
import com.mobiquityinc.mobit.demo.rest.web.dao.mappers.TodoListsMapper;
import com.mobiquityinc.mobit.demo.rest.web.dao.mappers.UsersMapper;
import lombok.NonNull;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DaoInfrastructureConfig.class)
public class DaoMappersConfig {

    @Autowired
    private DaoInfrastructureConfig daoInfrastructureConfig;

    @Bean
    public TodoListsMapper todoListMapper() throws Exception {
        return createMapper(TodoListsMapper.class);
    }

    @Bean
    public UsersMapper userMapper() throws Exception {
        return createMapper(UsersMapper.class);
    }

    @Bean
    public AddressesMapper addressMapper() throws Exception {
        return createMapper(AddressesMapper.class);
    }

    @Bean
    public AuthTokensMapper authTokensMapper() throws Exception {
        return createMapper(AuthTokensMapper.class);
    }

    private <T> T createMapper(@NonNull final Class<T> mapperClass) throws Exception {
        final SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(
                this.daoInfrastructureConfig.sqlSessionFactory()
        );

        return sessionTemplate.getMapper(mapperClass);
    }

}
