package com.baronswindle;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.msgpack.jackson.dataformat.MessagePackMapper;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;

public class MessagePackClient {
    final WebTarget target;

    public MessagePackClient(WebTarget target) {
        this.target = target
                .register(JacksonMessagePackProvider.class);
    }

    Map<String, Object> getResponse() throws IOException {
        var response = this.target
                .request("application/x-msgpack")
                .get();
        var bytes = response.readEntity(byte[].class);
        var mapper = new ObjectMapper(new MessagePackFactory());
        return mapper.readValue(bytes, new TypeReference<Map<String, Object>>() {});
    }

    @Provider
    @Consumes("application/x-msgpack")
    @Produces("application/x-msgpack")
    public static class JacksonMessagePackProvider extends JacksonJsonProvider {
        public JacksonMessagePackProvider() {
            super(new MessagePackMapper());
        }

        @Override
        protected boolean hasMatchingMediaType(MediaType mediaType) {
            return mediaType.getSubtype().equals("x-msgpack");
        }
    }
}
