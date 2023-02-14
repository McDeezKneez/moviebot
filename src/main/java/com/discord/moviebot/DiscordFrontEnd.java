package com.discord.moviebot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordFrontEnd {
    
    public DiscordFrontEnd() throws InterruptedException {

        JDA jda = JDABuilder.createDefault(Token.discordToken)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .addEventListeners(new DiscordReady()).build();

        jda.awaitReady();

        jda.addEventListener(new MessageListener());
    }
}
