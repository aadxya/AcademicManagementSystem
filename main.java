import java.sql.*;
import java.util.*;

public class main {
    database d = new database();
    Statement st = d.getConnection();
    static Scanner sc = new Scanner(System.in);

    private static String serial_input(){
        String in = sc.nextLine();
        try {
            Integer.parseInt(in);
        } catch (Exception e) {
            System.out.println("Invalid Input!");
            return "continue";
        }
        return in;
    }

    private static void mainMenu() throws SQLException {
        System.out.println("Welcome!");
//        int selectedOption = 0;
        while(true){
            System.out.println();
            System.out.println("What is your role?");
            System.out.println("");
            System.out.println("--------------------------------------");
            System.out.println("          1. Student                   ");
            System.out.println("          2. Instructor                ");
            System.out.println("          3. Administrator              ");
            System.out.println("          4. Exit                      ");
            System.out.println("---------------------------------------");
            System.out.print("\nEnter Option: ");
                String selectedOption = serial_input();
                if(selectedOption.equals("continue"))continue;
                else {
                    int i = Integer.parseInt(selectedOption);
                    switch (i) {
                        case 1:
                            System.out.println("\n\n\n");
                            student st = new student();
                            st.studentLogin();
                            break;

                        case 2:
                            System.out.println("\n\n");
                            instructor in = new instructor();
                            in.instructorLogin();
                            break;

                        case 3:
                            System.out.println("\n\n\n");
                            admin ad = new admin();
                            ad.adminLogin();
                            break;

                        case 4:
                            System.out.println("Exiting the application...");
                            System.exit(0);

                        default:
                            System.out.println("Error! Please input only the number options available above!!!");
                            System.out.println("\n\n");
                    }
                }
        }
    }


        public static void main(String[] args) throws SQLException{
        mainMenu();
    }

}
