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
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.Command;
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
        jda.getPresence().setActivity(Activity.playing("Ready to Fly!"));

        jda.upsertCommand("cancel-request", "Moves the ticket to the 'Cancellation Requests' Category").setGuildOnly(true).queue();
        jda.upsertCommand("setup", "Initializes all sticky messages").setGuildOnly(true).queue();
        jda.upsertCommand("complete", "Complete customer onboarding. Assigns \"Customer\" role. Closes onboarding ticket.")
                .addOption(OptionType.USER, "user", "This is the user you want to complete onboarding for", true)
                .addOption(OptionType.STRING, "server-number", "Provide the user's server number", true)
                .setGuildOnly(true).queue();
        jda.updateCommands().addCommands(
                Commands.context(Command.Type.USER, "Open a Ticket")
        ).queue();
    }
}
