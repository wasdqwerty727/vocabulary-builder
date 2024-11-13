package com.example.vocabulary_builder.service;

import com.example.vocabulary_builder.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Word> getAllWords() {
        String sql = "SELECT * FROM words";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Word word = new Word();
            word.setId(rs.getLong("id"));
            word.setWord(rs.getString("word"));
            word.setDefinition(rs.getString("definition"));
            word.setPhoneticTranscription(rs.getString("phonetic_transcription"));
            word.setExampleSentence(rs.getString("example_sentence"));
            return word;
        });
    }
}
