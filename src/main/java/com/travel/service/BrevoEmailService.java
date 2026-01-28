package com.travel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BrevoEmailService {

  @Value("${brevo.api.key}")
  private String apiKey;

  @Value("${brevo.sender.email}")
  private String senderEmail;

  @Value("${brevo.sender.name}")
  private String senderName;

  private final RestTemplate restTemplate = new RestTemplate();

  public void sendResetPasswordEmail(String toEmail, String resetLink) {
    String url = "https://api.brevo.com/v3/smtp/email";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("api-key", apiKey);

    Map<String, Object> body = new HashMap<>();
    body.put("sender", Map.of("name", senderName, "email", senderEmail));
    body.put("to", List.of(Map.of("email", toEmail)));
    body.put("subject", "Réinitialisation de votre mot de passe");

    String html = """
      <div style="font-family:Arial,sans-serif;line-height:1.6">
        <h2>Réinitialisation du mot de passe</h2>
        <p>Vous avez demandé la réinitialisation de votre mot de passe.</p>
        <p>
          <a href="%s" style="display:inline-block;padding:10px 16px;background:#1e88e5;color:#fff;text-decoration:none;border-radius:6px">
            Réinitialiser mon mot de passe
          </a>
        </p>
        <p>Ce lien expire dans <b>30 minutes</b>.</p>
        <p>Si vous n'êtes pas à l'origine de cette demande, ignorez cet email.</p>
        <hr/>
        <small>Travel App</small>
      </div>
    """.formatted(resetLink);

    body.put("htmlContent", html);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

    ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

    if (!res.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Brevo email failed: " + res.getBody());
    }
  }
}
