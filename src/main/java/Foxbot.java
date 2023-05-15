import events.InteractionEventListener;
import events.MemberJoinEventListener;
import events.MemberLeaveEventListener;
import events.ReadyEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Foxbot {
    public static void main(String[] args) throws LoginException {

        JDABuilder jdaBuilder = JDABuilder.createDefault("OTk1NzkxMjQzMDM4Njk1NTI2.GG8NQj.vE3DMmfL8tFYY7XViU4kouCv3DUvM4S2D-61AQ");
        jdaBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
        JDA jda = jdaBuilder
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new ReadyEventListener(), new InteractionEventListener(), new MemberJoinEventListener(), new MemberLeaveEventListener())
                .build();
        jda.getPresence().setActivity(Activity.playing("Developing FoxBot 3.0!"));

        jda.upsertCommand("ping", "Replies with pong!").setGuildOnly(true).queue();
        jda.upsertCommand("cancel-request", "Moves the ticket to the 'Cancellation Requests' Category").setGuildOnly(true).queue();
        jda.upsertCommand("setup", "Initializes all sticky messages").setGuildOnly(true).queue();
        jda.upsertCommand("complete", "Complete customer onboarding. Assigns \"Customer\" role. Closes onboarding ticket.")
                .addOption(OptionType.USER, "user", "This is the user you want to complete onboarding for", true)
                .addOption(OptionType.STRING, "server-number", "Provide the user's server number", true)
                .setGuildOnly(true).queue();
    }
}
