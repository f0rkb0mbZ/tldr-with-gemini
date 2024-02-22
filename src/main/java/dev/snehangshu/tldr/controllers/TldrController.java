package dev.snehangshu.tldr.controllers;

import dev.snehangshu.tldr.services.FileHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
public class TldrController {

    @Autowired
    private FileHandlerService fileHandlerService;


    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadDocument(Model model, @RequestParam("file") MultipartFile doc, @RequestParam float creativity, @RequestParam int maxTokens) throws IOException {
        try {
            log.info("Processing file {} with {} creativity and {} maxTokens!", doc.getOriginalFilename(), creativity, maxTokens);
            String tldr = fileHandlerService.processFile(doc, creativity, maxTokens);
            model.addAttribute("message", tldr);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "index";
    }
}
