package com.email_generator.email_generator.app.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String text;
    private LocalDateTime scheduledTime;
}



