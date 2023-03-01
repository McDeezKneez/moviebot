package com.discord.moviebot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.discord.moviebot.models.EmbedMessages;
import com.discord.moviebot.models.Results;
import com.discord.moviebot.models.SearchData;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

public class EventListeners extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(EventListeners.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Only start parsing messages that start with the ~ character
        String rawMessage = event.getMessage().getContentDisplay();
        if (rawMessage.startsWith("~")) {

            log.debug("Getting into the correct conditional");
            // Drop the first character and any leading or trailing spaces
            String trimmed = rawMessage.substring(1).trim();

            log.debug("Searching: " + trimmed);

            // We want to search for the movie and respond with the pictures and IDs
            String uri = "https://imdb-api.com/en/API/Search/" + Token.imdbApiToken + "/" + trimmed;
            RestTemplate restTemplate = new RestTemplate();
            SearchData searchResult = restTemplate.getForObject(uri, SearchData.class);

            // Create an embeded message for the first 3 results that will display useful info
            if (searchResult != null) {
                MessageChannel channel = event.getChannel();
                for (int i = 0; i < 4; i++) {

                    if (searchResult.results() == null || searchResult.results().get(i) == null) {
                        break;
                    }
                    Results curr = searchResult.results().get(i);
                    log.info("Found: " + curr.image());
                    channel.sendMessageEmbeds(EmbedMessages.buildEmbedMessage(curr).build()).queue();;
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("mcmovies")) {

            event.deferReply().queue();
            if (event.getOption("type").getAsString().equals("Show")) {

                event.getHook().sendMessage("Looking for a show with name: " + event.getOption("title").getAsString()).queue();
            } else {

                event.getHook().sendMessage("Looking for a movie with name: " + event.getOption("title").getAsString()).queue();
            }
        }
    }
}
