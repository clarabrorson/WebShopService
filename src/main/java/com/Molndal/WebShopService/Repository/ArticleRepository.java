package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Detta interface används för att utföra operationer på tabellen articles.
 *
 * @author Fredrik
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findArticleById(Long articleId);
}
