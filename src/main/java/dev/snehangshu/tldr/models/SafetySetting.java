package dev.snehangshu.tldr.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SafetySetting {
    private String category;
    private String threshold;
}
