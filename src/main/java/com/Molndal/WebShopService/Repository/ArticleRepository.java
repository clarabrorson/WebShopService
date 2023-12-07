package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findArticleById(Long articleId);
}
