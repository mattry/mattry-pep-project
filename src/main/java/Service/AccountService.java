package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    // start constructors

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    // end constructors 

    // register new account
    // username cannot be blank
    // password must be > 4 chars
    // username cannot be taken

    public Account registerAccount(String username, String password) {
        // check if username is blank
        if (username == null) {
            return null;
        }

        // check if password is null or too short
        if (password == null || password.length() < 4) {
            return null;
        }

        // check if an account with the username is already in database
        Account exists = accountDAO.getAccountByUsername(username);
        if (exists != null) {
            return null;
        }

        // create new account and inster into database
        Account newAccount = new Account(username, password);

        return accountDAO.insertAccount(newAccount);
    }

    // authenticate credentials
    // username and password must match a real account in the database

    public Account login(String username, String password) {
        // check if username is blank
        if (username == null) {
            return null;
        }

        // check if password is null or too short
        if (password == null || password.length() < 4) {
            return null;
        }

        return accountDAO.getAccountByUsernameAndPassword(username, password);

    }




}
