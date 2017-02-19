package com.mobiquityinc.mobit.demo.rest.web.dao.mappers;

import com.mobiquityinc.mobit.demo.rest.web.dao.model.User;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Nullable;

public interface UsersMapper {

    long insertUser(User user);

    @Nullable
    User getUserByUsername(@Param("username") String username);

    @Nullable
    User getUserWithAddressByAuthToken(@Param("authToken") String authToken);

}
