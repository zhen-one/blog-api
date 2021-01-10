package com.blog.api.security;

import com.blog.api.model.SysUser;
import com.blog.api.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    private RedisTemplateHelper redisTemplate;

    @Autowired
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        SysUser user=userService.getByName(username);

        if(user == null){
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        }
//        List<SimpleGrantedAuthority> collect = user.getRoles().stream().map(Role::getRolename).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new SecurityUser(user);
//
//        String flagKey = "loginFailFlag:" + username;
//        String value = redisTemplate.get(flagKey);
//        Long timeRest = redisTemplate.getExpire(flagKey, TimeUnit.MINUTES);
//        if (StrUtil.isNotBlank(value)) {
//            // 超过限制次数
//            throw new LoginFailLimitException("登录错误次数超过限制，请" + timeRest + "分钟后再试");
//        }
//        User user;
//        if (NameUtil.mobile(username)) {
//            user = userService.findByMobile(username);
//        } else if (NameUtil.email(username)) {
//            user = userService.findByEmail(username);
//        } else {
//            user = userService.findByUsername(username);
//        }

    }
}