package com.discord.moviebot.models;

import java.util.List;

public record SearchData(String searchType, String expression, List<Results> results, String errorMessage) {}
