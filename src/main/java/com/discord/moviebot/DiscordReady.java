package com.discord.moviebot;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class DiscordReady implements EventListener {
    
    @Override
    public void onEvent(GenericEvent event) {

        if (event instanceof ReadyEvent) {
            System.out.println("JDA is ready");
        }
    }
}
