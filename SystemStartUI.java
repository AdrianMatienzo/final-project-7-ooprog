public class SystemStartUI{
    final InputValidation inputValidator = new InputValidation();

    public void start(){
        AccountsList.UserAccounts.add(new Admin("admin", "admin123"));
        AccountsList.UserAccounts.add(new Faculty("faculty", "faculty123"));
        boolean isRunning = true;

        while (isRunning){
            System.out.println("=======================================================");
            System.out.println("                Student Management System              ");
            System.out.println("=======================================================");
            System.out.println("Welcome! Please Select an option below.");
            System.out.println("-------------------------------------------------------");

            System.out.println("[1] Log In");
            System.out.println("[2] Exit System");

            System.out.println("=======================================================");

            int choice = inputValidator.getValidatedIntegerMinMax("Enter your choice: ", "Invalid input! Please enter a valid option (1-2).", 1, 2);


            switch (choice){
                case 1:
                    logInProcess();
                    break;

                case 2:
                    isRunning = false;
                    System.out.println("Thank you for using the Student Management System!");
                    System.exit(0);
                    break;
            }
            
        }
    }

    public void logInProcess(){
        System.out.println("\n=======================================================");
        System.out.println("                      Log In Page                      ");
        System.out.println("=======================================================");
        String userName = promptUsername();
        String password = promptPassword();

        boolean logInSuccessful = false;

        for (Account account : AccountsList.UserAccounts){
            if (!account.getUserName().equals(userName) ||!account.getPassword().equals(password)) {
                continue;
            }

            logInSuccessful = true;
            System.out.println("Login Successful! Welcome, " + userName + "!");

            String role = account.getRole();
            switch (role){
                case "Admin":
                    AdminUI adminUI = new AdminUI();
                    adminUI.roleMenu();
                    break;

                case "Faculty":
                    FacultyUI facultyUI = new FacultyUI();
                    facultyUI.roleMenu();
                    break;

                default:
                    System.out.println("Invalid role. Please try again.");
            }

            break;
        }

        if(!logInSuccessful){
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    public String promptUsername(){
        return inputValidator.getValidatedStringInput("Enter Username: ", "Invalid input! Username cannot be empty.", "^.+$");
    }

    public String promptPassword(){
        return inputValidator.getValidatedStringInput("Enter Password: ", "Invalid input! Password cannot be empty.", "^.+$");
    }
}
