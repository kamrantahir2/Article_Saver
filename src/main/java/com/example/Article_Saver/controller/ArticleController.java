package com.example.Article_Saver.controller;

import com.example.Article_Saver.model.Article;
import com.example.Article_Saver.service.ArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/articles/{id}")
    ResponseEntity<String> getArticleById(@PathVariable long id) throws JsonProcessingException {
        Optional<Article> opt = articleService.getArticleById(id);
        if(opt.isPresent()) {
            Article presentArticle = opt.get();
            String jsonArticle =
                    objectMapper.writeValueAsString(presentArticle);
            return new ResponseEntity<>(jsonArticle, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("Article not found",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/articles/favourites")
    ResponseEntity<String> getFavouriteArticles() throws JsonProcessingException {
        List<Article> favouriteArticles = articleService.getByFavourite();

        String jsonArticles =
                objectMapper.writeValueAsString(favouriteArticles);

        return new ResponseEntity<>(jsonArticles, HttpStatus.OK);
    }

    @PostMapping("/articles")
    ResponseEntity<String> addArticle(@RequestBody Article article) throws JsonProcessingException {

        Optional<Article> opt = Optional.ofNullable(articleService.createArticle(article));

        if (opt.isPresent()) {
            Article presentArticle = opt.get();
            String jsonArticle =
                    objectMapper.writeValueAsString(presentArticle);
            return new ResponseEntity<>(jsonArticle, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Error creating Article",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/articles/{id}")
    ResponseEntity<String> updateArticle(@PathVariable long id,
                                          @PathVariable Article article) throws JsonProcessingException {

        Optional<Article> opt = articleService.getArticleById(id);

        if (opt.isPresent()) {
            Article updatedArticle = articleService.updateArticle(id, article);

            String jsonUpdatedArticle = objectMapper.writeValueAsString(updatedArticle);

            return new ResponseEntity<>(jsonUpdatedArticle, HttpStatus.OK);

        } else {
           return new ResponseEntity<>("Article not found",
                   HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/articles/{id}")
    ResponseEntity<String> deleteArticle(@PathVariable long id) {
        Optional<Article> opt = articleService.getArticleById(id);

        if(opt.isPresent()) {
            Article foundArticle = opt.get();
            articleService.deleteArticle(id);
            return new ResponseEntity<>("Article '" + foundArticle.getName() + "' has been deleted", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Article not found",
                    HttpStatus.NOT_FOUND);
        }

    }

}
