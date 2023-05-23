package com.fox3ms.events.Handlers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.managers.channel.ChannelManager;
import net.dv8tion.jda.api.managers.channel.concrete.TextChannelManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ModalHandler extends ListenerAdapter {
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        super.onModalInteraction(event);
        String newLine = System.lineSeparator();
        switch (event.getModalId()) {
            case "omID" -> {
                String onboardingID = Objects.requireNonNull(event.getMember()).getEffectiveName();

                String custNumInputValue = Objects.requireNonNull(event.getValue("cnID")).getAsString();
                String serverNumInputValue = Objects.requireNonNull(event.getValue("snID")).getAsString();

                String interactionUserID = event.getUser().getAsMention();


                EmbedBuilder onboardPanelEmbed = new EmbedBuilder();
                onboardPanelEmbed.setColor(Color.RED);
                onboardPanelEmbed.setTitle(String.format("Onboarding Request Ticket Panel: %s", onboardingID));
                onboardPanelEmbed.setDescription("A staff member will be with you as soon as possible!");
                onboardPanelEmbed.addField("Ticket Created By:", interactionUserID, true);
                onboardPanelEmbed.addField("Support Hours", "``Monday - Saturday``" + newLine + "``10am - 10pm EST``", true);
                onboardPanelEmbed.addBlankField(false);
                onboardPanelEmbed.addField("Customer Number", custNumInputValue, true);
                onboardPanelEmbed.addField("Server Number", serverNumInputValue, true);
                onboardPanelEmbed.setThumbnail("https://static.wixstatic.com/media/54345b_9266a83269ec41dbadf793899fba1030~mv2.png/v1/fill/w_433,h_243,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/RedCard.png");
                onboardPanelEmbed.setFooter("If you've submitted this outside of business hours, a staff member will respond the next business day during Support Hours.");
                onboardPanelEmbed.setTimestamp(OffsetDateTime.now());

                TextChannel onboardTicketChannel = Objects.requireNonNull(event.getGuild()).createTextChannel("onboarding-" + onboardingID, event.getGuild().getCategoriesByName("onboarding requests", true).get(0)).complete();
                ChannelManager<TextChannel, TextChannelManager> onboardManager = onboardTicketChannel.getManager().putPermissionOverride(Objects.requireNonNull(event.getMember()), 3072L, 8192L).putPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0), 0L, 1024L);
                onboardManager.queue();

                TextChannel alertChannel = event.getGuild().getTextChannelsByName("fox3-alerts", true).get(0);
                String staffID = Objects.requireNonNull(event.getGuild().getRoleById("1080684124693614654")).getAsMention();
                alertChannel.sendMessage(String.format("%s, **New Onboarding Alert!** | Ticket-" + onboardingID + " | Created by: " + interactionUserID, staffID)).queue();

                event.reply("Onboarding Request created! Click " + onboardTicketChannel.getAsMention() + " to open your ticket!").setEphemeral(true).queue();
                onboardTicketChannel.sendMessageEmbeds(onboardPanelEmbed.build()).complete();
            }
            case "ticketModalID" -> {
                String ticketID = Objects.requireNonNull(event.getMember()).getEffectiveName();

                String complaintInputValue = Objects.requireNonNull(event.getValue("complaintID")).getAsString();

                String interactionUserID = event.getUser().getAsMention();

                EmbedBuilder ticketPanelEmbed = new EmbedBuilder();
                ticketPanelEmbed.setColor(Color.RED);
                ticketPanelEmbed.setTitle("Support Ticket Panel: " + ticketID);
                ticketPanelEmbed.setDescription("A Fox3 Staff member will assist you as soon as possible!");
                ticketPanelEmbed.addField("Support Hours", "``Monday - Saturday``" + newLine + "``10am - 10pm EST``", true);
                ticketPanelEmbed.addField("\u200b", "\u200b", true);
                ticketPanelEmbed.addField("Ticket Created by", interactionUserID, true);
                ticketPanelEmbed.addField("Support Issue", "``" + complaintInputValue + "``", false);
                ticketPanelEmbed.setThumbnail("https://static.wixstatic.com/media/54345b_9266a83269ec41dbadf793899fba1030~mv2.png/v1/fill/w_433,h_243,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/RedCard.png");
                ticketPanelEmbed.setFooter("If you've submitted this ticket outside of business hours, a staff member will respond the next business during Support Hours.");
                ticketPanelEmbed.setTimestamp(OffsetDateTime.now());

                net.dv8tion.jda.api.interactions.components.buttons.Button closeButton = net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("closeID", "Close Ticket");
                net.dv8tion.jda.api.interactions.components.buttons.Button closeReasonButton = net.dv8tion.jda.api.interactions.components.buttons.Button.danger("closeReasonID", "Close Ticket With Reason");
                net.dv8tion.jda.api.interactions.components.buttons.Button claimButton = Button.success("claimID", "Claim");

                TextChannel ticketChannel = Objects.requireNonNull(event.getGuild()).createTextChannel("ticket-" + ticketID, event.getGuild().getCategoriesByName("open tickets", true).get(0)).complete();
                ChannelManager<TextChannel, TextChannelManager> ticketManager = ticketChannel.getManager().putPermissionOverride(Objects.requireNonNull(event.getMember()), 3072L, 8192L).putPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0), 0L, 1024L);
                ticketManager.queue();

                TextChannel alertChannel = event.getGuild().getTextChannelsByName("fox3-alerts", true).get(0);
                String staffID = Objects.requireNonNull(event.getGuild().getRolesByName("Fox3 Staff", false).get(0)).getAsMention();
                alertChannel.sendMessage(String.format("%s, **New Ticket Alert!** | Ticket-" + ticketID + " | Created by: " + interactionUserID, staffID)).queue();

                event.reply("Support Ticket Created! Click " + ticketChannel.getAsMention() + " to open your ticket!").setEphemeral(true).queue();
                ticketChannel.sendMessageEmbeds(ticketPanelEmbed.build()).setActionRow(closeReasonButton, closeButton, claimButton).complete();
            }
            case "reasonModalID" -> {
                event.reply("This ticket is closing!").setEphemeral(false).queue();
                TextChannel ticketChanel = (TextChannel) event.getChannel();
                ticketChanel.delete().queueAfter(2, TimeUnit.SECONDS);
            }
        }
    }
}
