import com.pengrad.telegrambot.model.Message;

public class ModelNorwitt {
    private String name = "НОРВИТТ";
    public static String NEW_LINE = "\n";
    private String NEW_LINE_HTML = "<br>";
    private int karton;
    private int plenka;
    private int poddon;
    private int foto;
    UserDataCache userDataCache = new UserDataCache();
    public static SendMsg sendMsg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKarton() {
        return karton;
    }

    public void setKarton(int karton) {
        this.karton = karton;
    }

    public int getPlenka() {
        return plenka;
    }

    public void setPlenka(int plenka) {
        this.plenka = plenka;
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

    public void ZagruzkaNorwit(Message message) {
        switch (userDataCache.getModelState(message.from().id())) {
            case CASTOMER_FIRS_STATE: {
                sendMsg.sendMSG(message.chat().id(),
                        "В НОРВИТТ загружаем: " + NEW_LINE +
                                "1. картон" + NEW_LINE +
                                "2. пленка" + NEW_LINE +
                                "3. поддоны");
                sendMsg.sendMSG(message.chat().id(), "Введите массу картона: ");
                userDataCache.setModelState(message.from().id(), BotState.ZAGR_N_PLENKA);
                break;
            }
            case ZAGR_N_PLENKA: {
                if (Util.isNumeric(message.text())) setKarton(Integer.parseInt(message.text()));
                else {
                    sendMsg.sendMSG(message.chat().id(), "Неверное число!!!" + NEW_LINE + "Введите массу картона: ");
                    break;
                }
                sendMsg.sendMSG(message.chat().id(), "Введите массу пленки: ");
                userDataCache.setModelState(message.from().id(), BotState.ZAGR_N_PADDON);
                break;
            }
            case ZAGR_N_PADDON: {
                if (Util.isNumeric(message.text())) setPlenka(Integer.parseInt(message.text()));
                else {
                    sendMsg.sendMSG(message.chat().id(), "Неверное число!!!" + NEW_LINE + "Введите массу пленки: ");
                    break;
                }
                sendMsg.sendMSG(message.chat().id(), "Введите количество поддонов: ");
                userDataCache.setModelState(message.from().id(), BotState.ZAGR_N_FOTO);
                break;
            }
            case ZAGR_N_FOTO: {
                if (Util.isNumeric(message.text())) setPoddon(Integer.parseInt(message.text()));
                else {
                    sendMsg.sendMSG(message.chat().id(), "Неверное число!!!" + NEW_LINE + "Введите количество поддонов: ");
                    break;
                }
                sendMsg.sendMSG(message.chat().id(), "Приложите фото записей: ");
                userDataCache.setModelState(message.from().id(), BotState.ZAGR_N_PROVERKA);
                break;
            }
            case ZAGR_N_PROVERKA: {
                if (message.photo() != null) {
                    HttpDownload.DownloadPhoto(message);
                    setFoto(message.messageId());
                } else {
                    sendMsg.sendMSG(message.chat().id(), "Это не фото!!!" + NEW_LINE + "Приложите фото: ");
                    break;
                }
                sendMsg.sendMSG(message.chat().id(), "Проверьте ввод данных:" + NEW_LINE +
                        "В " + getName() + " загружены: " + NEW_LINE +
                        "масса картона: " + getKarton() + NEW_LINE +
                        "масса пленки: " + getPlenka() + NEW_LINE +
                        "количество поддонов: " + getPoddon(), 2);
                userDataCache.setModelState(message.from().id(), BotState.ZAGR_N_SEMD_MAIL);
                break;
            }
            case ZAGR_N_SEMD_MAIL: {
                if (message.text().equals("Да, все верно!")) {

                    try {
                        SndMail.sndMail("В " + getName() + " загружены: " + NEW_LINE_HTML +
                                "масса картона: " + getKarton() + NEW_LINE_HTML +
                                "масса пленки: " + getPlenka() + NEW_LINE_HTML +
                                "количество поддонов: " + getPoddon(), getName(), getFoto(), message.from().id());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    userDataCache.setModelState(message.from().id(), BotState.ASK_CASTOMER);
                    break;
                } else {
                    if (!message.text().equals("Нет, повторить ввод.")) {
//                        Bot_p.key_board_status = 2;
                        sendMsg.sendMSG(message.chat().id(), "Ответ не верный" + NEW_LINE +
                                "Проверьте ввод данных:" + NEW_LINE +
                                "В " + getName() + " загружены: " + NEW_LINE +
                                "масса картона: " + getKarton() + NEW_LINE +
                                "масса пленки: " + getPlenka() + NEW_LINE +
                                "количество поддонов: " + getPoddon());
                        userDataCache.setModelState(message.from().id(), BotState.ZAGR_N_SEMD_MAIL);
                    } else {
//                        Bot_p.key_board_status = 0;
                        sendMsg.sendMSG(message.chat().id(), "Выберите из списка: ", 0);
                        userDataCache.setModelState(message.from().id(), BotState.ASK_CASTOMER);
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
