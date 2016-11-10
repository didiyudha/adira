package com.adira.service.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Created by didiyudha on 10/11/16.
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String generateJwtToken(String id) {

        Key key = MacProvider.generateKey();

        String compactJws = Jwts.builder()
                .setSubject(id)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        return compactJws;
    }
}
