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
    }
}
