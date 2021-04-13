package com.blog.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "oauth")
@Data
@Component
public class OauthConfig {

    public ConfigItem gitee;

    public ConfigItem weibo;

    public ConfigItem github;
}

