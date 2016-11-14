package com.adira.service.security;

import com.adira.dao.UserRepository;
import com.adira.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Created by didiyudha on 10/11/16.
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String generateJwtToken(String id) {

        Key key = MacProvider.generateKey();

        String compactJws = Jwts.builder()
                .setSubject(id)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        return compactJws;
    }

    @Override
    public User getUserLogedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        return user;
    }
}
