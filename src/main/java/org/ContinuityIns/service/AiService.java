package org.ContinuityIns.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface AiService {
    String create(JsonNode message, String model);
    Flux<String> getStream(String id);
    void stopChat(String id);
}
