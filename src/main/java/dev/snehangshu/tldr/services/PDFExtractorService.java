package dev.snehangshu.tldr.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class PDFExtractorService {
    public String extractText(File file) {
        try (PDDocument document = Loader.loadPDF(file)) {
            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new IOException("There is no permission to extract text from the document!");
            }
            PDFTextStripper stripper = new PDFTextStripper();
            System.out.println(stripper.getText(document).trim());
            return stripper.getText(document).trim();
        } catch (IOException e) {
            log.error("Error occurred while parsing PDF:", e);
            return null;
        }
    }
}
