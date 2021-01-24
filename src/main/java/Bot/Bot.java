package Bot;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.util.List;

public class Bot {



    private Bot() throws LoginException, SQLException {


        JDABuilder.createDefault(Config.get("token"))
                //.setEventManager(new AnnotatedEventManager())
                .addEventListeners(new Listen(),new LISTNER())
                .setActivity(Activity.watching("Anime"))
                .build();



    }

    public static void main(String[] args) throws LoginException, SQLException, InterruptedException {
        new Bot();

    }




}