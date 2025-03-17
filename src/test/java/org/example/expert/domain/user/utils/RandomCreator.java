package org.example.expert.domain.user.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@Component
public class RandomCreator {

    private List<String> adjectives;
    private List<String> nouns;
    private final int MAX_NUMBER = 9999;
    private final Random random = new Random();

    public RandomCreator() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(new File("src/test/java/org/example/expert/domain/user/utils/words.json"));

        adjectives = objectMapper.readValue(node.get("adjectives").toString(), List.class);
        nouns = objectMapper.readValue(node.get("nouns").toString(), List.class);
    }

    public String generateNickname() {
        String adjective = adjectives.get(random.nextInt(adjectives.size()));
        String noun = nouns.get(random.nextInt(nouns.size()));
        int number = random.nextInt(MAX_NUMBER) + 1;
        return adjective + noun + number;
    }
}
