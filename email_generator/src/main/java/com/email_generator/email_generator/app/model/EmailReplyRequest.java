package com.email_generator.email_generator.app.model;

import lombok.Data;

@Data
public class EmailReplyRequest {

    private String emailContent;
    private String tone; //friendly , casual , professional

}
