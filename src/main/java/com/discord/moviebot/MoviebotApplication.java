package com.discord.moviebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MoviebotApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MoviebotApplication.class, args);

		DiscordFrontEnd discordFrontEnd = new DiscordFrontEnd();
	}

}
