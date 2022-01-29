package com.revature.ocean.services;

import com.revature.ocean.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.security.SecureRandom;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    EmailService emailService;

    @Mock
    JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        emailService = new EmailService();
    }

    @Test
    void newPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<10; i++){
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        sb.toString();
        assertEquals(sb.length(), 10);
    }

    @Test
    void sendNewPasswordMessageContents(){
        EmailService emailService = Mockito.spy(this.emailService);
        String pass = emailService.newPassword();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("oceansocailapp@gmail.com");

        User user = new User("user", "password", "tommy.arnette@gmail.com", "Tommy", "TEST", new Date());

        message.setTo(user.getEmail());

        message.setText("Hello "+ user.getFirstName() +",\n" +
                "This email is to confirm your password has been reset.\n" +
                "Please find your new password is.\n" +
                "\n"+
                "Password: " + pass + " \n" +
                "\n"+
                "\n"+
                "We recommend changing your password again after login in.\n"+
                "\n"+
                "If you did not request a password change please reach out to our administrators.\n" +
                "at oceansocailapp@gmail.com\n" +
                "Thank you and have a great day!\n" +
                "\n");

        String expectedMessageContents = message.getText();

        String actualMessageContents = "Hello "+ "Tommy" +",\n" +
                "This email is to confirm your password has been reset.\n" +
                "Please find your new password is.\n" +
                "\n"+
                "Password: " + pass + " \n" +
                "\n"+
                "\n"+
                "We recommend changing your password again after login in.\n"+
                "\n"+
                "If you did not request a password change please reach out to our administrators.\n" +
                "at oceansocailapp@gmail.com\n" +
                "Thank you and have a great day!\n" +
                "\n";

        assertEquals(expectedMessageContents, actualMessageContents);
    }

    @Test
    void welcomeEmail(){
        EmailService emailService = Mockito.spy(this.emailService);

        SimpleMailMessage welcome = new SimpleMailMessage();
        welcome.setFrom("oceansocailapp@gmail.com");

        User user = new User("user", "password", "tommy.arnette@gmail.com", "Tommy", "TEST", new Date());

        welcome.setTo(user.getEmail());
        welcome.setText("Hello "+ user.getFirstName() +",\n" +
                "This email is to confirm your account has been added\n" +
                " to the Ocean Social Application.\n" +
                "\n"+
                "\n"+
                "To get started please update your profile and then\n" +
                "get to posting in your 'Tide'.\n"+
                "\n"+
                "\n"+
                "Check out other peoples' post and view their profile.\n"+
                "\n"+
                "Leave comments and likes on your friends' post to show them some love. \n"+
                "\n"+
                "\n"+
                "If you have any issues please feel free to reach out to our administrators.\n" +
                "at oceansocailapp@gmail.com\n" +
                "\n"+
                "Thank you and have a great day!");

        String expectedWelcomeEmail = welcome.getText();

        String actualWelcomeEmail = "Hello "+ "Tommy" +",\n" +
                "This email is to confirm your account has been added\n" +
                " to the Ocean Social Application.\n" +
                "\n"+
                "\n"+
                "To get started please update your profile and then\n" +
                "get to posting in your 'Tide'.\n"+
                "\n"+
                "\n"+
                "Check out other peoples' post and view their profile.\n"+
                "\n"+
                "Leave comments and likes on your friends' post to show them some love. \n"+
                "\n"+
                "\n"+
                "If you have any issues please feel free to reach out to our administrators.\n" +
                "at oceansocailapp@gmail.com\n" +
                "\n"+
                "Thank you and have a great day!";

        assertEquals(expectedWelcomeEmail, actualWelcomeEmail);
    }
}