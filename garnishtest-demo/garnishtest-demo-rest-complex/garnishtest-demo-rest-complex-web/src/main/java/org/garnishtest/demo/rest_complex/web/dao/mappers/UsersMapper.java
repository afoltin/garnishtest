package org.garnishtest.demo.rest_complex.web.dao.mappers;

import org.garnishtest.demo.rest_complex.web.dao.model.User;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Nullable;

public interface UsersMapper {

    long insertUser(User user);

    @Nullable
    User getUserByUsername(@Param("username") String username);

    @Nullable
    User getUserWithAddressByAuthToken(@Param("authToken") String authToken);

}
