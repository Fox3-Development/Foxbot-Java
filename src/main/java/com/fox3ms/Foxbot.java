package com.fox3ms;

import events.InteractionEventListener;
import events.MemberJoinEventListener;
import events.MemberLeaveEventListener;
import events.ReadyEventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Foxbot {
    public static void main(String[] args) throws LoginException {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("TOKEN");
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
        JDA jda = jdaBuilder
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new ReadyEventListener(), new InteractionEventListener(), new MemberJoinEventListener(), new MemberLeaveEventListener())
                .build();
        jda.getPresence().setActivity(Activity.playing("Developing FoxBot 3.0!"));

        jda.upsertCommand("cancel-request", "Moves the ticket to the 'Cancellation Requests' Category").setGuildOnly(true).queue();
        jda.upsertCommand("setup", "Initializes all sticky messages").setGuildOnly(true).queue();
        jda.upsertCommand("complete", "Complete customer onboarding. Assigns \"Customer\" role. Closes onboarding ticket.")
                .addOption(OptionType.USER, "user", "This is the user you want to complete onboarding for", true)
                .addOption(OptionType.STRING, "server-number", "Provide the user's server number", true)
                .setGuildOnly(true).queue();
        jda.updateCommands().addCommands(
//                Commands.context(Command.Type.MESSAGE, "Opens A New Ticket")
                Commands.context(Command.Type.USER, "Open a Ticket")
        ).queue();
    }
}
