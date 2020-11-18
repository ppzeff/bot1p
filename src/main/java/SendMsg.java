
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;

public class SendMsg {
    static Run run;
    private Keyboard forceReply = new ForceReply();


    private static Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
            new String[]{"НОРВИТТ", "ЛЕКС", "ВОСХОДОВ", "РОСТОВ"})
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);        // optional

    private static Keyboard replyKeyboardMarkup2 = new ReplyKeyboardMarkup(
            new String[]{""})
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);        // optional

    private static Keyboard replyKeyboardMarkup3 = new ReplyKeyboardMarkup(
            new String[]{"Да, все верно!", "Нет, повторить ввод."})
            .oneTimeKeyboard(true)   // optional
            .resizeKeyboard(true)    // optional
            .selective(true);        // optional

    private Keyboard keyboard = new ReplyKeyboardMarkup(
            new KeyboardButton[]{
                    new KeyboardButton("text"),
                    new KeyboardButton("\uD83D\uDD10 contact").requestContact(true),
                    new KeyboardButton("location").requestLocation(true)
            }
    );

    private static InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
            new InlineKeyboardButton[]{
                    new InlineKeyboardButton("url").url("www.google.com"),
                    new InlineKeyboardButton("callback_data").callbackData("callback_data"),
                    new InlineKeyboardButton("Switch!").switchInlineQuery("switch_inline_query")
            });

    public static void sendMSG(long chtID, String msg) {
        sendMSG(chtID, msg, 1);
//        BotModel.bot.execute(new SendMessage(chtID, msg));
    }

    public static void sendMSG(long chtID, String msg, int key_board_status) {
//        BotModel.bot.execute(new SendMessage(BotModel.chatId, msg));

        switch (key_board_status) {
            case 0: {
                run.bot.execute(new SendMessage(chtID, msg).replyMarkup(replyKeyboardMarkup));
                key_board_status = 0;
                break;
            }
            case 1: {
                run.bot.execute(new SendMessage(chtID, msg).replyMarkup(replyKeyboardMarkup2));

                break;
            }
            case 2: {
                run.bot.execute(new SendMessage(chtID, msg).replyMarkup(replyKeyboardMarkup3));

                break;
            }
            default: {

            }
        }
    }
//        bot.execute(new SendMessage(chtID, "").replyMarkup(replyKeyboardMarkup2));


}
