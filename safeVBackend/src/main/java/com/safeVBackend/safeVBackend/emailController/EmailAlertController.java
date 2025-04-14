package com.safeVBackend.safeVBackend.emailController;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert")
@CrossOrigin(origins = "http://localhost:3000")  // Adjust as needed
public class EmailAlertController {

    private final JavaMailSender mailSender;
    private final String recipientEmail = "adityaamolthodsare@gmail.com";  // Change if needed
    private final String senderEmail = "thodsareaditya@gmail.com"; // Change if needed

    public EmailAlertController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 1️⃣ Accident Detected API
    @GetMapping("/accident")
    public String sendAccidentAlert() {
        String subject = "🚨 Tilt Angle Alert: Car Accident Detected!";
        String message = "<h3 style='color:red;'>Emergency Alert: Car Accident!</h3>"
                + "<p><strong>Issue:</strong> The vehicle has tilted beyond a safe angle.</p>"
                + "<p><strong>Immediate Action:</strong> Check vehicle stability and notify emergency services.</p>";
        return sendEmail(subject, message);
    }

    // 2️⃣ Gas Leak Detected API
    @GetMapping("/gas-leak")
    public String sendGasLeakAlert() {
        String subject = "⚠️ Gas Leak Alert: Unsafe Levels Detected!";
        String message = "<h3 style='color:orange;'>Emergency Alert: Gas Leak Detected!</h3>"
                + "<p><strong>Issue:</strong> Gas concentration exceeded 1000 PPM (Unsafe).</p>"
                + "<p><strong>Immediate Action:</strong> Evacuate the area and take safety measures.</p>";
        return sendEmail(subject, message);
    }

    // 3️⃣ Alcohol Level Unsafe API
    @GetMapping("/alcohol-level")
    public String sendAlcoholAlert() {
        String subject = "🚫 Alcohol Detected: Engine Remains OFF!";
        String message = "<h3 style='color:red;'>Emergency Alert: Driver is Drunk!</h3>"
                + "<p><strong>Issue:</strong> Alcohol level exceeded 1400 PPM (Unsafe to drive).</p>"
                + "<p><strong>Immediate Action:</strong> The engine will remain OFF for safety.</p>";
        return sendEmail(subject, message);
    }

    // 4️⃣ Fire Detected API
    @GetMapping("/fire-detected")
    public String sendFireAlert() {
        String subject = "🔥 Fire Alert: Immediate Action Required!";
        String message = "<h3 style='color:red;'>Emergency Alert: Fire Detected!</h3>"
                + "<p><strong>Issue:</strong> Fire has been detected in the vehicle.</p>"
                + "<p><strong>Immediate Action:</strong> Use a fire extinguisher and evacuate immediately.</p>";
        return sendEmail(subject, message);
    }

    // ✅ Send Email Function
    private String sendEmail(String subject, String htmlMessage) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(senderEmail);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);

            String emailTemplate = "<html><body style='font-family: Arial, sans-serif; background: #f9f9f9; padding: 20px;'>"
                    + "<div style='max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 4px 10px rgba(0,0,0,0.1);'>"
                    + "<h2 style='text-align: center; color: #008080;'>" + subject + "</h2>"
                    + "<hr style='border: 1px solid #ddd; margin-bottom: 20px;'>"
                    + htmlMessage
                    + "<br><p style='text-align: center; font-size: 12px; color: #777;'>This is an automated email, please do not reply.</p>"
                    + "</div></body></html>";

            helper.setText(emailTemplate, true);
            mailSender.send(message);
            return "✅ Email Sent Successfully: " + subject;
        } catch (MessagingException e) {
            return "❌ Email Sending Failed: " + e.getMessage();
        }
    }
}
