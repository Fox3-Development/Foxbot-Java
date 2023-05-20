package com.fox3ms.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.managers.channel.ChannelManager;
import net.dv8tion.jda.api.managers.channel.concrete.TextChannelManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class InteractionEventListener extends ListenerAdapter {

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
    public void onButtonInteraction(@NotNull ButtonInteractionEvent buttonEvent) {
        super.onButtonInteraction(buttonEvent);
        System.out.println("A button was pressed!");
        switch (buttonEvent.getComponentId()) {
            case "wbID" -> {
                System.out.println("Welcome Button Pressed!");
                TextInput custNumInput = TextInput.create("cnID", "Provide customer number from welcome email", TextInputStyle.SHORT)
                        .setPlaceholder("ex. 1234567890")
                        .setRequiredRange(1, 10)
                        .setRequired(true)
                        .build();
                TextInput serverNumInput = TextInput.create("snID", "Please provide server number", TextInputStyle.SHORT)
                        .setPlaceholder("ex. A2-202")
                        .setRequiredRange(1, 20)
                        .setRequired(true)
                        .build();
                Modal onboardModal = Modal.create("omID", "Onboarding Request Ticket")
                        .addComponents(ActionRow.of(custNumInput), ActionRow.of(serverNumInput))
                        .build();
                buttonEvent.replyModal(onboardModal).queue();
            }
            case "tbID" -> {
                System.out.println("Create ticket button pressed!");
                TextInput complaintInput = TextInput.create("complaintID", "Please provide details about your issue:", TextInputStyle.PARAGRAPH)
                        .setPlaceholder("ex. My server isn't showing up in the webgui...")
                        .setRequired(true)
                        .setRequiredRange(10, 500)
                        .build();
                Modal ticketModal = Modal.create("ticketModalID", "Fox3 Support Ticket")
                        .addComponents(ActionRow.of(complaintInput))
                        .build();
                buttonEvent.replyModal(ticketModal).queue();
            }
            case "rbID" -> {
                Member interactionUser = buttonEvent.getMember();
                Role newCustRole = Objects.requireNonNull(buttonEvent.getGuild()).getRoleById("992687491029151774");

                assert interactionUser != null;
                if (interactionUser.getRoles().stream().anyMatch(role -> role.getName().equals("Customers")) || interactionUser.getRoles().stream().anyMatch(role -> role.getName().equals("New Customer"))) {
                    buttonEvent.reply("You've already accepted the rules!").setEphemeral(true).queue();
                } else if (interactionUser.getRoles().stream().noneMatch(role -> role.getName().equals("New Customer"))) {
                    assert newCustRole != null;
                    buttonEvent.getGuild().addRoleToMember(interactionUser, newCustRole).queue();
                    buttonEvent.reply("You have accepted the server rules. Please head over to the <#992255774036328488> to request onboarding!").setEphemeral(true).queue();
                }
            }
            case "closeID" -> {
                buttonEvent.reply("This ticket is closing in 5 seconds!").setEphemeral(false).queue();
                TextChannel ticketChanel = (TextChannel) buttonEvent.getChannel();
                ticketChanel.delete().queueAfter(2, TimeUnit.SECONDS);
            }
            case "closeReasonID" -> {
                Member interactionUser = buttonEvent.getMember();
                assert interactionUser != null;
                if (interactionUser.getRoles().stream().anyMatch(role -> role.getName().equals("Fox3 Staff"))) {
                    TextInput reasonInput = TextInput.create("reasonID", "Please provide the reason to close", TextInputStyle.PARAGRAPH)
                            .setPlaceholder("ex. Resolved")
                            .setRequired(true)
                            .setRequiredRange(1, 50)
                            .build();
                    Modal reasonModal = Modal.create("reasonModalID", "Ticket close reason")
                            .addComponents(ActionRow.of(reasonInput))
                            .build();
                    buttonEvent.replyModal(reasonModal).queue();
                } else {
                    buttonEvent.reply("You do not have perms to press this button!").setEphemeral(true).queue();
                }


            }
            case "claimID" -> {
                Member interactionUser = buttonEvent.getMember();
                System.out.println(interactionUser);
                assert interactionUser != null;
                if (interactionUser.getRoles().stream().anyMatch(role -> role.getName().equals("Fox3 Staff"))){
                    String mentionUser = interactionUser.getAsMention();


                    EmbedBuilder claimEmbed =new EmbedBuilder();
                    claimEmbed.setColor(Color.BLUE);
                    claimEmbed.setTitle("Claimed Ticket");
                    claimEmbed.setDescription("Your ticket will be handled by " + mentionUser);

                    Message message = buttonEvent.getMessage();

                    Button closeButton = Button.secondary("closeID", "Close Ticket");
                    Button closeReasonButton = Button.danger("closeReasonID", "Close Ticket With Reason");

                    message.editMessageComponents().setActionRow(closeReasonButton, closeButton).queue();

                    buttonEvent.replyEmbeds(claimEmbed.build()).queue();
                } else {
                    buttonEvent.reply("You do not have the perms to press this button!").setEphemeral(true).queue();
                }
            }
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent modalEvent) {
        super.onModalInteraction(modalEvent);
        System.out.println("A modal was submitted!");
        String newLine = System.lineSeparator();
        switch (modalEvent.getModalId()) {
            case "omID" -> {
                String onboardingID = Objects.requireNonNull(modalEvent.getMember()).getEffectiveName();

                String custNumInputValue = Objects.requireNonNull(modalEvent.getValue("cnID")).getAsString();
                String serverNumInputValue = Objects.requireNonNull(modalEvent.getValue("snID")).getAsString();

                String interactionUserID = modalEvent.getUser().getAsMention();


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

                TextChannel onboardTicketChannel = Objects.requireNonNull(modalEvent.getGuild()).createTextChannel("onboarding-" + onboardingID, modalEvent.getGuild().getCategoriesByName("onboarding requests", true).get(0)).complete();
                ChannelManager<TextChannel, TextChannelManager> onboardManager = onboardTicketChannel.getManager().putPermissionOverride(Objects.requireNonNull(modalEvent.getMember()), 3072L, 8192L).putPermissionOverride(modalEvent.getGuild().getRolesByName("@everyone", true).get(0), 0L, 1024L);
                onboardManager.queue();

                TextChannel alertChannel = modalEvent.getGuild().getTextChannelsByName("fox3-alerts", true).get(0);
                String staffID = Objects.requireNonNull(modalEvent.getGuild().getRoleById("1080684124693614654")).getAsMention();
                alertChannel.sendMessage(String.format("%s, **New Onboarding Alert!** | Ticket-" + onboardingID + " | Created by: " + interactionUserID, staffID)).queue();

                modalEvent.reply("Onboarding Request created! Click " + onboardTicketChannel.getAsMention() + " to open your ticket!").setEphemeral(true).queue();
                onboardTicketChannel.sendMessageEmbeds(onboardPanelEmbed.build()).complete();
            }
            case "ticketModalID" -> {
                String ticketID = Objects.requireNonNull(modalEvent.getMember()).getEffectiveName();

                String complaintInputValue = Objects.requireNonNull(modalEvent.getValue("complaintID")).getAsString();

                String interactionUserID = modalEvent.getUser().getAsMention();

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

                Button closeButton = Button.secondary("closeID", "Close Ticket");
                Button closeReasonButton = Button.danger("closeReasonID", "Close Ticket With Reason");
                Button claimButton = Button.success("claimID", "Claim");

                TextChannel ticketChannel = Objects.requireNonNull(modalEvent.getGuild()).createTextChannel("ticket-" + ticketID, modalEvent.getGuild().getCategoriesByName("open tickets", true).get(0)).complete();
                ChannelManager<TextChannel, TextChannelManager> ticketManager = ticketChannel.getManager().putPermissionOverride(Objects.requireNonNull(modalEvent.getMember()), 3072L, 8192L).putPermissionOverride(modalEvent.getGuild().getRolesByName("@everyone", true).get(0), 0L, 1024L);
                ticketManager.queue();

                TextChannel alertChannel = modalEvent.getGuild().getTextChannelsByName("fox3-alerts", true).get(0);
                String staffID = Objects.requireNonNull(modalEvent.getGuild().getRoleById("1080684124693614654")).getAsMention();
                alertChannel.sendMessage(String.format("%s, **New Ticket Alert!** | Ticket-" + ticketID + " | Created by: " + interactionUserID, staffID)).queue();

                modalEvent.reply("Support Ticket Created! Click " + ticketChannel.getAsMention() + " to open your ticket!").setEphemeral(true).queue();
                ticketChannel.sendMessageEmbeds(ticketPanelEmbed.build()).setActionRow(closeReasonButton, closeButton, claimButton).complete();
            }
            case "reasonModalID" -> {
                modalEvent.reply("This ticket is closing!").setEphemeral(false).queue();
                TextChannel ticketChanel = (TextChannel) modalEvent.getChannel();
                ticketChanel.delete().queueAfter(2, TimeUnit.SECONDS);
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
