package dev.snehangshu.tldr.services;


import dev.snehangshu.tldr.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class GeminiRestService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${API_KEY}")
    private String API_KEY;

    public GeminiResponse chat(String docText, float temperature, int maxTokens) {
        GenerationConfig generationConfig = GenerationConfig.builder().temperature(temperature).topK(1).topP(1.0F).maxOutputTokens(maxTokens).build();
        List<SafetySetting> safetySettings = Arrays.asList(
                new SafetySetting("HARM_CATEGORY_HARASSMENT", "BLOCK_MEDIUM_AND_ABOVE"),
                new SafetySetting("HARM_CATEGORY_HATE_SPEECH", "BLOCK_MEDIUM_AND_ABOVE"),
                new SafetySetting("HARM_CATEGORY_SEXUALLY_EXPLICIT", "BLOCK_MEDIUM_AND_ABOVE"),
                new SafetySetting("HARM_CATEGORY_DANGEROUS_CONTENT", "BLOCK_MEDIUM_AND_ABOVE")
        );
        List<Content> chatHistory = List.of(
                new Content("user", List.of(new Part("There are some long text appended to this prompt. Summarise the given text, respect the maxOutputTokens setting. Output only in markdown. Do not mention text like 'Here is a markdown summary of the given text -'. Here goes the text -\n" + docText)))
        );
        GeminiRequest geminiRequest = GeminiRequest.builder().generationConfig(generationConfig).safetySettings(safetySettings).contents(chatHistory).build();
        GeminiResponse response = restTemplate.postForObject(String.format("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.0-pro:generateContent?key=%s", API_KEY), geminiRequest, GeminiResponse.class);
        log.info("Gemini response received: {}", response);
        return response;
    }
}
