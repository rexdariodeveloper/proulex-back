package com.pixvs.spring.util;

import com.pixvs.spring.models.projections.Usuario.UsuarioLoginProjection;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWT {

    public static final String URL_SIIAU = "https://scrapapi.cucea.udg.mx/api/1.0/";
    public static final String SCRAPAPI_SECRET = "secret";

    public static String generaTokenPixvs(String issuer, UsuarioLoginProjection usuario, String secret) throws UnsupportedEncodingException {

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", usuario.getId());
        claims.put("correo", usuario.getCorreoElectronico());
        claims.put("idRol", usuario.getRolId());

        return generaToken(issuer, secret, "u/" + usuario.getId(), claims, SignatureAlgorithm.HS256, null);
    }

    public static String generaTokenSiiauCucea(String codigo, String pws, String ciclo, String secret) throws UnsupportedEncodingException {

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("typ", "JWT");

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("codigo", codigo);
        claims.put("psw", pws);
        claims.put("ciclo", ciclo);

        return generaToken(null, secret, null, claims, SignatureAlgorithm.HS256, headers);
    }

    public static String generaToken(String issuer, String secret, String Subject, Map<String, Object> claims, SignatureAlgorithm signature, Map<String, Object> headers) throws UnsupportedEncodingException {
        String jwt = Jwts.builder()
                .setIssuer(issuer)
                .setHeaderParams(headers)
                .setSubject(Subject)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .signWith(signature, secret.getBytes("UTF-8"))
                .compact();

        return jwt;
    }

    public static ResponseEntity<JSONObject> getDatosSiiau(String complemento) throws MalformedURLException {
        String url = URL_SIIAU+complemento;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(url, JSONObject.class);
        return response;
    }


}
