package com.email_generator.email_generator.app.controller;
import com.email_generator.email_generator.app.dto.EmailRequest;
import com.email_generator.email_generator.app.model.EmailMessageRequest;
import com.email_generator.email_generator.app.service.EmailMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/email")
public class EmailMessageController {

    @Autowired
    private EmailMessageService emailMessageService;

    @PostMapping("/generate/message")
    ResponseEntity<String>  generateEmail(@RequestBody EmailMessageRequest emailMessageRequest)
    {
        String response = emailMessageService.generateEmailMessage(emailMessageRequest);
        return ResponseEntity.ok(response);
    }
}



