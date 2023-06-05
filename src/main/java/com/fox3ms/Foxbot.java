package com.fox3ms;

import com.fox3ms.events.Handlers.ButtonHandler;
import com.fox3ms.events.Handlers.CommandHandler;
import com.fox3ms.events.DiscordUtils.MemberJoinEventListener;
import com.fox3ms.events.DiscordUtils.MemberLeaveEventListener;
import com.fox3ms.events.DiscordUtils.ReadyEventListener;
import com.fox3ms.events.Handlers.ModalHandler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Foxbot {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("TOKEN");
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
        JDA jda = jdaBuilder
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(
                        new ReadyEventListener(),
                        new CommandHandler(),
                        new MemberJoinEventListener(),
                        new MemberLeaveEventListener(),
                        new ButtonHandler(),
                        new ModalHandler()
                ).build();

        jda.getPresence().setActivity(Activity.playing("Ready to Fly!")); //Sets the online presence for the bot

        jda.updateCommands().addCommands(
                Commands.slash("setup", "Initializes system embeds and buttons")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .setGuildOnly(true),
                Commands.slash("cancel-request", "Moves the ticket to the 'Cancellation Requests' Category")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .setGuildOnly(true),
                Commands.slash("complete", "Completes customer onboarding")
                        .addOption(OptionType.USER, "user", "The target user to complete onboarding for")
                        .addOption(OptionType.STRING, "server-number", "Provide the customer's server number")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .setGuildOnly(true),
                Commands.slash("search", "Searches for and displays customer info") // TODO: #1 create jdbc connection and test searching for customers
                        .addOption(OptionType.USER, "customer-name", "User to search for")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .setGuildOnly(true),
                Commands.context(Command.Type.USER, "Open a Ticket")
                        .setGuildOnly(true)
        ).queue();
    }
}
