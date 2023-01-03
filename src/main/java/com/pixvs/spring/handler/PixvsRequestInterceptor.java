package com.pixvs.spring.handler;

import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.util.Utilidades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.time.Instant;

@Component
public class PixvsRequestInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PixvsRequestInterceptor.class);

    @Autowired
    private Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) request);
        long startTime = Instant.now().toEpochMilli();
        String info = "" + (idUsuario == null ? "?" : idUsuario) + "::" + request.getRequestURL().toString() +
                " ::S=" + Instant.now();
        logger.info(info);
        if (environment.getProperty("environments.pixvs.logging.requests").equals("true")) {
            Utilidades.logFile("pixvs-request", info, false);
        }
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) request);
        long startTime = (Long) request.getAttribute("startTime");

        DecimalFormat df = new DecimalFormat("0.00");
        String info = "" + (idUsuario == null ? "?" : idUsuario) + "::" + request.getRequestURL().toString() +
                " ::E=" + df.format(((Instant.now().toEpochMilli() - startTime)) * 0.001);
        logger.info(info);
        if (environment.getProperty("environments.pixvs.logging.requests").equals("true")) {
            Utilidades.logFile("pixvs-request", info, false);
        }

        if (environment.getProperty("environments.pixvs.logging.requests-slow").equals("true") &&
                (((Instant.now().toEpochMilli() - startTime)) * 0.001) > 3.00)
            Utilidades.logFile("pixvs-request-slow", info, false);

    }
}