package com.discord.moviebot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record RatingData(String title, String imDb, String metacritic, String rottenTomatoes) {

}
