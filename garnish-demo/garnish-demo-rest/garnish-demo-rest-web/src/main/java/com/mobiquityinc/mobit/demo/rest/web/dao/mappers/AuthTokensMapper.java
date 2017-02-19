package com.mobiquityinc.mobit.demo.rest.web.dao.mappers;

import com.mobiquityinc.mobit.demo.rest.web.dao.model.AuthToken;
import org.apache.ibatis.annotations.Param;

public interface AuthTokensMapper {

    void createTokenForUser(@Param("userId") long userId,
                            @Param("authToken") AuthToken authToken);

}
