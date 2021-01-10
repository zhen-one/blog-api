package com.blog.api.controller;

import com.blog.api.model.SysUser;
import com.blog.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private SysUserService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object createAuthenticationToken(
            String username,String password
    ) throws AuthenticationException {
        //  @RequestBody JwtAuthenticationRequest authenticationRequest
        final String token = authService.login(username,password);

        // Return the token
        return token;
    }

//    @RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
//    public ResponseEntity<?> refreshAndGetAuthenticationToken(
//            HttpServletRequest request) throws AuthenticationException{
//        String token = request.getHeader(tokenHeader);
//        String refreshedToken = authService.refresh(token);
//        if(refreshedToken == null) {
//            return ResponseEntity.badRequest().body(null);
//        } else {
//            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
//        }
//    }
//
//    @RequestMapping(value = "${jwt.route.authentication.register}", method = RequestMethod.POST)
//    public SysUser register(@RequestBody SysUser addedUser) throws AuthenticationException{
//        return authService.register(addedUser);
//    }

}