package com.discord.moviebot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class DiscordReady implements EventListener {
    
    private static final Logger log = LoggerFactory.getLogger(DiscordReady.class);
    @Override
    public void onEvent(GenericEvent event) {

        if (event instanceof ReadyEvent) {
            log.info("JDA is ready");
        }
    }
}
