package com.discord.moviebot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordFrontEnd {

    public DiscordFrontEnd() throws InterruptedException {

        JDA jda = JDABuilder.createDefault(Token.discordBotToken)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new DiscordReady()).build();

        jda.awaitReady();

        jda.updateCommands().addCommands(
                Commands.slash("mcmovies", "Search for and/or review movies or shows.")
                        .addOptions(new OptionData(OptionType.STRING, "type", "Specify movie or show", true)
                                .addChoice("Movie", "Movie")
                                .addChoice("Show", "Show"))
                        .addOption(OptionType.STRING, "title", "The title of the movie or show", true))
                .queue();

        jda.addEventListener(new EventListeners());
    }
}
