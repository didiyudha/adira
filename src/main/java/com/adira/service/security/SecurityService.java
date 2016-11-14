package com.adira.service.security;

import com.adira.entity.User;

/**
 * Created by didiyudha on 10/11/16.
 */
public interface SecurityService {
    User getUserLogedIn();

    String generateJwtToken(String id);
}
