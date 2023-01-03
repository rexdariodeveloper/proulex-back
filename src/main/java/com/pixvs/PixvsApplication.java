package com.pixvs;

import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching(proxyTargetClass = true)
public class PixvsApplication {

    @Autowired
    Environment environment;

    private static ConfigurableApplicationContext context;

    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {

        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter(environment.getProperty("environments.jwt.secret")));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }

    public static void main(String[] args) {
        inicializa();
        context = SpringApplication.run(PixvsApplication.class, args);
    }

    private static void inicializa() {
        //Log Pixvs Request
        try {
            File log = new File("pixvs-request.log");
            log.createNewFile();

            log = new File("pixvs-request-slow.log");
            log.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(PixvsApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }


}
