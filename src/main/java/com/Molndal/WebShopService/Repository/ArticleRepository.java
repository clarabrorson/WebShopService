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

    /**
     * Denna metod används för att hämta en artikel från databasen med ett specifikt id.
     *
     * @param articleId är id:t för den artikel som ska hämtas.
     * @return en artikel med det specifika id:t.
     */
    Optional<Article> findArticleById(Long articleId);

}
