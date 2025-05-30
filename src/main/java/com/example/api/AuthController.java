package com.example.api;

import com.example.openidconnect.OpenIdConnectProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class AuthController {
    private final OpenIdConnectProperties openIdConnectProperties;

    @Autowired
    public AuthController(OpenIdConnectProperties openIdConnectProperties) {
        this.openIdConnectProperties = openIdConnectProperties;
    }

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        System.out.println("Entering /login endpoint");
        String authorizeUrl = openIdConnectProperties.getEndpoint() + "/login/oauth/authorize" +
                "?client_id=" + openIdConnectProperties.getClientId() +
                "&response_type=code" +
                "&redirect_uri=" + openIdConnectProperties.getRedirectUri() +
                "&scope=openid profile email";
        System.out.println("Redirecting to: " + authorizeUrl);
        response.sendRedirect(authorizeUrl);
    }

    @GetMapping("/callback")
    public void callback(@RequestParam String code, HttpServletResponse response) throws IOException {
        System.out.println("Got code: " + code);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", code);
        formData.add("client_id", openIdConnectProperties.getClientId());
        formData.add("client_secret", openIdConnectProperties.getClientSecret());
        formData.add("redirect_uri", openIdConnectProperties.getRedirectUri());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        try {
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                    openIdConnectProperties.getEndpoint() + "/api/login/oauth/access_token",
                    request,
                    Map.class
            );
            System.out.println("Token response: " + tokenResponse.getBody());
            String accessToken = (String) tokenResponse.getBody().get("access_token");
            if (accessToken == null) {
                System.err.println("Access token is null in response: " + tokenResponse.getBody());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to get access token");
                return;
            }
            System.out.println("Setting cookie with access_token: " + accessToken);
            response.addHeader("Set-Cookie", "access_token=" + accessToken + "; Path=/");
            response.sendRedirect("/?loggedIn=true");
        } catch (Exception e) {
            System.err.println("Error in /callback: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Failed to obtain access token");
        }
    }
}