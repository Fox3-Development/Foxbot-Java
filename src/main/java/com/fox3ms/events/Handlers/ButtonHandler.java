package com.fox3ms.events.Handlers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ButtonHandler extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        super.onButtonInteraction(event);
        switch (event.getComponentId()) {
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
                event.replyModal(onboardModal).queue();
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
                event.replyModal(ticketModal).queue();
            }
            case "rbID" -> {
                Member interactionUser = event.getMember();
                Role newCustRole = Objects.requireNonNull(event.getGuild()).getRolesByName("New Customer", false).get(0);

                assert interactionUser != null;
                if (interactionUser.getRoles().stream().anyMatch(role -> role.getName().equals("Customers")) || interactionUser.getRoles().stream().anyMatch(role -> role.getName().equals("New Customer"))) {
                    event.reply("You've already accepted the rules!").setEphemeral(true).queue();
                } else if (interactionUser.getRoles().stream().noneMatch(role -> role.getName().equals("New Customer"))) {
                    assert newCustRole != null;
                    event.getGuild().addRoleToMember(interactionUser, newCustRole).queue();
                    TextChannel welcomeChannel = event.getGuild().getTextChannelsByName("welcome", true).get(0);
                    event.reply(String.format("You have accepted the server rules. Please head over to the %s to request onboarding!", welcomeChannel.getAsMention())).setEphemeral(true).queue();
                }
            }
            case "cbID" -> {
                Member interactionUser = event.getMember();
                Role communityRole = Objects.requireNonNull(event.getGuild()).getRolesByName("Community Member", false).get(0);

                assert interactionUser != null;
                if (interactionUser.getRoles().stream().anyMatch(role -> role.getName().equals("Community Member"))) {
                    event.reply("You've already joined the community!").setEphemeral(true).queue();
                } else if (interactionUser.getRoles().stream().noneMatch(role -> role.getName().equals("Community Member"))) {
                    event.getGuild().addRoleToMember(interactionUser, communityRole).queue();
                    event.reply("Welcome to the community! If you are here for the Top Gun tournament or just here to hang out, no further action is required! Welcome to Fox3!").setEphemeral(true).queue();
                }
            }
            case "closeID" -> {
                //EmbedBuilder transcriptEmbed = new EmbedBuilder();


                event.reply("This ticket is closing in 5 seconds!").setEphemeral(false).queue();
                TextChannel ticketChanel = (TextChannel) event.getChannel();
                ticketChanel.delete().queueAfter(2, TimeUnit.SECONDS);
            }
            case "closeReasonID" -> {
                Member interactionUser = event.getMember();
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
                    event.replyModal(reasonModal).queue();
                } else {
                    event.reply("You do not have perms to press this button!").setEphemeral(true).queue();
                }


            }
            case "claimID" -> {
                Member interactionUser = event.getMember();
                System.out.println(interactionUser);
                assert interactionUser != null;
                if (interactionUser.getRoles().stream().anyMatch(role -> role.getName().equals("Fox3 Staff"))){
                    String mentionUser = interactionUser.getAsMention();


                    EmbedBuilder claimEmbed =new EmbedBuilder()
                        .setColor(Color.BLUE)
                        .setTitle("Claimed Ticket")
                        .setDescription("Your ticket will be handled by " + mentionUser);

                    Message message = event.getMessage();

                    net.dv8tion.jda.api.interactions.components.buttons.Button closeButton = net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("closeID", "Close Ticket");
                    net.dv8tion.jda.api.interactions.components.buttons.Button closeReasonButton = Button.danger("closeReasonID", "Close Ticket With Reason");

                    message.editMessageComponents().setActionRow(closeReasonButton, closeButton).queue();

                    event.replyEmbeds(claimEmbed.build()).queue();
                } else {
                    event.reply("You do not have the perms to press this button!").setEphemeral(true).queue();
                }
            }
        }
    }
}
