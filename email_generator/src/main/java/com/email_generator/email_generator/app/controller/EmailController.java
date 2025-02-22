package com.email_generator.email_generator.app.controller;
import com.email_generator.email_generator.app.dto.EmailRequest;
import com.email_generator.email_generator.app.service.EmailSchedulingService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        emailSchedulingService.scheduleEmail(request);
        return "Email scheduled successfully!";
    }
}
