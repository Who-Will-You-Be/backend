package com.example.data_analysisbackend.neis;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/neis")
public class NeisController {

    private static final String NEIS_BASE = "https://open.neis.go.kr";

    @GetMapping("/hub/{endpoint}")
    public ResponseEntity<String> proxy(
            @PathVariable String endpoint,
            HttpServletRequest request) {

        String query = request.getQueryString();
        String url = UriComponentsBuilder
                .fromUriString(NEIS_BASE + "/hub/" + endpoint)
                .query(query)
                .build(true)
                .toUriString();

        String body = RestClient.create()
                .get()
                .uri(URI.create(url))
                .retrieve()
                .body(String.class);

        return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(body);
    }
}
