package com.fox3ms.events.Handlers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class CommandHandler extends ListenerAdapter {

    String newLine = System.lineSeparator();
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        System.out.println("Interaction!");
        switch (event.getName()) {
            case "ping" -> event.reply("Pong!").setEphemeral(false).queue();
            case "cancel-request" -> {
                TextChannel alertChannel = Objects.requireNonNull(event.getGuild()).getTextChannelsByName("fox3-alerts", true).get(0);
                Category cancelParent = Objects.requireNonNull(event.getGuild()).getCategoriesByName("cancellation requests", true).get(0);
                TextChannel ticketChannel = event.getGuild().getTextChannelsByName(event.getChannel().getName(), true).get(0);
                String alertUser = "264270923845074956";
                event.getJDA().getUserById(alertUser);
                ticketChannel.getManager().setParent(cancelParent).queue();
                event.reply("This ticket has been marked for cancellation! Your service cancellation will be processed by end of day.").setEphemeral(false).queue();
                alertChannel.sendMessage("<@264270923845074956>, " + event.getChannel().getAsMention() + " is pending cancellation!").queue();
            }
            case "setup" -> {
                TextChannel welcomeChannel = Objects.requireNonNull(event.getGuild()).getTextChannelsByName("welcome", true).get(0);
                TextChannel rulesChannel = event.getGuild().getTextChannelsByName("rules", true).get(0);
                TextChannel createTicketChannel = event.getGuild().getTextChannelsByName("\uD83C\uDFAB︱create-ticket", true).get(0);

                String welcomeDescription = "Fox3 provides fully managed dedicated DCS servers. To clarify, we don't consider ourselves as a hosting company. We see ourselves as a service provider." + newLine + newLine + "If you recently bought a server from our website, please read the directions below to start your onboarding.";
                String lineOne = "**1. **When your server has been provisioned, you'll receive a welcome letter via email. Be sure to check your spam folder. This email contains important information." + newLine + newLine + "**2. **When you have your welcome email and info ready, request an onboarding by pressing the button bellow!";


                EmbedBuilder welcomeEmbed = new EmbedBuilder();
                    welcomeEmbed.setColor(Color.red);
                    welcomeEmbed.setTitle("Welcome to Fox3 Managed Solutions!");
                    welcomeEmbed.setDescription(welcomeDescription);
                    welcomeEmbed.addBlankField(false);
                    welcomeEmbed.addField("Please follow the steps below to get started!", lineOne, false);
                    welcomeEmbed.addBlankField(false);
                    welcomeEmbed.addField("From the Fox3 Staff:", "Thank you so much for choosing Fox3 and we truly hope you enjoy this unique service we provide!", false);
                    welcomeEmbed.setThumbnail("https://static.wixstatic.com/media/54345b_9266a83269ec41dbadf793899fba1030~mv2.png/v1/fill/w_433,h_243,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/RedCard.png");

                Button welcomeButton = Button.primary("wbID", "Request Onboarding");

                welcomeChannel.sendMessageEmbeds(welcomeEmbed.build()).setActionRow(welcomeButton).complete();

                String rulesValue = "**1.** Be kind to other people here" + newLine + newLine + "**2**. Please do not discuss politics or religion" + newLine + newLine + "**3.** We are currently developing a feedback system. Until then, please open a support ticket to leave critical feedback about Fox3" + newLine + newLine + "**4.** Keep any jokes or memes to a clean level where one of my kids can see it" + newLine + newLine + "**5.** Please keep complaints of DCS to a minimum, those should be directed to Eagle Dynamics." + newLine + newLine + "**6.** We are a ~~small~~ medium sized community of DCS players, we should always be welcoming to everyone, including new players." + newLine + newLine + "**7.** Please review all pinned messages in <#814959240238071830> - that will help if you are a customer" + newLine + newLine + "**-Fox3 Staff**";

                EmbedBuilder rulesEmbed = new EmbedBuilder();
                    rulesEmbed.setColor(Color.RED);
                    rulesEmbed.setTitle("Fox3 Managed Solutions Discord Rules");
                    rulesEmbed.setDescription("Welcome to Fox3 Managed Solutions! Below you will find a short list of rules we ask you to follow.");
                    rulesEmbed.addBlankField(false);
                    rulesEmbed.addField("Discord Server Rules", rulesValue, false);
                    rulesEmbed.addBlankField(false);
                    rulesEmbed.setThumbnail("https://static.wixstatic.com/media/54345b_9266a83269ec41dbadf793899fba1030~mv2.png/v1/fill/w_433,h_243,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/RedCard.png");

                Button rulesButton = Button.success("rbID", "I ACCEPT");

                rulesChannel.sendMessageEmbeds(rulesEmbed.build()).setActionRow(rulesButton).complete();

                String ticketDescription = "Welcome to Fox3 Tech Support!" + newLine + newLine + "If you are looking for assistance with server issues, please click the button below to open a new ticket! This ticket channel is visible only to you and our excellent Fox3 staff.";
//                String fieldOne = "``Monday - Saturday``" + newLine + newLine + "``10am - 10pm EST``";
                EmbedBuilder ticketEmbed = new EmbedBuilder();
                    ticketEmbed.setColor(Color.RED);
                    ticketEmbed.setTitle("Fox3 Support");
                    ticketEmbed.setDescription(ticketDescription);
                    ticketEmbed.addBlankField(false);
                    ticketEmbed.addField("Tech Support Hours", "``Monday - Saturday``" + newLine + newLine + "``10am - 10pm EST``", true);
                    ticketEmbed.addField("Fox3 Support", "If you create a ticket and do not respond within 8 hours, your ticket will automatically be closed.", true);

                Button ticketButton = Button.primary("tbID", "\uD83C\uDFAB Create Ticket");

                createTicketChannel.sendMessageEmbeds(ticketEmbed.build()).setActionRow(ticketButton).complete();

                event.reply("All embeds sent to their channels!").setEphemeral(true).queue();
            }
            case "complete" -> {
                Member userValue = Objects.requireNonNull(event.getOption("user")).getAsMember();
                String serverNumberValue = Objects.requireNonNull(event.getOption("server-number")).getAsString();
                Role newCustRole = Objects.requireNonNull(event.getGuild()).getRoleById("992687491029151774");
                Role custRole = event.getGuild().getRoleById("814960014992474172");

                if (userValue != null) {
                    assert custRole != null;
                    Objects.requireNonNull(event.getGuild()).addRoleToMember(userValue, custRole).queue();
                } else {
                    event.getChannel().sendMessage("Error assigning " + null + " the ``Customers`` role!").queue();
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                assert newCustRole != null;
                assert userValue != null;
                event.getGuild().removeRoleFromMember(userValue, newCustRole).queue();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String newUser = userValue.getUser().getName() + " | " + serverNumberValue;
                userValue.modifyNickname(newUser).queue();
                String newMember = userValue.getUser().getAsMention();

                event.reply(String.format("%s has been successfully onboarded! Ticket closing in 5 seconds!", newMember)).queue();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                event.getChannel().delete().queueAfter(5, TimeUnit.SECONDS);
                }
            }
        }
    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent contextEvent) {
        super.onUserContextInteraction(contextEvent);
        if (contextEvent.getName().equals("Open a Ticket")) {
            TextInput complaintInput = TextInput.create("complaintID", "Please provide details about your issue:", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("ex. My server isn't showing up in the webgui...")
                    .setRequired(true)
                    .setRequiredRange(10, 500)
                    .build();
            Modal ticketModal = Modal.create("ticketModalID", "Fox3 Support Ticket")
                    .addComponents(ActionRow.of(complaintInput))
                    .build();
            contextEvent.replyModal(ticketModal).queue();
        }
    }
}
