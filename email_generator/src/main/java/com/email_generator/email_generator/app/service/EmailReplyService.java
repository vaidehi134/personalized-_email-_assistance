package com.email_generator.email_generator.app.service;


import com.email_generator.email_generator.app.model.EmailReplyRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailReplyService {

//You can use @Autowired on private final WebClient webClient, but it won't work because Spring injects dependencies after
// the object is created, and final fields must be set during object creation.
// Constructor injection works because it assigns the final field at creation time.
    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;  //here value will be got from the application.properties from key:gemini.api.url

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public EmailReplyService(WebClient.Builder webClientBuilder) {      //constructor injection
        this.webClient = webClientBuilder.build();
    }

    public String generateEmailReply(EmailReplyRequest emailReplyRequest)
    {
        //build the prompt(input to the gemini api
        String prompt = buildPrompt(emailReplyRequest);

        //craft a request
        //we need request in this form......(
        //{
        //  "contents": [{
        //    "parts":[{"text": "Explain how AI works"}]
        //    }]
        //   }  ---> it's a key value pair so we will use Map

        Map<String, Object> requestBody = Map.of(
                "contents",new Object[] {
                        Map.of("parts",new Object[]{
                                Map.of("text",prompt)
                        })
                }
        );


        //do request and get response

//to make api call we will use WebClient -it's a way to do a API request . and it's built on top of project reactor
        //and it enables us to handle asyncronous  non-blocking http request and responses. it makes this thing
        //well suited for morden reactive web applications and for this we will need webflux dependency
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

    private String buildPrompt(EmailReplyRequest emailReplyRequest) {
         StringBuilder prompt = new StringBuilder(); //what is diffrence between StringBuilder and String
         prompt.append("Generate a professional email reply for the following content. Please don't generate a subject line ");
         if(emailReplyRequest.getTone() != null && !emailReplyRequest.getTone().isEmpty()){
             prompt.append("Use a ").append(emailReplyRequest.getTone()).append("tone.");
         }
         prompt.append("\nOriginal email: \n").append(emailReplyRequest.getEmailContent());
         return prompt.toString();
    }
}
