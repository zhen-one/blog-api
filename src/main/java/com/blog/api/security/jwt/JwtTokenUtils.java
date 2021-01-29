package com.blog.api.security.jwt;

import com.blog.api.dto.TokenDto;
import com.blog.api.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@Component
public class JwtTokenUtils {

    @Autowired
    private JwtSecurityProperties jwtSecurityProperties;
    private static final String AUTHORITIES_KEY = "auth";
    private String key;

    public JwtTokenUtils() {


    }


    private TokenDto.Token createToken(Map<String, Object> claims, long expire) {


        String token = Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS512, jwtSecurityProperties.getSecret())
                .compact();

        var tokenInfo = new TokenDto.Token();
        tokenInfo.setExpire(expire);
        tokenInfo.setToken(token);
        return tokenInfo;

    }

    private TokenDto.Token createToken(SecurityUser user) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", user.getUsername());
        claims.put("userid", user.getUserid());
        claims.put("created", new Date());
        long expire = jwtSecurityProperties.getExpiration() * 1000;
        return createToken(claims, expire);
    }

    private TokenDto.Token createRefreshToken(SecurityUser user) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", user.getUsername());
        claims.put("userid", user.getUserid());
        claims.put("created", new Date());
        long expire = jwtSecurityProperties.getExpiration() * 1000 * 10;
        return createToken(claims, expire);
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }


//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(key)
//                .parseClaimsJws(token)
//                .getBody();
//
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//        HashMap map =(HashMap) claims.get("auth");
//
//        User principal = new User(map.get("user").toString(), map.get("password").toString(), authorities);
//
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(this.getJwtSecurityProperties().getSecret()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    public TokenDto accessToken(SecurityUser user) {
        var accessToken = this.createToken(user);
        var refreshToken = this.createRefreshToken(user);
        var tokenDto = new TokenDto();
        tokenDto.setAccess_token(accessToken);
        tokenDto.setRefresh_token(refreshToken);
        return tokenDto;
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public TokenDto refreshToken(String token) {
        long expire = jwtSecurityProperties.getExpiration() * 1000;


        if (!validateToken(token) || this.isTokenExpired(token)) return null;

        Claims claims = getClaimsFromToken(token);
        claims.put("created", new Date());
        var accessToken = createToken(claims, expire);
        var refreshToken = createToken(claims, expire * 10);
        var tokenDto = new TokenDto();
        tokenDto.setAccess_token(accessToken);
        tokenDto.setRefresh_token(refreshToken);
        return tokenDto;

    }

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @return 是否有效
     */
    public Boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(this.jwtSecurityProperties.getSecret()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public Boolean validateToken(String token, SecurityUser user) {


        boolean userEqual = user.getUsername().equals(getUsernameFromToken(token));
        boolean validate = validateToken(token);
//         return  validateToken(token)&&user.getUserName()==getUsernameFromToken(token);
        return userEqual && validate;

    }


}

