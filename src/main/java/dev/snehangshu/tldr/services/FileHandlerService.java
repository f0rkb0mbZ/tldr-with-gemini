package dev.snehangshu.tldr.services;

import dev.snehangshu.tldr.models.GeminiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileHandlerService {

    @Autowired
    private PDFExtractorService pdfExtractorService;

    @Autowired
    private GeminiRestService geminiRestService;


    public String processFile(MultipartFile doc, float temperature, int maxTokens) {
        if (doc.isEmpty()) {
            throw new RuntimeException("Looks like your file attachment skills are taking a coffee break! Better luck next upload!");
        }
        String contentType = doc.getContentType();
        if (!StringUtils.equalsAny(contentType, "application/pdf", "text/plain")) {
            throw new RuntimeException("Only text and pdf files are allowed!");
        }
        String extractedText = pdfExtractorService.extractText(doc);
        GeminiResponse chatResponse = geminiRestService.chat(extractedText, temperature, maxTokens);

        return chatResponse.getCandidates().getFirst().getContent().getParts().getFirst().getText().trim();
    }
}
