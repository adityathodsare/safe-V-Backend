//package com.safeVBackend.safeVBackend.emailController;
//
//import com.safeVBackend.safeVBackend.data.User;
//import com.safeVBackend.safeVBackend.data.userPrinciple;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Random;
//
//@RestController
//public class emailController {
//
//    private final JavaMailSender mailSender;
//
//    public emailController(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//
//    @RequestMapping("sendmail")
//    public String sendMail() {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String customerEmail = authentication.getName(); // Retrieves the logged-in username (email)
//            String coordinatorEmail = "thodsareaditya@gmail.com";
//            String chatboxCode = String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit code
//
//            // Sending email to customer
//            sendEmail(customerEmail, "Dear Customer, your purchase request is accepted. <br>We will convey details shortly. <br><br>For further communication, use the chatbox on the website with code: <b style='color:blue;'>" + chatboxCode + "</b>");
//
//            // Sending email to coordinator
//            sendEmail(coordinatorEmail, "Order received. <br>Please connect through the chatbox using code: <b style='color:green;'>" + chatboxCode + "</b>");
//
//            return chatboxCode + " generated successfully and emails sent";
//        } catch (Exception e) {
//            return "Error sending email: " + e.getMessage();
//        }
//    }
//
//    private void sendEmail(String to, String htmlMessage) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setFrom("thodsareaditya@gmail.com");
//        helper.setTo(to);
//        helper.setSubject("Request for Purchase Accepted");
//        helper.setText("<html><body style='font-family:Arial,sans-serif;padding:15px;border:1px solid #ddd;background:#f9f9f9;border-radius:10px;'>" +
//                "<h2 style='color:#008080;'>SafeV Purchase Update</h2>" +
//                "<p style='font-size:16px;'>" + htmlMessage + "</p>" +
//                "<hr><p style='font-size:12px;color:#555;'>This is an automated email, please do not reply.</p>" +
//                "</body></html>", true);
//
//        mailSender.send(message);
//    }
//}

package com.safeVBackend.safeVBackend.emailController;

import com.safeVBackend.safeVBackend.data.User;
import com.safeVBackend.safeVBackend.data.userPrinciple;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class emailController {

    private final JavaMailSender mailSender;

    public emailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("sendmail")
    public String sendMail() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String customerEmail = authentication.getName(); // Retrieves the logged-in username (email) // Retrieves the logged-in username (email)
            String coordinatorEmail = "thodsareaditya@gmail.com";
            String chatboxCode = String.valueOf(100000 + new Random().nextInt(900000)); // Generate 6-digit code

            // Customer message
            String customerMessage = "<p style='font-size:16px;'>Dear Customer, your purchase request is accepted. <br>We will convey details shortly.</p>" +
                    "<p style='font-size:16px;'><b>For further communication, use the chatbox on the website with code: <span style='color:blue;'>" + chatboxCode + "</span></b></p>" +
                    "<div style='margin-top:20px; padding:10px; background:#fff3cd; border-radius:5px; text-align:center;'>" +
                    "<p style='color:#856404; font-weight:bold;'>If you provided incorrect email or phone number, please update the correct details in the chatbox.</p></div>";

            // Coordinator message
            String coordinatorMessage = "<p style='font-size:16px;'>Order received.</p>" +
                    "<p style='font-size:16px;'><b>Please connect with the customer through the chatbox using code: <span style='color:green;'>" + chatboxCode + "</span></b></p>" +
                    "<div style='margin-top:20px; padding:10px; background:#d1ecf1; border-radius:5px; text-align:center;'>" +
                    "<p style='color:#0c5460; font-weight:bold;'>Ensure smooth communication with the customer using the provided code.</p></div>";

            // Send emails
            sendEmail(customerEmail, "SafeV Purchase Update", customerMessage);
            sendEmail(coordinatorEmail, "New Order Notification", coordinatorMessage);

            return chatboxCode + " generated successfully and emails sent";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }

    private void sendEmail(String to, String subject, String htmlMessage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("thodsareaditya@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        // Email template
        String emailContent = "<html><body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f4f4;'>" +
                "<div style='max-width: 600px; margin: 20px auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1);'>" +
                "<h2 style='color: #008080; text-align: center; margin-bottom: 15px;'>" + subject + "</h2>" +
                "<hr style='border: 1px solid #ddd;'>" +
                htmlMessage +
                "<br>" +
                "<p style='text-align: center; font-size: 14px; color: #777;'>This is an automated email, please do not reply.</p>" +
                "</div>" +
                "</body></html>";

        helper.setText(emailContent, true);

        mailSender.send(message);
    }
}
