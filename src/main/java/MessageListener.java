import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MessageListener extends ListenerAdapter
{
    public static void main(String[] args)
            throws LoginException
    {
        JDA jda = JDABuilder.createDefault("OTgyODk5Mzk4MDg0Mjg0NDU2.GyNwnN.5E_fWZ4x-UPky1EoNRLikLt_-GHnDiL35aOSMA").build();
        //You can also add event listeners to the already built JDA instance
        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
        jda.addEventListener(new MessageListener());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
            System.out.println(event.getMessage().getContentDisplay());
            String[] names = event.getMessage().getContentDisplay().split(" ");
            Set<String> intersect = null;
            MessageChannel channel = event.getChannel();

            /*channel.sendMessage("Pong!") *//* => RestAction<Message> *//*
                    .queue(response *//* => Message *//* -> {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis()).queue();
                    });*/
            for (String name : names) {
                System.out.println(name);
                try {
                    Set<String> hs = ApiCodeforces.getSet(name);
                    if (intersect == null)
                        intersect = hs;
                    else {
                        Set<String> sw = new HashSet<>();
                        for (String v : hs) {
                            if (intersect.contains(v))
                                sw.add(v);
                        }
                        intersect = sw;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            String pr = "Not found";
            for (String s : intersect) {
                StringBuilder digs = new StringBuilder();
                StringBuilder alph = new StringBuilder();
                int ind = 0;
                for (; ind < s.length(); ++ind) {
                    if (Character.isDigit(s.charAt(ind)))
                        digs.append(s.charAt(ind));
                    else {

                        break;
                    }
                }
                for (; ind < s.length(); ++ind)
                    alph.append(s.charAt(ind));

                if (s.length() >= 7)
                    pr = String.format("https://codeforces.com/gym/%s/problem/%s\n", digs, alph);
                else
                    pr = String.format("https://codeforces.com/problemset/problem/%s/%s\n", digs, alph);
                channel.sendMessage(pr).queue();
                break;
            }
            //String template = "https://codeforces.com/api/user.ratedList?activeOnly=false&includeRetired=false&contestid=566"
        }
        else
        {
            //System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
            //        event.getTextChannel().getName(), event.getMember().getEffectiveName(),
            //        event.getMessage().getContentDisplay());
            System.out.println(event.getGuild().getName());
            System.out.println(event.getTextChannel().getName());
            System.out.println(event.getMember().getEffectiveName());
            System.out.println(event.getMessage().getContentDisplay());
        }
    }
}