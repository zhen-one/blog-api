package com.blog.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


@SpringBootApplication()
@ComponentScan("com.blog.api")
@EntityScan("com.blog.api")
@EnableJpaRepositories("com.blog.api")
public class BlogApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(BlogApiApplication.class, args);

    }

//    public static void main(String[] args) throws URISyntaxException, IOException {
//        System.out.println("java.home : "+System.getProperty("java.home"));
//        System.out.println("java.class.version : "+System.getProperty("java.class.version"));
//        System.out.println("java.class.path : "+System.getProperty("java.class.path"));
//        System.out.println("java.library.path : "+System.getProperty("java.library.path"));
//        System.out.println("java.io.tmpdir : "+System.getProperty("java.io.tmpdir"));
//        System.out.println("java.compiler : "+System.getProperty("java.compiler"));
//        System.out.println("java.ext.dirs : "+System.getProperty("java.ext.dirs"));
//        System.out.println("user.name : "+System.getProperty("user.name"));
//        System.out.println("user.home : "+System.getProperty("user.home"));
//        System.out.println("user.dir : "+System.getProperty("user.dir"));
//        System.out.println("===================");
//        System.out.println("package: "+BlogApiApplication.class.getPackage().getName());
//        System.out.println("package: "+BlogApiApplication.class.getPackage().toString());
//        System.out.println("=========================");
//        String packName = BlogApiApplication.class.getPackage().getName();
//                /*URL packurl = new URL(packName);
//                System.out.println(packurl.getPath());*/
//        URI packuri = new URI(packName);
//        System.out.println(packuri.getPath());
//        //System.out.println(packuri.toURL().getPath());
//        System.out.println(packName.replaceAll("//.", "/"));
//        System.out.println(System.getProperty("user.dir")+"/"+(BlogApiApplication.class.getPackage().getName()).replaceAll("//.", "/")+"/");
//        System.in.read();
//    }

}
