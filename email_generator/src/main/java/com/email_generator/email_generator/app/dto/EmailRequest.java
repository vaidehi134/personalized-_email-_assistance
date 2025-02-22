package com.email_generator.email_generator.app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String text;
    private LocalDateTime scheduledTime;
}
