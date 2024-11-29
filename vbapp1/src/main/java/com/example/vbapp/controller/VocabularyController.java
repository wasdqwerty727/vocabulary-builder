package com.example.vbapp.controller;

import com.example.vbapp.model.User;
import com.example.vbapp.model.Word;
import com.example.vbapp.repository.UserRepository;
import com.example.vbapp.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VocabularyController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WordRepository wordRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This should match the name of your HTML file (without .html)
    }

    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String password, Model model) {
        User user = userRepository.findByName(name);

        if (user != null && user.getPassword().equals(password)) {
            // Successful login, redirect to user's words page
            return "redirect:/vocabulary/words/" + user.getId(); // Redirect to the user's words page
        } else {
            // Login failed, show error
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Return to login page with error
        }
    }

//    @GetMapping("/vocabulary/words/{userId}")
//    public String listWords(@PathVariable Long userId, Model model) {
//        List<Word> words = wordRepository.findByUserId(userId);
//        model.addAttribute("words", words);
//        model.addAttribute("userId", userId); // Pass userId to the view if needed
//        return "userWords"; // This should match the name of your HTML file (without .html)
//    }
@GetMapping("/vocabulary/words/{userId}")
public String listWords(@PathVariable Long userId, Model model) {
    List<Word> words = wordRepository.findByUserId(userId);

    // Calculate scores
    int totalWords = words.size();
    long learnedWords = words.stream().filter(Word::isLearned).count();
    long unlearnedWords = totalWords - learnedWords;

    // Add attributes to the model
    model.addAttribute("words", words);
    model.addAttribute("userId", userId);
    model.addAttribute("totalWords", totalWords);
    model.addAttribute("learnedWords", learnedWords);
    model.addAttribute("unlearnedWords", unlearnedWords);

    return "userWords"; // This should match the name of your HTML file (without .html)
}
    @PostMapping("/vocabulary/addWord")
    public String addWord(@RequestParam Long userId, @RequestParam String word, @RequestParam String translation) {
        // Create a new Word object
        Word newWord = new Word();
        newWord.setWord(word);
        newWord.setTranslation(translation);
        newWord.setLearned(false); // Default to unlearned

        // Set the user for the new word
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User  not found"));
        newWord.setUser (user);

        // Save the new word to the database
        wordRepository.save(newWord);

        // Redirect back to the user's words page
        return "redirect:/vocabulary/words/" + userId;
    }
    // Method to show the edit form
    @GetMapping("/vocabulary/edit/{wordId}")
    public String showEditForm(@PathVariable Long wordId, Model model) {
        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new RuntimeException("Word not found"));
        model.addAttribute("word", word);
        return "editWord"; // This should match the name of your edit Word HTML template
    }

    // Method to handle the update request
    @PostMapping("/vocabulary/updateWord")
    public String updateWord(@RequestParam Long id, @RequestParam String word, @RequestParam String translation) {
        Word existingWord = wordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Word not found"));
        existingWord.setWord(word);
        existingWord.setTranslation(translation);

        // Save the updated word to the database
        wordRepository.save(existingWord);

        // Redirect back to the user's words page
        return "redirect:/vocabulary/words/" + existingWord.getUser ().getId();
    }
    @PostMapping("/vocabulary/delete/{wordId}")
    public String deleteWord(@PathVariable Long wordId) {
        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new RuntimeException("Word not found"));

        // Delete the word from the database
        wordRepository.delete(word);

        // Redirect back to the user's words page
        return "redirect:/vocabulary/words/" + word.getUser ().getId();
    }
}