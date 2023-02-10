package com.discord.moviebot;

import static com.discord.moviebot.Token.imdbApiToken;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.discord.moviebot.models.SearchData;
import com.discord.moviebot.models.TitleData;

@RestController
public class SearchController {
    
    @RequestMapping("/hello")
    @ResponseBody
    private String hello() {
        return "Hello World";
    }

    @GetMapping(value = "/search/{title}")
    @ResponseBody
    public SearchData getInceptionSearch(@PathVariable String title) {

        String uri = "https://imdb-api.com/en/API/SearchMovie/" + Token.imdbApiToken + "/" + title;
        RestTemplate restTemplate = new RestTemplate();
        SearchData searchResult = restTemplate.getForObject(uri, SearchData.class);

        printTitles(restTemplate, searchResult.results().get(0).id());

        return searchResult;
    }

    public void printTitles(RestTemplate restTemplate, String id) {
        
        String uri = "https://imdb-api.com/en/API/Title/" + Token.imdbApiToken + "/" + id;
        TitleData movieDetails = restTemplate.getForObject(uri, TitleData.class);

        System.out.println("/n");
        System.out.println(movieDetails.toString());
    }

}
