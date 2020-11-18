import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

public class Run {
    public static String NEW_LINE = "\n";
    public static TelegramBot bot = new TelegramBot("1454751998:AAFZDDvdmGkETJlcHeRL-rAfCcGF_6cXB00");
    public static Zagruzka zagr = new Zagruzka(null);
    BotState botState = BotState.ASK_CASTOMER;
    UserDataCache userDataCache = new UserDataCache();
    SendMsg sendMsg;

    public void run() {

        bot.setUpdatesListener(updates -> {
            if (!updates.isEmpty()) {
                updates.stream().map(Update::message).forEach(message -> {
//                    chatId = message.chat().id();
//                    System.out.print("chat " + message.chat().id());
//                    System.out.println(" user " + message.from().id());

                    if (userDataCache.getUsersBotState(message.from().id()) == BotState.ASK_CASTOMER && message.text() != null) {

                        switch (message.text()) {
                            case "НОРВИТТ": {
//                                SendMsg.sendMSG(message.chat().id(), "!!!!");
                                botState = BotState.CASTOMER_NORWITT;
                                System.out.println(botState);
                                break;
                            }
                            case "ВОСХОДОВ": {
                                botState = BotState.CASTOMER_VOSHODOV;
//                                zagr = new Zagruzka(message.text());
//                                zagr.ZagruzkaPP(message);
//                                System.out.println("bot " + zagr.getStateZagr());
                                break;
                            }
                            case "ЛЕКС":
                                sendMsg.sendMSG(message.chat().id(), "ЛЕКС");
//                                zg.setName(message.text());
//                                zg.ZagruzkaPP(message);
                                break;

                            default: {
                                sendMsg.sendMSG(message.chat().id(), "Введено неправильное название контерагента, необходимо выбрать из списка", 0);
                                break;
                            }
                        }
                        userDataCache.setUsersBotState(message.from().id(), botState);
//                        zagr.ZagruzkaPP(botState, message);
                    } else {


//                        botState = userDataCache.getUsersBotState(message.from().id());
//                        System.out.println("1 " + botState);
//                        System.out.println("2 " + userDataCache.getUsersBotState(message.from().id()));
                    }
                    zagr.ZagruzkaPP(userDataCache.getUsersBotState(message.from().id()), message);
                });
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}
