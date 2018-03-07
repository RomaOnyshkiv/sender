import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Sender {

    public static void main(String... args) {
        final String username = "USERNAME";
        final String password = "PASSWORD";

        String to = "TO.SOMEONE";
        String from = "FROM.SOMEONE";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }

        });

        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Test message");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Hello");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            String file = "src/main/resources/attachments/file.txt";
            String attachment = "file.txt";
            DataSource dataSource = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(dataSource));
            messageBodyPart.setFileName(attachment);

            multipart.addBodyPart(textPart);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            System.out.println("Sending...");

            Transport.send(message);

            System.out.println("Done");


        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
