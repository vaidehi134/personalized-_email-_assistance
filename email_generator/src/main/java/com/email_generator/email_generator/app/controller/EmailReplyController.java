package com.email_generator.email_generator.app.controller;
import com.email_generator.email_generator.app.model.EmailReplyRequest;
import com.email_generator.email_generator.app.service.EmailReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")

public class EmailReplyController {

    private final EmailReplyService emailReplyService;

    public EmailReplyController(EmailReplyService emailReplyService) {
        this.emailReplyService = emailReplyService;
    }  //we can use @AllArgsConstructor instead of this

      @PostMapping("/generate")
    public ResponseEntity<String> generateEmail(@RequestBody EmailReplyRequest emailReplyRequest){
        String response = emailReplyService.generateEmailReply(emailReplyRequest);
        return ResponseEntity.ok(response);
    }
}
