package com.pi.appraisal.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    public String body;

    public Response(String body) {
        this.body = body;
    }

    public static ResponseEntity<Response> ok(String body) {
        return ResponseEntity.ok(new Response(body));
    }

    public static ResponseEntity<Response> status(HttpStatus status, String body) {
        return ResponseEntity.status(status).body(new Response(body));
    }
}
