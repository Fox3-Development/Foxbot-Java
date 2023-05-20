package com.fox3ms.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public class MemberLeaveEventListener extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        super.onGuildMemberRemove(event);
        try {
            TextChannel auditLogsChannel = event.getGuild().getTextChannelById("1030481630411898890");
            String user = Objects.requireNonNull(event.getMember()).getUser().getAsTag();
            Member member = event.getMember();
            List<Role> roles = member.getRoles();
            StringBuilder rolesMentionable = new StringBuilder();
            for (Role role : roles) {
                rolesMentionable.append(role.getAsMention()).append(" ");
            }

            EmbedBuilder leaveEmbed = new EmbedBuilder();
                leaveEmbed.setColor(Color.RED);
                leaveEmbed.setAuthor(event.getMember().getEffectiveName(), null, member.getUser().getAvatarUrl());
                leaveEmbed.setTitle("Member left");
                leaveEmbed.setDescription("``" + user + "``" + " has left the server");
                leaveEmbed.addField("User's Roles", rolesMentionable.toString(), false);
                leaveEmbed.setThumbnail("https://static.wixstatic.com/media/54345b_9266a83269ec41dbadf793899fba1030~mv2.png/v1/fill/w_433,h_243,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/RedCard.png");
                leaveEmbed.setTimestamp(OffsetDateTime.now());

            assert auditLogsChannel != null;
            auditLogsChannel.sendMessageEmbeds(leaveEmbed.build()).complete();
        } catch ( NullPointerException e) {
            System.out.println("Caught NullPointerException: " + e.getMessage());
        }
    }
}
