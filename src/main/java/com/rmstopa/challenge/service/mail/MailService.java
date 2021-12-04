package com.rmstopa.challenge.service.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;


@Service
@PropertySource("classpath:env/mail.properties")
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Async
    public void sendEmail(String firstName, String lastName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            message.setTo("startergft@gmail.com");
            message.setFrom(env.getProperty("mail.smtp.username"));
            message.setSubject(env.getProperty("mail.alert"));
            message.setText("<!doctype html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                    "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                    "  <title>Email Informativo</title>\n" +
                    "  <style>\n" +
                    "\n" +
                    "    body {\n" +
                    "      background-color: #f6f6f6;\n" +
                    "      font-family: sans-serif;\n" +
                    "      -webkit-font-smoothing: antialiased;\n" +
                    "      font-size: 14px;\n" +
                    "      line-height: 1.4;\n" +
                    "      margin: 0;\n" +
                    "      padding: 0;\n" +
                    "      -ms-text-size-adjust: 100%;\n" +
                    "      -webkit-text-size-adjust: 100%;\n" +
                    "    }\n" +
                    "\n" +
                    "    .body {\n" +
                    "      background-color: #f6f6f6;\n" +
                    "      width: 100%;\n" +
                    "    }\n" +
                    "\n" +
                    "    .content {\n" +
                    "      box-sizing: border-box;\n" +
                    "      display: block;\n" +
                    "      margin: 0 auto;\n" +
                    "      max-width: 580px;\n" +
                    "      padding: 10px;\n" +
                    "    }\n" +
                    "\n" +
                    "\n" +
                    "    .main {\n" +
                    "      background: #ffffff;\n" +
                    "      border-radius: 3px;\n" +
                    "      width: 100%;\n" +
                    "    }\n" +
                    "\n" +
                    "    .wrapper {\n" +
                    "      box-sizing: border-box;\n" +
                    "      padding: 20px;\n" +
                    "    }\n" +
                    "\n" +
                    "\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body class=\"\">\n" +
                    "<span class=\"preheader\"></span>\n" +
                    "<table role=\"presentation\" class=\"body\">\n" +
                    "  <tr>\n" +
                    "    <td>&nbsp;</td>\n" +
                    "    <td class=\"container\">\n" +
                    "      <div class=\"content\">\n" +
                    "        <table role=\"presentation\" class=\"main\">\n" +
                    "          <tr>\n" +
                    "            <td class=\"wrapper\">\n" +
                    "              <table role=\"presentation\" >\n" +
                    "                <tr>\n" +
                    "                  <td>\n" +
                    "                    <p>Olá,</p>\n" +
                    "                    <p>Informo que o Scrum Master "+firstName+ " " + lastName + " acabou de acessar a plataforma de cadastro de Starters.</p>\n" +
                    "                    <p>Plataforma criada por Raphael Mendes Stopa.</p>\n" +
                    "                    <p>Tenha um bom dia e obrigado.</p>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </table>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "      </div>\n" +
                    "    </td>\n" +
                    "    <td>&nbsp;</td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +
                    "</body>\n" +
                    "</html>", true);
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
