package org.ContinuityIns.service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;

public interface AiService {
    String create(JsonNode message);
    Flux<String> getStream(String id);
    void stopChat(String id);
}
