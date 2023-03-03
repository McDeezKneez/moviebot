package com.discord.moviebot.models;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedMessages {

    public static EmbedBuilder buildEmbedSearchMessage(Results result) {

        EmbedBuilder eb = new EmbedBuilder();

        // Build the embed message
        eb.setTitle(result.title());
        eb.addField("Media type: ", result.resultType(), false);
        eb.addField("ID: ", result.id(), false);
        eb.addField("Description: ", result.description(), false);
        eb.setThumbnail(result.image());

        // I want to add a button here that when pressed will display more information

        return eb;
    }

    public static EmbedBuilder buildEmbedTitleMessage(TitleData title) {

        EmbedBuilder eb = new EmbedBuilder();

        // Build it
        eb.setTitle(title.title());
        eb.addField("Year: ", title.year(), false);
        eb.addField("Plot: ", title.plot(), false);
        eb.addField("Directors: ", title.directors(), false);
        eb.addField("Stars: ", title.stars(), false);

        if (title.ratings() != null) {
            eb.addField("IMDb:", title.ratings().imDb(), true);
            eb.addField("Metacritic:", title.ratings().metacritic(), true);
            eb.addField("RottenTomatoes:", title.ratings().rottenTomatoes(), true);
        }
        eb.setThumbnail(title.image());

        return eb;
    }
}
