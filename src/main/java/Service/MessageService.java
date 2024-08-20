package Service;

import Model.Message;
import Model.Account;
import DAO.MessageDAO;
import DAO.AccountDAO;
import java.util.*;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    // start constructors
    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }
    // end constructors 

    /*
     * create a new message
     * message_text cannot be blank, or > 255 chars
     * posted_by must == real account_id
     */
    public Message createMessage(String message_text, int posted_by, long time_posted_epoch) {
        // check message_text is not null, blank, or too long
        if (message_text == null || message_text.isBlank() || message_text.length() > 255) {
            return null;
        }

        // check that user with id == posted_by exists
        Account exists = accountDAO.getAccountByAccountId(posted_by);
        if (exists == null) {
            return null;
        }

        // create a new message and insert it into the database
        Message message = new Message(posted_by, message_text, time_posted_epoch);
        return messageDAO.insertMessage(message);
    }

    // return a list of all messages in the database, posted by any user
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // return a message based on its id
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    // delete a message based on its id
    public void deleteMessageById(int message_id) {
        messageDAO.deleteMessageById(message_id);
    }

    public void updateMessageText(int message_id, String message_text) {
        messageDAO.updateMessageText(message_id, message_text);
    }

    public List<Message> getMessagesByPoster(int posted_by) {
        return messageDAO.getMessagesByPoster(posted_by);
    }

    
}
