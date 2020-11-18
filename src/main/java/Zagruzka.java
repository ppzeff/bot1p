import com.pengrad.telegrambot.model.Message;

class Zagruzka {
    public static String NEW_LINE = "\n";
    private String NEW_LINE_HTML = "<br>";

    private String stateZagr = null;

    public ModelNorwitt mnz = new ModelNorwitt();
    public ModelVoshodov mvz = new ModelVoshodov();

    public BotState botState;

    public Zagruzka(String str) {
        setStateZagr(str);
    }

    public void setStateZagr(String stateZagr) {
        this.stateZagr = stateZagr;
    }

    public String getStateZagr() {
        return stateZagr;
    }

    public void ZagruzkaPP(BotState botState, Message message) {
        switch (botState) {
            case CASTOMER_NORWITT: {
                mnz.ZagruzkaNorwit(message);
                break;
            }
            case CASTOMER_VOSHODOV: {
                mvz.ZagruzkaVoshodov(message);
                break;
            }
            default:{
                mvz.ZagruzkaVoshodov(message);
            }
        }
    }

    public boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
