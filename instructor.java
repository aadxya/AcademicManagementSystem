import java.io.*;
import java.rmi.StubNotFoundException;
import java.sql.*;
import java.util.*;

public class instructor {
    database d = new database();
    Statement st = d.getConnection();
    Scanner sc = new Scanner(System.in);

    String year = "";
    String sem = "";

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

    private String serial_double(){
        String in = sc.nextLine();
        try {
            Double.parseDouble(in);
        } catch (Exception e) {
            System.out.println("Invalid Input!");
            return "continue";
        }
        return in;
    }

    void instructorLogin() throws SQLException{
        while(true){
            System.out.println("In Instructor Login Now!");
//            Scanner sc = new Scanner(System.in);
            System.out.println("Instructor Login-");
            System.out.println("Enter login id-");
            String login = sc.nextLine();
            System.out.println("Enter password-");
            String password = sc.nextLine();
            ResultSet rs = st.executeQuery("select * from instructor where instructor_id ='" + login+"';");
            if(rs.next()){
                String pass = rs.getString("instructor_pass");
                String name = rs.getString("instructor_name");
                if(pass.equals(password)){
                    System.out.println("Correct Password");
                    instructorMenu(login, name);
                }
            }
            else{
                System.out.println("Username not found");
            }
            System.out.println("Enter -1 to go back, anything else to continue");
            String in = sc.nextLine();
            if(in.equals("-1"))return;
        }
    }

    void instructorMenu(String ID, String name) throws SQLException{
        System.out.println("In Instrcutor Menu Now!");
        String query = "select * from acadSess";
        ResultSet rs = st.executeQuery(query);
        rs.next();
        year = rs.getString("currYear");
        sem = rs.getString("currSem");
        while (true){
            System.out.println("1. Float a course");
            System.out.println("2. Drop a course");
            System.out.println("3. View enrolment list");
            System.out.println("4. Update marks");
            System.out.println("5. View student details");
            System.out.println("-1. Back");
            String option = serial_input();
            if(option.equals("continue"))continue;
            else{
                int i = Integer.parseInt(option);
                switch (i){
                    case 1:
                        floatCourse(ID, name);
                        break;
                    case 2:
                        dropCourse(ID);
                        break;
                    case 3:
                        viewEnrolment(ID);
                        break;
                    case 4:
                        updateGrade(ID);
                        break;
                    case 5:
                        admin ad= new admin();
                        ad.viewStudentInfo();
                        break;
                    case -1:
                        return;
                    default:
                        System.out.println("Enter Valid Input");
                }
            }
        }
    }

