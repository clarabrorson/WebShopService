package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Article;
import com.Molndal.WebShopService.Repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service klass som används för att utföra operationer på tabellen articles.
 * Klassen innehåller metoder för att hämta, lägga till, uppdatera och ta bort artiklar från databasen.
 * Varje metod är kopplad till en specifik endpoint i ArticleController.
 *
 * @author Fredrik
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     * Denna metod används för att hämta alla artiklar från databasen.
     *
     * @return en lista med alla artiklar.
     */
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    /**
     * Denna metod används för att hämta en artikel från databasen med ett specifikt id.
     *
     * @param id är id:t för den artikel som ska hämtas.
     * @return en artikel med det specifika id:t.
     */
    public Article getOneArticle(Long id){
        return articleRepository.findArticleById(id).get();
    }

    /**
     * Denna metod används för att lägga till en artikel i databasen.
     *
     * @param article är den artikel som ska läggas till i databasen.
     * @return den artikel som har lagts till i databasen.
     */
    public Article addNewArticle(Article article) {
        return articleRepository.save(article);
    }

    /**
     * Denna metod används för att uppdatera en artikel i databasen.
     *
     * @param id är id:t för den artikel som ska uppdateras.
     * @param articleDetails är den artikel som innehåller den nya informationen.
     * @return den uppdaterade artikeln.
     */
    public Article updateArticle(Long id, Article articleDetails) {

        if (articleRepository.existsById(id)) {

            Article existingArticle = getOneArticle(id);

            existingArticle.setName(articleDetails.getName());
            existingArticle.setCost(articleDetails.getCost());
            existingArticle.setDescription(articleDetails.getDescription());

            return articleRepository.save(existingArticle);
        }
        else {
            return null;
        }
    }

    /**
     * Denna metod används för att ta bort en artikel från databasen.
     *
     * @param id är id:t för den artikel som ska tas bort.
     * @return en sträng som indikerar om artikeln har tagits bort eller inte.
     */
    public String deleteOneArticle(Long id) {
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
            return "Article with id: " + id + " was deleted";
        } else {
            return "Article with id: " + id + " does not exist";
        }
    }
}

