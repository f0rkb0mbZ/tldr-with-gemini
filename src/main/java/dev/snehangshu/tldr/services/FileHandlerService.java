package dev.snehangshu.tldr.services;

import dev.snehangshu.tldr.models.GeminiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class FileHandlerService {

    @Autowired
    private PDFExtractorService pdfExtractorService;

    @Autowired
    private GeminiRestService geminiRestService;


    public String processFile(MultipartFile doc, float temperature, int maxTokens) throws IOException {
        if (doc.isEmpty()) {
            throw new RuntimeException("Looks like your file attachment skills are taking a coffee break! Better luck next upload!");
        }
        String contentType = doc.getContentType();
        if (!StringUtils.equalsAny(contentType, "application/pdf", "text/plain")) {
            throw new RuntimeException("Only text and pdf files are allowed!");
        }
        String extractedText = "";
        if (Objects.equals(contentType, "application/pdf")) {
            extractedText = pdfExtractorService.extractText(doc);
        } else if (Objects.equals(contentType, "text/plain")) {
            extractedText = new String(doc.getBytes(), StandardCharsets.UTF_8);
        }

        GeminiResponse chatResponse = geminiRestService.chat(extractedText, temperature, maxTokens);

        return chatResponse.getCandidates().getFirst().getContent().getParts().getFirst().getText().trim();
    }
}
