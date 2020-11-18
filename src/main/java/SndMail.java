import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class SndMail {
    private static String MY_EMAIL = "ppzeff@yandex.ru";
    private static String MY_EMAIL_PASS = "saundtrak";
    private static String NEW_LINE = "\n";

    //    public static void main(String[] args) throws MessagingException {
    public static void sndMail(String text, String name, int fotoID, long chatId) throws Exception {
        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        //Объект properties хранит параметры соединения.
        //Для каждого почтового сервера они разные.
        //Если не знаете нужные - обратитесь к администратору почтового сервера.
        //Ну или гуглите;=)
        //Конкретно для Yandex параметры соединения можно подсмотреть тут:
        //https://yandex.ru/support/mail/mail-clients.html (раздел "Исходящая почта")
        Properties properties = new Properties();
        //Хост или IP-адрес почтового сервера
        properties.put("mail.smtp.host", "smtp.yandex.ru");
        //Требуется ли аутентификация для отправки сообщения
        properties.put("mail.smtp.auth", "true");
        //Порт для установки соединения
        properties.put("mail.smtp.socketFactory.port", "465");
        //Фабрика сокетов, так как при отправке сообщения Yandex требует SSL-соединения
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //Создаем соединение для отправки почтового сообщения
        Session session = Session.getDefaultInstance(properties,
                //Аутентификатор - объект, который передает логин и пароль
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MY_EMAIL, MY_EMAIL_PASS);
                    }
                });
        //Создаем новое почтовое сообщение
        Message message = new MimeMessage(session);
        //От кого
        message.setFrom(new InternetAddress("Алексей Толстик <ppzeff@yandex.ru>"));
        //Кому
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("ppzeff@yandex.ru"));
        message.setRecipient(Message.RecipientType.CC, new InternetAddress("aleksey.tolstik@outlook.com"));

        //Тема письма
        message.setSubject(name + ". Отгрузка " + formatter.format(date));
        //Текст письма
        message.setText(text);

        //Файл вложения
        File file = new File("./images/" + fotoID + ".jpg");
        //Собираем содержимое письма из кусочков
        MimeMultipart multipart = new MimeMultipart();
        //Первый кусочек - текст письма
        MimeBodyPart part1 = new MimeBodyPart();
        part1.addHeader("Content-Type", "text/plain; charset=UTF-8");

        String htmlText = "<html><body>" + text + NEW_LINE + "<br><img src=\"cid:" + fotoID + ".jpg\"></body></html>";

        part1.setDataHandler(new DataHandler(htmlText, "text/html; charset=\"utf-8\""));


        //Второй кусочек - файл
/*
        MimeBodyPart part2 = new MimeBodyPart();
        part2.setFileName(MimeUtility.encodeWord(file.getName()));
        part2.setDataHandler(new DataHandler(new FileDataSource(file)));
        multipart.addBodyPart(part2);
 */

        // Третий кусочек - вложение фото
        MimeBodyPart imagePart = new MimeBodyPart();
        imagePart.attachFile("images/" + fotoID + ".jpg");
        String cid = fotoID + ".jpg";
        imagePart.setContentID("<" + cid + ">");
        imagePart.setDisposition(MimeBodyPart.INLINE);

        multipart.addBodyPart(imagePart);
        multipart.addBodyPart(part1);
        //Добавляем оба кусочка в сообщение

        message.setContent(multipart);


        //Поехали!!!
        Transport.send(message);
//        Bot_p.key_board_status=0;
        System.out.println("email send. " + formatter.format(date));
        SendMsg.sendMSG(chatId,"email send. " + formatter.format(date),0);
    }
}