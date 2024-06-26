package com.example.Article_Saver.repository;

import com.example.Article_Saver.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByFavouriteTrue();

    Article findByName(String name);

    List<Article> findByNameContaining(String str);

}
