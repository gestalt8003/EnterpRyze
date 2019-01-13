import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;

public class EnterpRyze {
    public static final char PREFIX = '$';
    public static final int ROLL_DEFAULT = 6;

    public static void main(String[] args)
            throws LoginException
    {
        String token = getTokenFromFile("E:/Projects/EnterpRyzeToken.txt");

        // Note: It is important to register your ReadyListener before building
        JDA jda = new JDABuilder(token).build();
        jda.addEventListener(new MessageListener());
    }

    public static String getTokenFromFile(String path) {
        String token = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            token += br.readLine();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        System.out.println("Token found: " + token);
        return token;
    }
}
