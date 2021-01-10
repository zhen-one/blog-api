package com.blog.api.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtSecurityProperties {

    /** Request Headers ： Authorization */
    private String header;




    /** Base64对该令牌进行编码 */
    private String secret;

    /** 令牌过期时间 此处单位/秒 */
    private Long expiration;



}
