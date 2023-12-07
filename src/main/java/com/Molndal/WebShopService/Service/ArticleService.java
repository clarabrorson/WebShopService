package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getOneArticle(Long id){
        return articleRepository.findArticleById(id).get();
    }

    public Article addNewArticle(Article article) {
        return articleRepository.save(article);
    }

    //Bör lägga till felhantering i denna metod
    public Article updateArticle(Long id, Article articleDetails) {

        //Hämtar ut den befintliga artikeln
        Article existingArticle = getOneArticle(id);

        //Uppdaterar den befintliga artikeln
        existingArticle.setName(articleDetails.getName());
        existingArticle.setCost(articleDetails.getCost());
        existingArticle.setDescription(articleDetails.getDescription());

        //Returnerar och sparar den uppdaterade artikeln
        return articleRepository.save(existingArticle);
    }

    public String deleteOneArticle(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return "Article with id: " + id + " was deleted";
        } else {
            return "Article with id: " + id + " does not exist";
        }
    }
}

