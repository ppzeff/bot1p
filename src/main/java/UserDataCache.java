import java.util.HashMap;
import java.util.Map;

public class UserDataCache {

  private static  Map<Integer, BotState> userBotStates = new HashMap<>();
  private static  Map<Integer, BotState> modelState = new HashMap<>();

    public  void setUsersBotState(int userId, BotState botState) {
        userBotStates.put(userId, botState);
        System.out.print ("su "+ userId);
        System.out.println("  su " + botState);
    }
    public  void setModelState(int userId, BotState botState) {
        modelState.put(userId, botState);
    }

    public  BotState getUsersBotState(int userId) {
        BotState botState = userBotStates.get(userId);
        if (botState == null) {
            botState = BotState.ASK_CASTOMER;
        }
        // System.out.println(botState);
        return botState;
    }
    public  BotState getModelState(int userId) {
        BotState botState = modelState.get(userId);
        if (botState == null) {
            botState = BotState.CASTOMER_FIRS_STATE;
        }
        // System.out.println(botState);
        return botState;
    }


}
