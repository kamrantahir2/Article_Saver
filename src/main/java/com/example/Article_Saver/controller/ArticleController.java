package com.example.Article_Saver.controller;

import com.example.Article_Saver.model.Article;
import com.example.Article_Saver.service.ArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ArticleController {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ArticleService articleService;

    @GetMapping("/articles")
    ResponseEntity<String> getArticles() throws JsonProcessingException {
        List<Article> articles = articleService.getArticles();
        return new ResponseEntity<>(objectMapper.writeValueAsString(articles)
                , HttpStatus.OK);
    }


    @PostMapping("/articles")
    ResponseEntity<String> addArticle(@RequestBody Article article) throws JsonProcessingException {

        Optional<Article> opt = Optional.ofNullable(articleService.createArticle(article));

        if (opt.isPresent()) {
            Article presentArticle = opt.get();
            String jsonArticle = objectMapper.writeValueAsString(presentArticle)
            return new ResponseEntity<>(jsonArticle, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error creating Article", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}
