package com.blog.api.common;


import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerBeanConfig {

    @Bean
    public Mapper mapper() {
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        return mapper;
    }
}
