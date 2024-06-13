package com.baronswindle;

import java.util.Map;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

public class JsonClient {
    final WebTarget target;

    public JsonClient(WebTarget target) {
        this.target = target
                .register(JacksonJsonProvider.class);
    }

    Map<String, Object> getResponse() {
        return this.target
                .request(MediaType.APPLICATION_JSON)
                .get()
                .readEntity(new GenericType<Map<String, Object>>() {
                });
    }
}
