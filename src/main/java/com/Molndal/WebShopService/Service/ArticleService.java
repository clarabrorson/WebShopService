package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article addNewArticle(Article article) {
        return articleRepository.save(article);
    }
}
