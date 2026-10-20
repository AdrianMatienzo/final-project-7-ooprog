package models;

public class Admin extends Account {
    private String role;

    public Admin(String username, String password) {
        super(username, password);
        this.role = "Admin";
    }

    @Override 
    public String getRole() {
        return role;
    }

    @Override 
    public void setRole(String role) {
        this.role = role;
    }
    
}
