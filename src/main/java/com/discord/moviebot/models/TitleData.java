package com.discord.moviebot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record TitleData(String id, String title, String year, String plot, String directors, String stars,
        String imDbRating, String metacriticRating) {
}
