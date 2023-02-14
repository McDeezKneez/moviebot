package com.discord.moviebot;

import org.springframework.web.client.RestTemplate;

import com.discord.moviebot.models.Results;
import com.discord.moviebot.models.SearchData;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        // Only start parsing messages that start with the ~ character
        String rawMessage = event.getMessage().getContentDisplay();
        if (rawMessage.startsWith("~")) {

            System.out.println("Getting into the correct conditional");
            // Drop the first character and any spaces
            String trimmed = rawMessage.substring(1).trim();

            // See what the command is
            if (trimmed.toUpperCase().startsWith("SEARCH")) {

                // TODO: create a regex here that will parse and throw an error if not formatted
                // correctly
                String title = trimmed.split(":")[1].trim();

                System.out.println("Searching: " + title);
                // We want to search for the movie and respond with the pictures and IDs
                String uri = "https://imdb-api.com/en/API/SearchMovie/" + Token.imdbApiToken + "/" + title;
                RestTemplate restTemplate = new RestTemplate();
                SearchData searchResult = restTemplate.getForObject(uri, SearchData.class);

                System.out.println("Search type: " + searchResult.searchType());
                if (searchResult != null) {
                    MessageChannel channel = event.getChannel();
                    for (int i = 0; i < 4; i++) {

                        if (searchResult.results() == null || searchResult.results().get(i) == null) {
                            break;
                        }
                        Results curr = searchResult.results().get(i);
                        System.out.println("Found: " + curr.image());
                        channel.sendMessage(curr.image()).queue();
                    }
                }
            }
        }
    }
}
