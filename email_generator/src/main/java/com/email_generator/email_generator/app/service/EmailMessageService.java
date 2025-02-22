package com.email_generator.email_generator.app.service;

import com.email_generator.email_generator.app.model.EmailMessageRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Service
public class EmailMessageService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;  //here value will be got from the application.properties from key:gemini.api.url

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public EmailMessageService(WebClient.Builder webClientBuilder) {      //constructor injection
        this.webClient = webClientBuilder.build();
    }

    public String generateEmailMessage(EmailMessageRequest emailMessageRequest) {

        //build the prompt
        String prompt = buildPrompt(emailMessageRequest);

        //craft request
        Map<String,Object> requestBody = Map.of(
                "contents",new Object[]{
                        Map.of("parts",new Object[]{
                                  Map.of("text",prompt)
                        } )
                }
        );

        //do request and get request
        String response = webClient.post().uri(geminiApiUrl + geminiApiKey)
                .header("content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //Extract response and return response
        return extractResponseContent(response);




    }

    private String extractResponseContent(String response) {

        try{
            //here we have to send response in certain format  (check postman->Gemini Api collection)
            //ObjectMapper (from Jackson library) converts Java objects to JSON and vice versa.
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);  //readTree() is method that turns json into tree like structure
            return rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")    //text will contain actual answer
                    .asText();

        }
        catch(Exception e){
            return "Error processing request:" + e.getMessage();}

    }

    //prompt : Generate an email with a formal tone for the purpose of Job Application. The response should be within 150 words."
    private String buildPrompt(EmailMessageRequest emailMessageRequest) {
        StringBuilder prompt = new StringBuilder("Generate an email with ");

        // Append tone if available
        if (emailMessageRequest.getTone() != null) {
            prompt.append(emailMessageRequest.getTone()).append(" tone ");
        }

        // Append purpose if available
        if (emailMessageRequest.getPurpose() != null) {
            prompt.append("for the purpose of ").append(emailMessageRequest.getPurpose()).append(". ");
        }

        // Append word limit if available
        if (emailMessageRequest.getWordLimit() != null) {
            prompt.append("The response should be within ").append(emailMessageRequest.getWordLimit()).append(" words.");
        }

        return prompt.toString();
    }
}
