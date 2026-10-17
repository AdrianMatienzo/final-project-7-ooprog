package managers;

import objects.*;
import java.util.ArrayList;
import java.util.List;

public class AccountManagementSystemManager {
    private static AccountManagementSystemManager instance; 
    private List<Account> accountList = new ArrayList<>();

    private AccountManagementSystemManager() {}

    public static AccountManagementSystemManager getInstance() {
        if (instance == null){
            instance = new AccountManagementSystemManager();
        }
        return instance;
    }

    public List<Account> getAccountList() {
        return accountList;
    }


    public void addAccount(Account account) {
        accountList.add(account);
    }

    public void removeAccount(Account account) {
        accountList.remove(account);
    }
}
