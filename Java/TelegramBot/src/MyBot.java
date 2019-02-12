import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {
    public MyBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();
            String message_text = update.getMessage().getText();

            SendMessage message = new SendMessage()
                    .setChatId(chat_id)
                    .setText(message_text);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            long chat_id = update.getMessage().getChatId();
            
        }
    }

    @Override
    public String getBotUsername() {
        return "LeeEchoBot";
    }


    @Override
    public String getBotToken() {
        return "730821308:AAEa8tXQKFzlNkoaruEXut2eDQaBFBxGujU";
    }
}
