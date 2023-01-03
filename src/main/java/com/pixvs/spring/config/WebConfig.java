package com.pixvs.spring.config;

import com.pixvs.spring.handler.PixvsRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;

    /*
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        gsonHttpMessageConverter.setGson(gson);
        converters.add(gsonHttpMessageConverter);
    }
    */

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

    @Autowired
    private PixvsRequestInterceptor pixvsRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (environment.getProperty("environments.pixvs.logging.requests").equals("true") || environment.getProperty("environments.pixvs.logging.requests-slow").equals("true")) {
            registry.addInterceptor(pixvsRequestInterceptor)
                    .addPathPatterns("/**");
        }

    }
}
