import java.util.*;

public class Tester {
    public static void main(String[] args) {
        Logic logic;
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the LVM system! Enter your commands:\n\n");
        System.out.println("cmd#: ");
        String userChoice = s.nextLine();
        logic.choices(userChoice);




    }
}
