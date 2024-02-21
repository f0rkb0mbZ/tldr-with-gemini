package dev.snehangshu.tldr.services;


import com.fasterxml.jackson.databind.JsonNode;
import dev.snehangshu.tldr.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GeminiRestService {

    @Autowired
    private RestTemplate restTemplate;

    private String API_KEY = "AIzaSyDmfr0ceQDBdskBjiOctuxvDd7aAtNLGoA";

    public GeminiResponse chat(String docText) {
        GenerationConfig generationConfig = GenerationConfig.builder().temperature(0.9F).topK(1).topP(1.0F).maxOutputTokens(2048).build();
        List<SafetySetting> safetySettings = Arrays.asList(
                new SafetySetting("HARM_CATEGORY_HARASSMENT", "BLOCK_MEDIUM_AND_ABOVE"),
                new SafetySetting("HARM_CATEGORY_HATE_SPEECH", "BLOCK_MEDIUM_AND_ABOVE"),
                new SafetySetting("HARM_CATEGORY_SEXUALLY_EXPLICIT", "BLOCK_MEDIUM_AND_ABOVE"),
                new SafetySetting("HARM_CATEGORY_DANGEROUS_CONTENT", "BLOCK_MEDIUM_AND_ABOVE")
        );
        List<Content> chatHistory = Arrays.asList(
                new Content("user", Arrays.asList(new Part("There is a long text given below, it was converted from a pdf. There might be some weird spacing, newlines and unknown characters introduced because of the scan, try to ignore them. Summarise the text. Here goes the text -\n"+docText)))
        );
        GeminiRequest geminiRequest = GeminiRequest.builder().generationConfig(generationConfig).safetySettings(safetySettings).contents(chatHistory).build();
        GeminiResponse response = restTemplate.postForObject(String.format("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.0-pro:generateContent?key=%s", API_KEY), geminiRequest, GeminiResponse.class);
        System.out.println(response);
        return response;
    }
}
