package com.blog.api.security.jwt;

import cn.hutool.core.util.StrUtil;
import com.blog.api.security.SecurityUser;
import com.blog.api.security.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {



    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtils jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

       var header=jwtTokenUtil.getJwtSecurityProperties().getHeader();
        String authHeader = request.getHeader(header);
        if (authHeader != null && !StrUtil.isBlank(authHeader)) {
           if(authHeader.startsWith("Bearer")){
               authHeader=authHeader.substring(7);
               String username = jwtTokenUtil.getUsernameFromToken(authHeader);
               log.info(username);

               var context=SecurityContextHolder.getContext();
               if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                   UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                   if (jwtTokenUtil.validateToken(authHeader, (SecurityUser)userDetails)) {
                       UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                       authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                       SecurityContextHolder.getContext().setAuthentication(authentication);
                   }
               }
           }

        }
        chain.doFilter(request, response);
    }
}


