package Controller;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.*;
import Service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private final AccountService accountService = new AccountService();
    private final MessageService messageService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */ 
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/example-endpoint", this::exampleHandler);
        app.post("/register", this::accountRegistrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{id}", this::getMessageByIdHandler);
        app.delete("/messages/{id}", this::deleteMessageHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
      */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
   
    // Our API should be able to process new User registrations.
    private void accountRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.registerAccount(account.getUsername(), account.getPassword());
        if (newAccount != null) {
            ctx.json(newAccount);
        }else{
            ctx.status(400);
        }
    }

    // Our API should be able to process User logins.
    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account authAccount = accountService.login(account.getUsername(), account.getPassword());
        if (authAccount == null) {
            ctx.status(401);
        } else {
            ctx.json(authAccount);
        }
        
    }

    // Our API should be able to process the creation of new messages.
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message created = messageService.createMessage(message.getMessage_text(), message.getPosted_by(), message.getTime_posted_epoch());
        if (created == null) {
            ctx.status(400);
        } else {
            ctx.json(created);
        }
    }

    // Our API should be able to retrieve all messages.
    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }

    // Our API should be able to retrieve a message by its ID.
    private void getMessageByIdHandler(Context ctx) {
        String id_string = ctx.pathParam("id");

        Integer message_id = Integer.parseInt(id_string);
        Message message = messageService.getMessageById(message_id);

        if (message != null) {
            ctx.json(message);
        }
        ctx.status(200).result("");
    }

    // Our API should be able to delete a message identified by a message ID.
    private void deleteMessageHandler(Context ctx) {
        String id_string = ctx.pathParam("id");

        Integer message_id = Integer.parseInt(id_string);
        Message message = messageService.getMessageById(message_id);

        if (message != null) {
            ctx.json(message);
            messageService.deleteMessageById(message_id);
        }
        ctx.status(200).result("");
    }


}