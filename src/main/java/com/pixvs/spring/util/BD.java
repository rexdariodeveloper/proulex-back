package com.pixvs.spring.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BD {
    public static final String SQLSERVER = "sqlserver";
    public static final String POSTGRES = "postgres";


    public static String PLATAFORM;

    @Value("#{environment.getProperty(\"spring.datasource.platform\")}")
    public void setDatabase(String platform) {
        PLATAFORM = platform;
    }
}
