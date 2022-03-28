import java.io.IOException;
import java.util.*;

public class Tester {
    public static void main(String[] args) throws IOException {
        Logic logic = new Logic();
        logic.getData();
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the LVM system! Enter your commands:\n");
        System.out.print("cmd#: ");
        String userChoice = s.nextLine();
        while(!userChoice.equals("Exit"))
        {
            logic.choices(userChoice);
            System.out.print("cmd#: ");
            userChoice = s.nextLine();
        }

        System.out.println("Good Bye");
        logic.saveData();










    }
}
