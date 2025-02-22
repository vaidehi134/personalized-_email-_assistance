package com.email_generator.email_generator.app.service;

import com.email_generator.email_generator.app.dto.EmailRequest;
import com.email_generator.email_generator.app.model.EmailModel;
import com.email_generator.email_generator.app.repository.EmailRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class EmailSchedulingService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private EmailRepository emailRepository;

    public void scheduleEmail(EmailRequest request) throws SchedulerException {
        // Save email details to the database
        EmailModel scheduledEmail = new EmailModel();
        scheduledEmail.setTo(request.getTo());
        scheduledEmail.setSubject(request.getSubject());
        scheduledEmail.setText(request.getText());
        scheduledEmail.setScheduledTime(request.getScheduledTime());
        emailRepository.save(scheduledEmail);

        // Create a unique identifier for the job
        String jobName = UUID.randomUUID().toString();
        String triggerName = UUID.randomUUID().toString();

        // Create JobDataMap with email details
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("to", request.getTo());
        jobDataMap.put("subject", request.getSubject());
        jobDataMap.put("text", request.getText());

        // Create JobDetail
        JobDetail jobDetail = JobBuilder.newJob(EmailJob.class)
                .withIdentity(jobName, "emailGroup")
                .usingJobData(jobDataMap)
                .build();

        // Create Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, "emailGroup")
                .startAt(Date.from(request.getScheduledTime().atZone(ZoneId.systemDefault()).toInstant()))
                .build();

        // Schedule the job
        scheduler.scheduleJob(jobDetail, trigger);
    }
}









//    @Autowired
//    private JavaMailSender javaMailSender;

//    public void sendEmail(String to, String subject, String body)
//    {
//        try{
//            SimpleMailMessage mail = new SimpleMailMessage();
//            mail.setTo(to);
//            mail.setSubject(subject);
//            mail.setText(body);
//            javaMailSender.send(mail);
//
//        }catch(Exception e)
//        {
//            log.error("Exception while sendEmail", e);
//        }
//    }



