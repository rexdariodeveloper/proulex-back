package com.pixvs.spring.config;

import com.pixvs.spring.models.JsonResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class JwtFilter extends GenericFilterBean {

    private static String secret = "secreto";
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    public JwtFilter(String secret){
        this.secret = secret;
    }

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader(HEADER);

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        } else {

            if (authHeader == null || !authHeader.startsWith(PREFIX)) {
                response.sendError(JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES,"Error de autentificación.\nFalta token de autorización");
                return;
            }


            try {
                final String token = authHeader.substring(PREFIX.length());
                final Claims claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();
                request.setAttribute("claims", claims);
            } catch (final SignatureException e) {
                response.sendError(JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES,"Error de autentificación.\n" + e.getMessage());
                return;
            }catch(MalformedJwtException e){
                response.sendError(JsonResponse.STATUS_ERROR_USUARIO_CREDENCIALES,"Error de autentificación.\n" + e.getMessage());
                return;
            }catch(Exception e){
                response.sendError(JsonResponse.STATUS_ERROR_SERVIDOR,"Error de autentificación.\n" + e.getMessage());
                return;
            }

            chain.doFilter(req, res);
        }
    }

    public static int getUsuarioId(HttpServletRequest req){

        try {
            String token = ((HttpServletRequest) req).getHeader("authorization").substring(7);
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();

            return (Integer) claims.get("id");
        }catch (Exception ex){
            return -1;
        }
    }


    public static Map<String, Object> getClaims(String token){

        try {
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();

            return claims;
        }catch (Exception ex){
            return null;
        }
    }

    public static Map<String, Object> getClaims(String token, String secret){

        try {
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();

            return claims;
        }catch (Exception ex){
            return null;
        }
    }

}

