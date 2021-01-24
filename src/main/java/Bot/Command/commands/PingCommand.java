package Bot.Command.commands;

import Bot.Command.CommandContext;
import Bot.Command.Icommand;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements Icommand {

    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping) -> ctx.getChannel()
                .sendMessageFormat("Rest ping: %sms\nWS ping: %sms",ping, jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp(){
        return "Shows the current ping from the bot to the discord server";
    }
}
