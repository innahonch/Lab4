package com.example.jwt;

import com.example.openidconnect.OpenIdConnectProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;

@Component
public class JwtDecoder {

    @Autowired
    private OpenIdConnectProperties openIdConnectProperties;

    @Autowired
    public JwtDecoder(OpenIdConnectProperties openIdConnectProperties) {
        this.openIdConnectProperties = openIdConnectProperties;
    }

    public Claims decodeToken(String token) {
        try {
            JWKSet jwkSet = JWKSet.load(new URL(openIdConnectProperties.getEndpoint() + "/.well-known/jwks"));
            if (jwkSet.getKeys().isEmpty()) {
                throw new RuntimeException("No keys found in JWK Set");
            }
            JWK jwk = jwkSet.getKeys().get(0);
            RSAPublicKey publicKey = (RSAPublicKey) ((RSAKey) jwk).toPublicKey();

            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims;
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode token", e);
        }
    }
}