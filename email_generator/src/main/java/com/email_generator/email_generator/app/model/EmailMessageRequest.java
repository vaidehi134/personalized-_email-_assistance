package com.email_generator.email_generator.app.model;


import lombok.Data;

@Data
public class EmailMessageRequest {
    private String purpose;
    private String tone;
    private Integer wordLimit;
    //If wordLimit is not required, using Integer allows it to be null.
    //int cannot be null (defaults to 0), which might cause unwanted behavior.
}
