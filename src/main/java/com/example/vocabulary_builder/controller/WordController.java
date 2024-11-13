package com.example.vocabulary_builder.controller;

import com.example.vocabulary_builder.model.Word;
import com.example.vocabulary_builder.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping("/words")
    public String getAllWords(Model model) {
        List<Word> words = wordService.getAllWords();
        model.addAttribute("words", words);
        return "wordList";  // Thymeleaf template name
    }
}
