import java.util.*;

public class Tester {
    public static void main(String[] args) {
        Logic logic = new Logic();
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the LVM system! Enter your commands:\n");
        System.out.print("cmd#: ");
        String userChoice = s.nextLine();
        while(!userChoice.equals("Exits"))
        {
            logic.choices(userChoice);
            System.out.print("cmd#: ");
            userChoice = s.nextLine();
        }









    }
}
