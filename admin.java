import java.io.*;
import java.sql.*;
import java.util.*;

public class admin {
    database d = new database();
    Statement st = d.getConnection();
    Scanner sc = new Scanner(System.in);

    private String serial_input(){
        String in = sc.nextLine();
        try {
            Integer.parseInt(in);
        } catch (Exception e) {
            System.out.println("Invalid Input!");
            return "continue";
        }
        return in;
    }

    void adminLogin() throws SQLException{
        while(true) {
            System.out.println("In Admin Login Now!");
            System.out.println("enter -1 to go back, enter anything else to proceed:");
            String in = sc.nextLine();
            if(in.equals("-1"))return;
            System.out.println("Admin Login-");
            System.out.println("Enter login id-");
            String login = sc.nextLine();
            System.out.println("Enter password-");
            String password = sc.nextLine();
            ResultSet rs = st.executeQuery("select admin_password from admin where admin_username ='" + login+"';");
            if(rs.next()){
                String pass = rs.getString("admin_password");
                if(pass.equals(password)){
                    System.out.println("Correct Password");
                    adminMenu();
                }
            }
            else{
                System.out.println("Username not found");
            }
        }
    }

    void adminMenu() throws SQLException{
        while (true){
            System.out.println("In Admin Menu Now!");
            System.out.println("1. Create a new batch");
            System.out.println("2. Add a new student");
            System.out.println("3. Add a new instructor");
            System.out.println("4. End semester");
            System.out.println("5. View student info");
            System.out.println("6. Generate student transcript");// TODO: 13-Mar-23  
            System.out.println("7. Add a course to course catalog");// TODO: 13-Mar-23
            System.out.println("-1. Back");
            System.out.println("Select an option-");
            String option = serial_input();
            if(option.equals("continue"))continue;
            else{
                int i = Integer.parseInt(option);
                switch (i){
                    case 1:
                        createBatch();
                        break;
                    case 2:
                        addStudent();
                        break;
                    case 3:
                        addInstructor();
                        break;
                    case 4:
                        endSemester();
                        break;
                    case 5:
                        viewStudentInfo();
                        break;
                    case 6:
                        generateTranscript();
                        break;
                    case 7:
                        addToCC();
                        break;
                    case -1:
                        return;
                    default:
                        System.out.println("Enter Valid Input");
                }
            }
        }
    }

    void createBatch() throws SQLException{
        // int x = 0;
        while (true){
            System.out.println("In Batch Creation Now!");
            System.out.println("Are you sure you want to add batch? (enter -1 to go back, anything else to continue)");
            // x++;
            // if(x!=1)
            // {
            //     return;
            // }
            String in = sc.nextLine();
            //System.out.println(in+"-----------");
            if(in.equals("-1")) return;

            System.out.println("Enter Year:");
            String year = serial_input();
            System.out.println("Enter # program courses:");
            String pc = serial_input();
            System.out.println("Enter # program electives:");
            String pe = serial_input();
            System.out.println("Enter # credits required for semI:");
            String semI = serial_input();
            System.out.println("Enter # credits required for semII:");
            String semII = serial_input();
            boolean flag = createCirriculum(year);
            
            if(!flag)return;
            try {
                st.executeUpdate("INSERT INTO batch VALUES ('" + year + "', '" + pc + "', '" + pe + "', '" + semI + "', '" + semII + "') ;");
                System.out.println("records filled");
            }
            catch (Exception e){
                System.out.println("Batch already inserted/ Unknown error!!!");
            }
        }
    }

    void addStudent() throws SQLException{
        while(true){
//            System.out.println("(Enter -1 to go back anytime)");
            System.out.println("In Student Add Now!");
            System.out.println("Enter Name:");
            String name = sc.nextLine();
            System.out.println("Enter ID:");
            String ID = sc.nextLine();
            System.out.println("Enter Password:");
            String password = sc.nextLine();
            System.out.println("Enter Phone Number:");
            String phone_no = sc.nextLine();
            System.out.println("Enter Department:");
            String dep = sc.nextLine();
            System.out.println("Enter Batch:");
            String batch = sc.nextLine();
            System.out.println("Are you sure you want to add the student? (enter -1 to go back, anything else to continue)");
            String in = sc.nextLine();
            if(in.equals("-1")) return;
            try {
                st.executeUpdate("INSERT INTO student VALUES ('" + name + "', '" + ID + "','" + password + "', '" + phone_no + "', '" + dep + "', '" + batch +"');");

                String createTableQuery = "DROP TABLE IF EXISTS st"+ID+"; CREATE TABLE st" + ID + " (course_id TEXT PRIMARY KEY, course_name TEXT, course_type TEXT, credits INT, ltp TEXT, grade TEXT, currYear TEXT, sem TEXT);";
                boolean rowsAffected = st.execute(createTableQuery);

                if (rowsAffected == false) {
                    System.out.println("Table st" + ID + " created successfully.");
                } else {
                    System.out.println("Table creation failed.");
                }
                System.out.println("Records filled");
            }
            catch (Exception e){
                System.out.println("Record already present");
            }
        }
    }

    void addInstructor() throws SQLException{
        while(true){
            System.out.println("In Instrcutor Add Now!");
            System.out.println("Enter Name:");
            String name = sc.nextLine();
            System.out.println("Enter ID:");
            String ID = sc.nextLine();
            System.out.println("Enter Password:");
            String password = sc.nextLine();
            System.out.println("Enter Phone Number:");
            String phone_no = sc.nextLine();
            System.out.println("Enter Department:");
            String dep = sc.nextLine();
            System.out.println("Are you sure you want to add the instructor? (enter -1 to go back, anything else to continue)");
            String in = sc.nextLine();
            if(in.equals("-1")) return;
            try {
                st.executeUpdate("INSERT INTO instructor VALUES ('" + ID + "', '" + password + "','" + name + "', '" + dep + "', '" + phone_no + "');");
                System.out.println("records filled");
                System.out.println("Instrcutor added");
            }
            catch (Exception e){
                System.out.println("Record already present");
            }
        }
    }

