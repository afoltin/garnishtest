package org.garnishtest.demo.rest_complex.web.dao.mappers;

import org.garnishtest.demo.rest_complex.web.dao.model.AuthToken;
import org.apache.ibatis.annotations.Param;

public interface AuthTokensMapper {

    void createTokenForUser(@Param("userId") long userId,
                            @Param("authToken") AuthToken authToken);

}
