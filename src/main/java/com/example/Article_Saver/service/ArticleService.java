package com.example.Article_Saver.service;

import com.example.Article_Saver.model.Article;
import com.example.Article_Saver.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(long id) {
        return articleRepository.findById(id);
    }

    public Article getArticleByName(String name) {
        return articleRepository.findByName(name);
    }

    public List<Article> getByFavourite() {
        return articleRepository.findByFavouriteTrue();
    }


}