    void floatCourse(String ID, String name) throws SQLException{
        while(true){
            System.out.println("Are you sure you want to continue? (Enter -1 to go back, anything else to continue: )");
            String in = sc.nextLine();
            if(in.equals("-1"))return;
            System.out.println("Enter the course code of the course to be floated: ");
            String courseCode = sc.nextLine();
            courseCode = courseCode.toLowerCase();
            System.out.println("Enter CG Cut-off: ");
            String cgreq = serial_double();
            if(cgreq.equals("continue"))continue;
            else{
                double temp = Double.parseDouble(cgreq);
                if(temp < 0.0 && temp > 10.0){
                    System.out.println("Input is invalid, try again!!!");
                    continue;
                }
            }
            String query = "select * from courseCatalog where course_id = '" +courseCode+ "';";
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                String course_id = rs.getString("course_id");
                String course_name = rs.getString("course_name");
                String ltp = rs.getString("ltp");
                String credits = rs.getString("credits");
                String prereq = rs.getString("prereq");
                query = "insert into courseFloat values ('" +course_id+ "', '" +course_name+  "', '" +credits+  "', '" +ltp+ "', '" +ID+  "', '" +name+  "', '" +prereq+ "', '" +cgreq+  "', '" + year + "', '" + sem +  "')";
                try{
                    //--------
                    st.executeUpdate(query);
                    query = "DROP TABLE IF EXISTS " +course_id+ year+ sem+ ";" +
                            "CREATE TABLE " + course_id+ year+ sem+ " (" +
                            "  student_id text," +
                            "  grade text," +
                            "  PRIMARY KEY (student_id)" +
                            ");";
                    st.execute(query);
                    System.out.println("Course floated!!!");
                }catch (Exception e)
                {
                    System.out.println("Error in floating course!!!/ Course has already been floated!!!");
                    System.out.println(e);
                }
                //System.out.println("Course floated!!!");
            }
            else{
                System.out.println("Invalid input, try again!!!");
            }
        }
    }

    void dropCourse(String ID) throws SQLException{
        while(true){
            String[] data_set=new String[100000];
            int i = 0;
            int rows = 0;
            System.out.println("Enter the course code of the course to be dropped: ");
            String courseCode = sc.nextLine();
            System.out.println("Are you sure you want to continue? (Enter -1 to go back, anything else to continue: )");
            String in = sc.nextLine();
            if(in.equals("-1"))return;
            courseCode = courseCode.toLowerCase();
            String query = "delete from courseFloat where course_id = '" +courseCode+ "' AND currYear = '"+year+"' AND sem = '"+sem+"' AND instructor_id = '"+ID+"';";
                try {
                    int entries = st.executeUpdate(query);
                    if(entries > 0){
                        query = "select * from "+courseCode+year+sem+";";
                        ResultSet rs = st.executeQuery(query);
                        while (rs.next()){
                            data_set[i] = rs.getString("student_id");
                            rows++;
                            i++;
                        }
                        // rows += 1;
                        i = 0;
                        while(i < rows){
                            query = "delete from st" +data_set[i]+ " where course_id = '"+courseCode+"' AND currYear = '"+year+"' AND sem = '"+sem+"';";
                            st.executeUpdate(query);
                            i++;
                        }
                        System.out.println("Course dropped successfully!!!");
                        query = "drop table "+courseCode+year+sem+";";
                        st.execute(query);
                    }
                    else{
                        System.out.println("Unauthorised action!!!");
                    }
                }
                catch (Exception e){
                    System.out.println("No such course is running!!!");
                    System.out.println(e);
                }
        }
    }

    void viewEnrolment(String ID){
        while(true){
            System.out.println("Enter the course code of the course who's enrolment is to be viewed:");
            String courseCode = sc.nextLine();
            courseCode = courseCode.toLowerCase();
            System.out.println("Enter the year of the course who's enrolment is to be viewed:");
            String yearC = sc.nextLine();
            yearC = yearC.toLowerCase();
            System.out.println("Enter the sem of the course who's enrolment is to be viewed:");
            String semC = sc.nextLine();
            semC = semC.toLowerCase();
            System.out.println("Are you sure you want to continue? (Enter -1 to go back, anything else to continue: )");
            String in = sc.nextLine();
            if(in.equals("-1"))return;
            String query = "select * from "+courseCode+yearC+semC+";";
            try {
                ResultSet rs = st.executeQuery(query);
                String format = "%15s %15s \n\n";
                System.out.format(format, "student id", "grade");
                while (rs.next()){
                    String id = rs.getString("student_id");
                    String grade = rs.getString("grade");
                    System.out.format(format, id, grade);
                }
            }
            catch (Exception e){
                System.out.println("Input is invalid!!!");
            }
        }
    }

    void updateGrade(String ID) throws SQLException {
        while(true) {
            System.out.println("Enter the course code of the course who's grade is to be updated:");
            String courseCode = sc.nextLine();
            courseCode = courseCode.toLowerCase();
            System.out.println("Are you sure you want to continue? (Enter -1 to go back, anything else to continue: )");
            String in = sc.nextLine();
            if(in.equals("-1"))return;
            String prevYear, prevSem;
            File file = null;
            String line;
            if (sem.equals("1")) {
                int temp = Integer.parseInt(year);
                temp -= 1;
                prevYear = Integer.toString(temp);
                prevSem = "2";
            } else {
                prevYear = year;
                prevSem = "1";
            }
            String query = "select * from courseFloat where course_id = '" + courseCode + "' AND currYear = '" + prevYear + "' AND sem = '" + prevSem + "' AND instructor_id = '" + ID + "';";
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                System.out.println("Enter the file path:");
                String fp = sc.next();
                try {
                    //file = new File(fp);
                    //String csvFile = "data.csv";
                    BufferedReader br = new BufferedReader(new FileReader(fp));
                    try  {
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(",");
                            query = "update st" + values[0] + " set grade = '"+values[1]+"' where course_id = '"+courseCode+"' AND currYear = '" + prevYear + "' AND sem = '" + prevSem + "';";
                            System.out.println(query);
                            st.executeUpdate(query);
                            query = "update " +courseCode+prevYear+prevSem+ " set grade = '"+values[1]+"' where student_id = '"+values[0]+"';";
                            System.out.println(query);
                            st.executeUpdate(query);
                            System.out.println("Updation completed!!!");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                catch(Exception e){
                    System.out.print(e);
                    System.out.println("Please enter file path in a valid format: \"C:/Windows/System32/\"");
                }
            }
            else{
                System.out.println("Unauthorised action!!!");
            }
        }
    }
}
