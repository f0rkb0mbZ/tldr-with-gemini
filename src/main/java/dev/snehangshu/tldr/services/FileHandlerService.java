package dev.snehangshu.tldr.services;

import dev.snehangshu.tldr.models.GeminiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileHandlerService {

    @Autowired
    private PDFExtractorService pdfExtractorService;

    @Autowired
    private GeminiRestService geminiRestService;

    public String processFile(MultipartFile doc) throws IOException {
        if (doc.isEmpty()) {
            throw new RuntimeException("Looks like your file attachment skills are taking a coffee break! Better luck next upload!");
//            return ResponseEntity.badRequest().body(Map.of("message", "Please select a document to upload"));
        }
        String contentType = doc.getContentType();
        if (!StringUtils.equalsAny(contentType, "application/pdf", "text/plain")) {
            throw new RuntimeException("Only text and pdf files are allowed!");
//            return ResponseEntity.badRequest().body(Map.of("message", "Only text and pdf files are allowed"));
        }

        String fileName = doc.getOriginalFilename();

        String uploadPath = "/Users/snehangshub/IdeaProjects/tldr/uploads/";
        File file = new File(uploadPath + fileName);
        doc.transferTo(file);

        String extractedText = pdfExtractorService.extractText(file);
        GeminiResponse chatResponse = geminiRestService.chat(extractedText);

        return chatResponse.getCandidates().getFirst().getContent().getParts().getFirst().getText();
    }
}
