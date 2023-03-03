package com.discord.moviebot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.discord.moviebot.models.EmbedMessages;
import com.discord.moviebot.models.Results;
import com.discord.moviebot.models.SearchData;
import com.discord.moviebot.models.TitleData;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

public class EventListeners extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(EventListeners.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Only start parsing messages that start with the ~ character
        String rawMessage = event.getMessage().getContentDisplay();
        if (rawMessage.startsWith("~")) {

            log.info("Getting into the correct conditional");
            // Drop the first character and any leading or trailing spaces
            String trimmed = rawMessage.substring(1).trim();

            log.info("Searching: " + trimmed);

            // We want to search for the movie and respond with the first results data
            String searchUri = "https://imdb-api.com/en/API/Search/" + Token.imdbApiToken + "/" + trimmed;
            RestTemplate restTemplate = new RestTemplate();
            SearchData searchResult = restTemplate.getForObject(searchUri, SearchData.class);

            // Make another api call to get the first result's full data
            if (searchResult != null && searchResult.results() != null && searchResult.results().get(0) != null) {

                MessageChannel channel = event.getChannel();
                String titleId = searchResult.results().get(0).id();

                log.info("Searching for id: " + titleId);
                String titleUri = "https://imdb-api.com/en/API/Title/" + Token.imdbApiToken + "/"
                        + titleId + "/Ratings";
                TitleData titleData = restTemplate.getForObject(titleUri, TitleData.class);

                channel.sendMessageEmbeds(EmbedMessages.buildEmbedTitleMessage(titleData).build())
                        .addActionRow(Button.link("https://www.imdb.com/title/" + titleId, "IMDb page")).queue();
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("mcmovies")) {

            event.deferReply().queue();

            String trimmed = event.getOption("title").getAsString().trim();

            if (event.getOption("type").getAsString().equals("Show")) {

                event.getHook().sendMessage("Looking for shows with name: " + trimmed)
                        .queue();
                // We want to search for the movie and respond with the pictures and IDs
                String uri = "https://imdb-api.com/en/API/SearchSeries/" + Token.imdbApiToken + "/" + trimmed;
                RestTemplate restTemplate = new RestTemplate();
                SearchData searchResult = restTemplate.getForObject(uri, SearchData.class);

                // Create an embeded message for the first 3 results that will display useful
                // info
                if (searchResult != null) {
                    MessageChannel channel = event.getChannel();
                    for (int i = 0; i < 4; i++) {

                        if (searchResult.results() == null || searchResult.results().get(i) == null) {
                            break;
                        }
                        Results curr = searchResult.results().get(i);
                        log.info("Found: " + curr.image());
                        channel.sendMessageEmbeds(EmbedMessages.buildEmbedSearchMessage(curr).build()).queue();

                        // put a button here that will review this movie
                        MessageCreateData mcb = new MessageCreateBuilder()
                                .addActionRow(Button.primary(searchResult.results().get(i).id(),
                                        "Review: " + searchResult.results().get(i).title() + ": "
                                                + searchResult.results().get(i).description()))
                                .build();
                        channel.sendMessage(mcb).queue();

                    }
                }
            } else if (event.getOption("type").getAsString().equals("Movie")) {

                event.getHook().sendMessage("Looking for movies with name: " + trimmed)
                        .queue();

                // We want to search for the movie and respond with the pictures and IDs
                String uri = "https://imdb-api.com/en/API/SearchMovie/" + Token.imdbApiToken + "/" + trimmed;
                RestTemplate restTemplate = new RestTemplate();
                SearchData searchResult = restTemplate.getForObject(uri, SearchData.class);

                // Create an embeded message for the first 3 results that will display useful
                // info
                if (searchResult != null) {
                    MessageChannel channel = event.getChannel();
                    for (int i = 0; i < 4; i++) {

                        if (searchResult.results() == null || searchResult.results().get(i) == null) {
                            break;
                        }
                        Results curr = searchResult.results().get(i);
                        log.info("Found: " + curr.image());
                        channel.sendMessageEmbeds(EmbedMessages.buildEmbedSearchMessage(curr).build()).queue();

                        // put a button here that will review this movie
                        MessageCreateData mcb = new MessageCreateBuilder()
                                .addActionRow(Button.primary(searchResult.results().get(i).id(),
                                        "Review: " + searchResult.results().get(i).title() + ": "
                                                + searchResult.results().get(i).description()))
                                .build();
                        channel.sendMessage(mcb).queue();
                        ;

                    }
                }
            } else {

                // User didnt specify either show or movie, send error msg
                event.getHook().sendMessage("Invalid input, please choose either show or movie.")
                        .queue();
            }
        }
    }
}
