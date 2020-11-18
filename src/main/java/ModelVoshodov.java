import com.pengrad.telegrambot.model.Message;

public class ModelVoshodov {
    private String name = "ВОСХОДОВ";
    public static String NEW_LINE = "\n";
    private String NEW_LINE_HTML = "<br>";
    private int ps;
    private int poddon;
    private int foto;
    private int status;
    UserDataCache userDataCache;
    SendMsg sendMsg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getPoddon() {
        return poddon;
    }

    public void setPoddon(int poddon) {
        this.poddon = poddon;
    }

    public Integer getFoto() {
        return foto;
    }

    public void setFoto(Integer foto) {
        this.foto = foto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void ZagruzkaVoshodov(Message message) {
        switch (getStatus()) {
            case 0: {
                sendMsg.sendMSG(message.chat().id(),
                        "В ВОСХОДОВ загружаем: " + NEW_LINE +
                                "1. полистерол" + NEW_LINE +
                                "2. поддоны");
                sendMsg.sendMSG(message.chat().id(), "Введите массу полистерола: ");
                setStatus(1);
                break;
            }
            case 1: {
                if (Util.isNumeric(message.text())) setPs(Integer.parseInt(message.text()));
                else {
                    sendMsg.sendMSG(message.chat().id(), "Неверное число!!!" + NEW_LINE + "Введите массу полистерола: ");
                    break;
                }
                sendMsg.sendMSG(message.chat().id(), "Введите количество поддонов: ");
                setStatus(2);
                break;
            }
            case 2: {
                if (Util.isNumeric(message.text())) setPoddon(Integer.parseInt(message.text()));
                else {
                    sendMsg.sendMSG(message.chat().id(), "Неверное число!!!" + NEW_LINE + "Введите количество поддонов: ");
                    break;
                }
                sendMsg.sendMSG(message.chat().id(), "Приложите фото записей: ");
                setStatus(3);
                break;

            }
            case 3: {
                if (message.photo() != null) {
                    HttpDownload.DownloadPhoto(message);
                    setFoto(message.messageId());
                } else {
                    sendMsg.sendMSG(message.chat().id(), "Это не фото!!!" + NEW_LINE + "Приложите фото: ");
                    break;
                }
                sendMsg.sendMSG(message.chat().id(), "Проверьте ввод данных:" + NEW_LINE +
                        "В " + getName() + " загружены: " + NEW_LINE +
                        "масса полистирола: " + getPs() + NEW_LINE +
                        "количество поддонов " + getPoddon(), 2);
                setStatus(4);
                break;
            }
            case 4: {
                if (message.text().equals("Да, все верно!")) {

                    try {
                        SndMail.sndMail("В " + getName() + " загружены: " + NEW_LINE_HTML +
                                "масса полистирола: " + getPs() + NEW_LINE_HTML +
                                "количество поддонов: " + getPoddon(), getName(), getFoto(), message.from().id());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setStatus(0);
//                    BotModel.zagr.setStateZagr(null);
                    userDataCache.setUsersBotState(message.from().id(), BotState.ASK_CASTOMER);
                    break;

                } else {
                    if (!message.text().equals("Нет, повторить ввод.")) {
//                        Bot_p.key_board_status = 2;
                        sendMsg.sendMSG(message.chat().id(), "Ответ не верный" + NEW_LINE +
                                "Проверьте ввод данных:" + NEW_LINE +
                                "В " + getName() + " загружены: " + NEW_LINE +
                                "масса полистирола: " + getPs() + NEW_LINE +
                                "количество поддонов: " + getPoddon(), 2);
                        setStatus(5);
                    } else {
//                        Bot_p.key_board_status = 0;
                        sendMsg.sendMSG(message.chat().id(), "Выберите из списка: ", 0);
                        setStatus(0);
//                        BotModel.zagr.setStateZagr(null);
                        userDataCache.setUsersBotState(message.from().id(), BotState.ASK_CASTOMER);

                    }
                    break;

                }
            }

            default: {
                System.out.println("default");
            }
        }

    }

}
