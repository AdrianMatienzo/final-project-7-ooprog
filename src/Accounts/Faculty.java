public class Faculty extends Account{
    private String role;

    public Faculty(String username, String password) {
        super(username, password);
        this.role = "Faculty";
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
