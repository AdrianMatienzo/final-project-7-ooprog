import java.util.Scanner;

public class InputValidation{
    private static Scanner sc = new Scanner(System.in);

    public String getValidatedStringInput(String prompt, String errorMessage, String regexPattern){
        String userInput = "";
        boolean running = true;

        while (running){
            System.out.print(prompt);
            userInput = sc.nextLine();

            if (userInput.matches(regexPattern)){
                break;
            }
            else{
                System.out.println(errorMessage);
            }
        }
        return userInput;
    }

    public int getValidatedIntegerMinMax(String prompt, String errorMessage, int min, int max){
        int value = 0;
        boolean running = true;

        while (running){
            System.out.print(prompt);
            String userInput = sc.nextLine().trim();

            if (!userInput.matches("-?(0|[1-9]\\d*)")) { 
                System.out.println(errorMessage); 
                continue; 
            }

            try {
                value = Integer.parseInt(userInput);
                if (value >= min && value <= max){
                    break;
                }
                System.out.println(errorMessage);
            } catch (NumberFormatException e){
                System.out.println("Please enter a valid integer number.");
            }
        }
        return value;
    }

    public double getValidatedDoubleMax(String prompt, String errorMessage, double max){
        double value = 0;
        boolean running = true;

        while (running){
            System.out.print(prompt);
            String userInput = sc.nextLine().trim();

            if (!userInput.matches("-?(0|[1-9]\\d*)(\\.\\d+)?")) {
                System.out.println(errorMessage);
                continue;
            }

            try {
                value = Double.parseDouble(userInput);
                if (value > 0 && value <= max){
                    break;
                }
                System.out.println(errorMessage);
            } catch (NumberFormatException e){
                System.out.println(errorMessage);
            }
        }
        return value;
    }

    public String getValidatedItemCategory(String prompt){
        String userInput = "";
        boolean running = true;

        String itemCategories[] = {"Clothing", "Electronics", "Entertainment"};

        while (running){
            System.out.print(prompt);   
            userInput = sc.nextLine().trim();

            for (String category : itemCategories){
                if (userInput.equalsIgnoreCase(category)){
                    userInput = category;
                    running = false;
                    break;
                }
            }

            if (!running){
                break;
            }
            
            System.out.printf("Category '%s' does not exist! Please enter a valid category.\n", userInput);
        }
        return userInput;
    }

}
