package com.discord.moviebot.models;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedMessages {
    
    public static EmbedBuilder buildEmbedMessage(Results result) {

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
}
