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
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        SearchData searchResult = restTemplate.getForObject(uri, SearchData.class);

        System.out.println(searchResult);
        return searchResult;
    }
}
