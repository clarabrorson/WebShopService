package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
