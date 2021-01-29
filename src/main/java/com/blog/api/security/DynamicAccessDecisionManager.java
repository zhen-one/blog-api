package com.blog.api.security;

import com.blog.api.model.Api;
import com.blog.api.model.Permission;
import com.blog.api.service.ApiService;
import com.blog.api.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    private PermissionService permissionService;


    @Autowired
    private ApiService apiService;

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    /**
     * 判定是否拥有权限的决策方法，
     *
     * @param authentication
     * @param o
     * @param collection     方法上hasRole注解的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {


        //权限从Db读取

        FilterInvocation fi = (FilterInvocation) o;
        String url = fi.getRequestUrl();//获取请求Url
        if(url=="/")return;
        for (String ignoreUrl : ignoreUrlsConfig.getUrls()) {
            if(url.startsWith(ignoreUrl)){
                return;
            }
        }

        String pureUrl=url;
        if(url.contains("?")){
            pureUrl=url.split("\\?")[0];
        }
        //需要权限访问的接口列表
        var managedApis = apiService.getByUrl(pureUrl);

        //請求的地址 在數據庫中不存在 跳過權限校驗
        if (managedApis.size() == 0) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                throw new AuthenticationCredentialsNotFoundException("");
            } else {
                return;
            }
        }


//        if(managedApis.stream().filter(n->n.getUrl()==url).count()==0)return;;

        //当前登录用户
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();

        var authorities = userDetails.getAuthorities();

        List<Permission> permissions = authorities
                .stream().map(authority -> ((CustomAuthority) authority).getPermission()).collect(Collectors.toList());

        if (authorities == null && authorities.size() == 0) {
            permissions = permissionService.getPermissionByUserid(userDetails.getUserid());
        }

        var matcher = new AntPathMatcher();

        //忽略不需要权限的url
        for (String ignoreUrl : ignoreUrlsConfig.getUrls()) {
            if (matcher.match(ignoreUrl, url)) return;
        }

        //根据url进行权限匹配
        for (Permission permission : permissions) {

            Api api = permission.getApi();
            if (api == null) continue;
            boolean matched = matcher.match(api.getUrl(), url);
            if (matched) {
                return;
            }
        }
        throw new AccessDeniedException("没有足够的权限");

    }

    /**
     * @param configAttribute
     * @return
     */
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    /**
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
