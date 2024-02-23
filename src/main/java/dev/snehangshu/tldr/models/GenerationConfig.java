package dev.snehangshu.tldr.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerationConfig {
    private float temperature;
    private int topK;
    private float topP;
    private int maxOutputTokens;
    private List<Object> stopSequences;
}
