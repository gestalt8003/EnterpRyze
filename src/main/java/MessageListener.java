import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Random;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Log what happened
        if (event.isFromType(ChannelType.PRIVATE)) { // If a private message was received
            // Log the message
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());

            if(!event.getAuthor().isBot()) { // Prevent bot from talking to other bots and self
                PrivateChannel channel = event.getPrivateChannel();

                if(isCommand(event, "hello")) {
                    channel.sendMessage("Hello, " + event.getAuthor().getName() + ".").queue();
                }
            }
        } else { // If a message was received
            // Log the message
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                    event.getTextChannel().getName(), event.getMember().getEffectiveName(),
                    event.getMessage().getContentDisplay());

            if(!event.getAuthor().isBot()) { // Prevent bot from talking to other bots and self
                MessageChannel channel = event.getChannel();

                if(isCommand(event, "hello")) {                                                                 // HELLO
                    // Hello message
                    channel.sendMessage("Hello, " + event.getMember().getAsMention() + ".").queue();
                } else if(isCommand(event, "roll")) {                                                           // ROLL
                    String[] args = getCommandArgs(event); // Get args
                    int bounds = EnterpRyze.ROLL_DEFAULT; // Default bounds
                    // If user entered a max arg, then set bounds to that
                    if(args != null && args.length >= 1) {
                        try {
                            bounds = Integer.parseInt(args[0]);
                            if(bounds <= 0) {
                                return;
                            }
                        } catch(NumberFormatException e) {
                            return;
                        }
                    }
                    // Generate random number
                    Random rand = new Random();
                    int n = rand.nextInt(bounds) + 1;
                    // Roll message
                    channel.sendMessage(event.getMember().getAsMention() + " rolled a " + n + ".").queue();
                } else {                                                                                                 // INVALID COMMAND
                    // Invalid command message
                    channel.sendMessage(event.getMember().getAsMention() + ": Invalid command. " + EnterpRyze.PREFIX + "help for a list of valid commands.").queue();
                }
            }
        }
    }

    private boolean isCommand(MessageReceivedEvent event, String compare) {
        return event.getMessage().getContentDisplay().startsWith(EnterpRyze.PREFIX + compare);
    }

    private String[] getCommandArgs(MessageReceivedEvent event) {
        String[] split = event.getMessage().getContentDisplay().split(" ");
        String[] args = new String[split.length-1];
        for(int i = 1; i < split.length; i++) {
            args[i-1] = split[i];
        }
        return args;
    }
}
