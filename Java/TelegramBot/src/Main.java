import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    private static String PROXY_HOST = "127.0.0.1";
    private static Integer PROXY_PORT = 1080;

    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        try {
            botsApi.registerBot(new MyBot(botOptions));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
