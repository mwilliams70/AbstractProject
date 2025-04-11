import java.util.Arrays;
import java.util.Scanner;

public class testing {
    public static void main(String[] args) {
        accountGeneration ag = new accountGeneration();
        Scanner sc = new Scanner(System.in);
        ag.connect("abstract_project", "root", "student");

        System.out.print("Enter First Name: ");
        String fName = sc.nextLine();

        System.out.print("Enter Last Name: ");
        String lName = sc.nextLine();

        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        System.out.println("----Colleges----");
        System.out.println("[1] College of Art and Design");
        System.out.println("[2] Saunders College of Business");
        System.out.println("[3] Golisano College of Computing and Information Sciences\n");
        System.out.print("Enter number(s): ");
        String cID = sc.nextLine();
        String[] arr = cID.split(" ");
        System.out.println("This is the array: " + Arrays.toString(arr));

        System.out.print("What building is your office in? ");
        String building = sc.nextLine();

        System.out.print("Enter office number: ");
        int officeNum = sc.nextInt();

        System.out.print("Enter username: ");
        String un = sc.nextLine();

        System.out.print("Enter password: ");
        String pswd = sc.nextLine();

        
    }
}
