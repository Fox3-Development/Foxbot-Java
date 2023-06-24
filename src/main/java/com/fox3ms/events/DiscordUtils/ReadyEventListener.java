package com.fox3ms.events.DiscordUtils;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReadyEventListener extends ListenerAdapter {
    private boolean firstReady = true;

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        System.out.println("I've logged in successfully!");
        /*try {
            if (firstReady) {
                ScheduledExecutorService messageScheduler = Executors.newScheduledThreadPool(1);
                messageScheduler.scheduleAtFixedRate(() -> {
                    TextChannel scheduleChannel = event.getJDA().getTextChannelsByName("general", true).get(0);
                    String eventLink = "https://discord.gg/KfwGnWhZTj?event=1108181089232633967";
                    scheduleChannel.sendMessage(String.format("Have you heard about the **Fox3 Top Gun Tournament**? Check out the event to sign up! %s", eventLink)).queue();
                }, 0, 1, TimeUnit.DAYS);
                firstReady = false;
            }
        } catch ( Exception e) {
            System.out.println("Caught Exception, " + e.getMessage());
        }*/

    }
}