    boolean createCirriculum(String year) throws SQLException{
//        boolean flag= false;
        System.out.println("Enter the file path:");
//        File file = null;
        String line;
        String query;
        String fp = sc.nextLine();
        try {
//            file = new File(fp);
            BufferedReader br = new BufferedReader(new FileReader(fp));
            try  {
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    query = "insert into ug_cirriculum (batch,course_id, course_type) values ('"+year+"','"+ values[0] +"', '"+values[1]+"');";
                    st.executeUpdate(query);
                }
                br.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Please enter file path in a valid format: \"C:/Windows/System32\"");
        }
        return false;
    }

    void viewStudentInfo() throws SQLException {
        while(true) {
            System.out.println("In Student Info Viewer Now!");
            System.out.println("Enter the ID of the student who's info is to be viewed: ");
            System.out.println("(Enter the -1 to go back)");
            String ID = sc.nextLine();
            if(ID.equals("-1"))return;
            String query = "select * from st" + ID + ";";
            try {
                System.out.println("---");
                ResultSet rs = st.executeQuery(query);
                String format = "%15s %15s %15s %15s %15s %15s %15s %15s\n";
                System.out.format(format, "course id", "course name", "course type", "credits", "LTP", "grade", "year", "sem");
                while (rs.next()) {
                    String id = rs.getString("course_id");
                    String name = rs.getString("course_name");
                    String type = rs.getString("course_type");
                    String credits = rs.getString("credits");
                    String ltp = rs.getString("ltp");
                    String grade = rs.getString("grade");
                    String year = rs.getString("currYear");
                    String sem = rs.getString("sem");
                    System.out.format(format, id, name, type, credits, ltp, grade, year, sem);
                }
            }
            catch (Exception e){
                System.out.println("Student does not exist");
            }
        }
    }

    void endSemester() throws SQLException{
        System.out.println("Ending semester now...");
        String query = "select * from acadSess";
        ResultSet rs = st.executeQuery(query);
        rs.next();
        String year = rs.getString("currYear");
        String sem = rs.getString("currSem");
        String nextYear = null, nextSem = null;
        if (sem.equals("1")) {
            nextSem = "2";
            nextYear = year;
        } else {
            int temp = Integer.parseInt(year);
            temp += 1;
            nextYear = Integer.toString(temp);
            nextSem = "1";
        }
        try {
            query = "delete from acadSess where currYear = '"+year+"' AND currSem = '"+sem+"';";
            st.executeUpdate(query);
            query = "insert into acadSess values('"+nextYear+"','"+nextSem+"') ;";
            st.executeUpdate(query);
            System.out.println("Semester Ended!!!");
        } catch (Exception e) {
            System.out.println("Something went wrong, try again!!!");
        }
        
    }

    void addToCC() throws SQLException{
        while(true){
            System.out.println("Give details of the course you want to add-");
            System.out.println("Enter course id-");
            String course_id = sc.nextLine();
            System.out.println("Enter course name-");
            String course_name = sc.nextLine();
            System.out.println("Enter course credits-");
            String credits = sc.nextLine();
            System.out.println("Enter course ltp-");
            String ltp = sc.nextLine();
            System.out.println("Enter course prerequisites-");
            String prereq = sc.nextLine();
            System.out.println("Are you sure you want to continue?");
            String in = sc.nextLine();
            if(in.equals("-1"))return;
            String query = "insert into courseCatalog values('"+course_id+"','"+course_name+"','"+credits+"','"+ltp+"','"+prereq+"');";
            try {
                st.executeUpdate(query);
                System.out.println("Course added!!!");
            } catch (Exception e) {
                System.out.println("Course not added!!!/Course already present!!!");
                System.out.println(e);
                // TODO: handle exception
            }
        }
    }

    void generateTranscript() throws SQLException{
        System.out.println("Enter the ID of the student who's transcript is to be generated:");
        String student_id = sc.nextLine();
        System.out.println("Enter the file path where the transcript is to be generated:");
        String file_path = sc.nextLine();
        try {
            File myObj = new File(file_path);
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;
          }

          try {
            FileWriter myWriter = new FileWriter(file_path);
            myWriter.write("Transcript:\n");
            myWriter.write("-------------------\n");
            String query = "select * from student where student_id ='"+student_id+"';";
            ResultSet rs = st.executeQuery(query);
            rs.next();
            myWriter.write("\nName: " + rs.getString("student_name"));
            myWriter.write("\nStudentID: " + rs.getString("student_id"));
            myWriter.write("\nPhone No: " + rs.getString("phone_no"));
            myWriter.write("\nDepartment: " + rs.getString("department"));
            query = "select * from st"+student_id+";";
            rs = st.executeQuery(query);
            while(rs.next()){
                myWriter.write("\nCourse Name: " + rs.getString("course_name"));
                myWriter.write("\t\tCourse ID: " + rs.getString("course_id"));
                myWriter.write("\t\tCourse type: " + rs.getString("course_type"));
                myWriter.write("\t\tGrade: " + rs.getString("grade"));
                myWriter.write("\t\tSession: " + rs.getString("currYear")+"-"+rs.getString("sem"));
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}