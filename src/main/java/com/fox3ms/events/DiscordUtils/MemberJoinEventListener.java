package com.fox3ms.events.DiscordUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;

public class MemberJoinEventListener extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        super.onGuildMemberJoin(event);
        TextChannel welcomeChannel = event.getGuild().getTextChannelById("814940459134484514");
        String joinedUser = event.getUser().getAsMention();

        // disabled due to discord bug not showing member name mention. shows member ID instead
//        EmbedBuilder welcomeEmbed = new EmbedBuilder();
//            welcomeEmbed.setColor(Color.RED);
//            welcomeEmbed.setTitle("Welcome to Fox3 Managed Solutions!");
//            welcomeEmbed.setDescription("Welcome, " + joinedUser + ", to Fox3!");
//            welcomeEmbed.setThumbnail("https://static.wixstatic.com/media/54345b_9266a83269ec41dbadf793899fba1030~mv2.png/v1/fill/w_433,h_243,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/RedCard.png");
//            welcomeEmbed.setTimestamp(OffsetDateTime.now());

        Button linkUrlButton = Button.link("https://www.fox3ms.com", "Visit Fox3ms.com");

        assert welcomeChannel != null;
        welcomeChannel.sendMessage(String
                .format("Welcome, %s, to **Fox3 Managed Solutions!** Please head to " + event.getGuild()
                        .getTextChannelsByName("rules", true)
                        .get(0).getAsMention() + " to get started!", joinedUser))
                .setActionRow(linkUrlButton).complete();
    }
}
