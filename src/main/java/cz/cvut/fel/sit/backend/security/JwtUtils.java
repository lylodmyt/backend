package cz.cvut.fel.sit.backend.security;


import cz.cvut.fel.sit.backend.security.detailsImpl.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    static Logger LOG = LogManager.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;


    public String generateJwtToken(Authentication authentication){
        Date date = new Date();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject((userDetails.getUsername()))
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret).compact();

    }


    public boolean validateJwtToken(String jwt){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | SignatureException |
                 IllegalArgumentException e){
            LOG.error(e.getMessage());
        }
        return false;
    }


    public String getUsernameFromToken(String jwt){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
    }
}
