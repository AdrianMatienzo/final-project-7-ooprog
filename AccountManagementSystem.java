public class AccountManagementSystem {
    public void addAccount(Account account) {
        AccountsList.UserAccounts.add(account);
    }

    public void removeAccount(Account account) {
        AccountsList.UserAccounts.remove(account);
    }
}
