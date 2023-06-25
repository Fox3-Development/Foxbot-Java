package com.fox3ms;

import com.fox3ms.events.DiscordUtils.MemberJoinEventListener;
import com.fox3ms.events.DiscordUtils.MemberLeaveEventListener;
import com.fox3ms.events.DiscordUtils.ReadyEventListener;
import com.fox3ms.events.Handlers.ButtonHandler;
import com.fox3ms.events.Handlers.CommandHandler;
import com.fox3ms.events.Handlers.ModalHandler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Foxbot {
    public static void main(String[] args) {

        // Set the bot token with dotenv
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("TOKEN");

        // Create the JDA instance and add the bot token to log in
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);

        // Include project classes
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

        // Set bot status and presence
        jda.getPresence().setActivity(Activity.playing("Ready to Fly!"));

        // Register all commands
        jda.updateCommands().addCommands(
                Commands.slash("setup", "Initializes all sticky system messages")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .setGuildOnly(true),
                Commands.slash("cancel-request", "Moves the current ticket to the 'Cancellation Requests' Category then sends an alert")
                        .setGuildOnly(true),
                Commands.slash("complete", "Complete customer onboarding. Assigns 'Customer' role and adds server number to their name. Then " +
                        "closes onboarding ticket")
                        .addOption(OptionType.USER, "user", "User to complete onboarding for", true)
                        .addOption(OptionType.STRING, "server-number", "Provide the customer's server number", true)
                        .setGuildOnly(true)
        ).queue();
    }
}
