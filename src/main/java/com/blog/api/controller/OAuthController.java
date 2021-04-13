package com.blog.api.controller;

import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.config.ConfigItem;
import com.blog.api.config.OauthConfig;
import com.blog.api.dto.LoginDto;
import com.blog.api.dto.TokenDto;
import com.blog.api.model.OauthUser;
import com.blog.api.model.SysUser;
import com.blog.api.service.SysUserService;
import com.github.dozermapper.core.Mapper;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeiboRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private OauthConfig oauthConfig;

    @Autowired
    protected Mapper dozerMapper;

    @RequestMapping("/render/{source}")
    public ResponseResult<String> renderAuth(@PathVariable String source,HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest(source);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        return ResponseUtil.success(authorizeUrl);
    }

    @RequestMapping(value = "/callback/{source}", method = RequestMethod.POST)
    public ResponseResult<Object> giteeLogin(
            @PathVariable String source,
            @RequestBody AuthCallback callback
    ) throws AuthenticationException {

        AuthRequest authRequest = getAuthRequest(source);
        var a = authRequest.login(callback);
        if (a.getCode() == 2000) {
            var userData = a.getData();

            var token = this.userService.oauthLogin((OauthUser) dozerMapper.map(userData, OauthUser.class));
            return ResponseUtil.success(token);
        } else {
            return ResponseUtil.fail(a.getMsg());
        }


    }

    private AuthRequest getAuthRequest(String source) {
        ConfigItem configItem=new ConfigItem();
        switch (source){
            case "gitee":{
                configItem=oauthConfig.gitee;
                return new AuthGiteeRequest(AuthConfig.builder()
                        .clientId(configItem.clientid)
                        .clientSecret(configItem.secret)
                        .redirectUri(configItem.redirect)
                        .build());

            }
            case "weibo":{
                configItem=oauthConfig.weibo;
                return new AuthWeiboRequest(AuthConfig.builder()
                        .clientId(configItem.clientid)
                        .clientSecret(configItem.secret)
                        .redirectUri(configItem.redirect)
                        .build());

            }
            case "github":{
                configItem=oauthConfig.github;
                return new AuthGithubRequest(AuthConfig.builder()
                        .clientId(configItem.clientid)
                        .clientSecret(configItem.secret)
                        .redirectUri(configItem.redirect)
                        .build());

            }
            default:{
                throw  new IllegalArgumentException("暂不支持平台类型"+source);
            }
        }

    }


}