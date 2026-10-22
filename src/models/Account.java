package models;

public abstract class Account {
    private String userName;
    private String password;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract String getRole();

    public abstract void setRole(String role);

    public String toString(){
        return String.format("%s|%s|%s", getUserName(), getPassword(), getRole());
    }

}
