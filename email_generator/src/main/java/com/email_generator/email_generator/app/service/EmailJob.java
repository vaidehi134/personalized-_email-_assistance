package com.email_generator.email_generator.app.service;

import com.email_generator.email_generator.app.model.EmailModel;
import com.email_generator.email_generator.app.repository.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

//@Component
//public class EmailJob implements Job {
//
//    @Autowired
//    private JavaMailSender emailSender;
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        String to = context.getJobDetail().getJobDataMap().getString("to");
//        String subject = context.getJobDetail().getJobDataMap().getString("subject");
//        String text = context.getJobDetail().getJobDataMap().getString("body");
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        emailSender.send(message);
//    }
//}
@Component
public class EmailJob implements Job {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            // Retrieve email details from the JobDataMap
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            String to = jobDataMap.getString("to");
            String subject = jobDataMap.getString("subject");
            String text = jobDataMap.getString("text");

            if (to == null || subject == null || text == null) {
                throw new JobExecutionException("Email details are missing");
            }

            // Create a MimeMessage
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Set the email details
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            // Send the email
            emailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new JobExecutionException("Failed to send email", e);
        }
    }
}


