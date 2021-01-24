package Bot;



import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.concurrent.TimeUnit;


public class LISTNER extends ListenerAdapter {

  private static JSONObject obj = new JSONObject();


    @Override
    public void onReady(@NotNull ReadyEvent event) {
        String jsonFileStr = null;
        try {
            jsonFileStr = new String(
                    Files.readAllBytes(Paths
                            .get("test.json")),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonFileStr);
        try {
            obj = (JSONObject) new JSONParser().parse(jsonFileStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {


        String Id = event.getMember().getUser().getId();
        long time =Instant.now().getEpochSecond();


            obj.put(Id, Id);
            obj.put(Id+"1", new Long(time));


        try{

            FileWriter file = new FileWriter("test.json");

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        String Id = event.getMember().getUser().getId();
        long timeLeave = Instant.now().getEpochSecond();

            obj.put(Id, Id);
            obj.put(Id+"2", new Long(timeLeave));

            long timesLog = (long)obj.get(Id+"1");
            long timeTotal = timeLeave-timesLog;

            if(obj.get(Id+"3")==null)
            obj.put(Id+"3",new Long(timeTotal));
            else obj.put(Id+"3",new Long((long)obj.get(Id+"3")+timeTotal));


         try{

            FileWriter file = new FileWriter("test.json");

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String Id = event.getAuthor().getId();
        String prefix = Config.get("prefix");
        String raw = event.getMessage().getContentRaw();

        if(obj.get(Id+"msg")!=null)
            obj.put(Id+"msg", (long)obj.get(Id+"msg")+1);
        else
            obj.put(Id+"msg", new Long(1));

        try{

            FileWriter file = new FileWriter("test.json");

            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(raw.equalsIgnoreCase(prefix+"stats")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("ShiroBot", null,
                    "https://media.discordapp.net/attachments/802701587293143041/802702412635832351/1530565453_97847679dadfe0ee8fe7f0a23209d9f6.gif?width=399&height=559");
            embedBuilder.setTitle("Ваша статистика на локалке ");
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            long minute = TimeUnit.SECONDS.toMinutes((long)obj.get(Id+"3"));
            long hours = TimeUnit.SECONDS.toHours((long)obj.get(Id+"3"));

            embedBuilder.setColor(Color.getHSBColor((float)Math.random()*255,(float)Math.random()*255,(float)Math.random()*255));
            if(minute>=60){
                embedBuilder.addField("Часов в войсе: "+ System.lineSeparator() +hours," ",true);
            }
            else {
                embedBuilder.addField("Минут в войсе: "+ System.lineSeparator() +minute," ",true);
            }
            embedBuilder.addField("Сообщений в чате: "+ System.lineSeparator() +obj.get(Id+"msg")," ",true);
            embedBuilder.setFooter("Вы на локалке с "+ event.getMember().getTimeJoined().toLocalDate(),event.getAuthor().getAvatarUrl());
            embedBuilder.setThumbnail(event.getAuthor().getAvatarUrl());


            TextChannel channel = event.getChannel();
            if (obj.get(Id)!=null) {
                String times = " "+ obj.get(Id+"3");

                channel.sendMessage(embedBuilder.build()).queue();
            }
            else {
                channel.sendMessage("Нужно сначала зайти в комнату, после выйти из неё").queue();
            }
        }


    }






}



