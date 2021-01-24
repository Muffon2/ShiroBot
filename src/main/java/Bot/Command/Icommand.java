package Bot.Command;

import java.util.List;

public interface Icommand {
    void handle(CommandContext ctx);

    String getName();

    String getHelp();

    default List<String> getAliases(){
        return List.of();
    }
}
