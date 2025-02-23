package com.email_generator.email_generator.app.controller;
import com.email_generator.email_generator.app.dto.EmailRequest;
import com.email_generator.email_generator.app.model.EmailModel;
import com.email_generator.email_generator.app.repository.EmailRepository;
import com.email_generator.email_generator.app.service.EmailSchedulingService;
import com.email_generator.email_generator.app.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@RestController
//@RequestMapping("/api/email")
//public class EmailController {
//
//    @Autowired
//    private EmailSchedulingService emailService;
//
//    @Autowired
//    private Scheduler scheduler;
//
//     @PostMapping("/send")
//    ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) throws SchedulerException
//     {
//         try{
//             emailService.sendEmail(emailRequest);
//             return ResponseEntity.ok("Email sent successfully!");
//         }catch(Exception e)
//         {
//             return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
//         }
//
//     }
//
////    @PostMapping("/send")
////    ResponseEntity<String> sendEmail(@RequestBody EmailModel emailModel)
////    {
////         try{
////             emailService.sendEmail(emailModel.getTo(),emailModel.getSubject(),emailModel.getBody());
////             return ResponseEntity.ok("Email sent successfully!");
////         }catch(Exception e)
////         {
////             return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
////         }
////    }
//
//
//
//}


@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailSchedulingService emailSchedulingService;

    @PostMapping("/schedule")
    public String scheduleEmail(@RequestBody EmailRequest request) throws SchedulerException {

        System.out.println("--------------------------------------------------------------------");
        System.out.println(request.getTo());
        System.out.println(request.getText());
        System.out.println(request.getScheduledTime());
        System.out.println(request.getSubject());

        emailSchedulingService.scheduleEmail(request);
        return "Email scheduled successfully!";
    }
}


