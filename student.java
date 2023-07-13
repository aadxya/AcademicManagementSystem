import java.io.*;
import java.sql.*;
import java.util.*;

public class student {
    static database d = new database();
    static Statement st = d.getConnection();
    static Scanner sc = new Scanner(System.in);
    static String currYear = null, currSem = null;

    private static String serial_input(){
//        System.out.println("-1. back");
        String in = sc.nextLine();
//        if(in.equals("-1"))
//        {
//            clear.clearConsole();
//            return "Back";
//        }
        try {
            Integer.parseInt(in);
        } catch (Exception e) {
            System.out.println("Invalid Input!");
            return "continue";
        }
        return in;
    }

    private static String gradeConvert(String grade) {
        if (grade.equals("A"))return "10";
        else if (grade.equals("A-"))return "9";
        else if (grade.equals("B"))return "8";
        else if (grade.equals("B-"))return "7";
        else if (grade.equals("C"))return "6";
        else if (grade.equals("C-"))return "5";
        else if (grade.equals("D"))return "4";
        else return "0";
    }

    static Boolean getStatus(String year, String sem)throws SQLException{ //true- completed, false- running
        boolean t1 = currYear.equals(year);
        boolean t2 = currSem.equals(sem);
        return (t1&&t2);
    }

    static void studentLogin() throws SQLException{
        while(true){
//            Scanner sc = new Scanner(System.in);
            System.out.println("In Student Login Now!");
            System.out.println("enter -1 to go back, enter anything else to proceed:");
            String in = sc.nextLine();
            if(in.equals("-1"))return;
            System.out.println("Student Login-");
            System.out.println("Enter login id-");
            String login = sc.nextLine();
            System.out.println("Enter password-");
            String password = sc.nextLine();
            ResultSet rs = st.executeQuery("select student_pass from student where student_id ='" + login+"';");
            if(rs.next()){
                String pass = rs.getString("student_pass");
                if(pass.equals(password)){
                    System.out.println("Correct Password");
                    studentMenu(login);
                }
            }
            else{
                System.out.println("Username not found");
            }
        }
    }

    static void studentMenu(String ID) throws SQLException{
        System.out.println("In Student Menu Now!");
        String query = "select * from acadSess";
        ResultSet rs = st.executeQuery(query);
        rs.next();
        currYear = rs.getString("currYear");
        currSem = rs.getString("currSem");
        while (true){
            System.out.println("1. View enrolled courses");
            System.out.println("2. Add a course");
            System.out.println("3. Drop a course");
            System.out.println("4. View CGPA");
            System.out.println("-1. Back");
            System.out.println("Select an option-");
            String option = serial_input();
            if(option.equals("continue"))continue;
            else{
                int i = Integer.parseInt(option);
                switch (i){
                    case 1:
                        viewCourse(ID);
                        break;
                    case 2:
                        addCourse(ID);
                        break;
                    case 3:
                        dropCourse(ID);
                        break;
                    case 4:
                        showCG(ID);
                        break;
                    case -1:
                        return;
                    default:
                        System.out.println("Enter Valid Input");
                }
            }
        }
    }

    static void viewCourse(String ID) throws SQLException{
        System.out.println("In Course Viewer Now!");
        String query = "select * from st" +ID+";";
        ResultSet rs = st.executeQuery(query);
        String format = "%15s %15s %15s %15s %15s %15s %15s %15s \n\n";
        while (rs.next()) {
            String id = rs.getString("course_id");
            String name = rs.getString("course_name");
            String type = rs.getString("course_type");
            String credits = rs.getString("credits");
            String ltp = rs.getString("ltp");
            String grade = rs.getString("grade");
            String currYear = rs.getString("currYear");
            String sem = rs.getString("sem");
            System.out.format(format, id, name, type, credits, ltp, grade, currYear, sem);
        }
    }

    static void dropCourse(String ID) throws SQLException{
        while (true){
            System.out.println("Enter the course you want to drop-");
            System.out.println("(Enter the -1 to go back-)");
            String choice = sc.nextLine();
            if(choice.equals("-1"))return;
            choice = choice.toLowerCase();
            String query = "select * from st" +ID+ " where currYear = '"+currYear+"' AND sem = '"+currSem+"' AND course_id = '" +choice+ "';";
            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                String course_id = rs.getString("course_id");
                String year = rs.getString("currYear");
                String sem = rs.getString("sem");
                query = "delete from " +course_id+year+sem+" where student_id = ('"+ID+"');";
                try{
                    //--------
                    st.executeUpdate(query);
                    System.out.println("Student dropped from course table");
                }catch (Exception e)
                {
                    System.out.println("Error in deleting data from table/ Data already not in table!");
                }
                query = "delete from st"+ID+" where course_id = '" +course_id+ "';";
                try{
                    //--------
                    st.executeUpdate(query);
                    System.out.println("Dropped course from student table");
                }catch (Exception e)
                {
                    System.out.println("Error in deleting data from table/ Data already not in table!");
                    System.out.println(e);
                }
            }
            else{
                System.out.println("No such course found to drop!!!");
            }
        }
    }

    static void showFloated() throws SQLException{
        System.out.println("In Floated Courses Now!");
        String query = "select * from courseFloat where currYear = '"+currYear+"' AND sem = '"+currSem+"';";
        ResultSet rs = st.executeQuery(query);
        String format = "%15s %15s %15s %15s %15s %15s %15s %15s %15s \n\n";
        while (rs.next()) {
            String id = rs.getString("course_id");
            String name = rs.getString("course_name");
            String credits = rs.getString("credits");
            String ltp = rs.getString("ltp");
            String instructor_name = rs.getString("instructor_name");
            String prereq = rs.getString("prereq");
            String cgreq = rs.getString("cgreq");
            System.out.format(format, "course id", "course name", "credits", "LTP", "Instructor", "Prerequisite", "CG Cutoff", "Year", "Sem");
            System.out.format(format, id, name, credits, ltp, instructor_name, prereq, cgreq, currYear, currSem);
        }
    }

    static void showCG(String ID) throws SQLException{
        boolean flag = false;
        double gxc = 0.0;
        double c = 0.0;
        String query = "select * from st" + ID +" where grade NOT IN ('E', 'F', 'NA');";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()){
            flag = true;
            double grade = Double.parseDouble(gradeConvert(rs.getString("grade")));
            double credits = Double.parseDouble(rs.getString("credits"));
            gxc += (grade*credits);
            c += credits;
        }
        gxc = gxc/c;
        System.out.println("CGPA is-!");
        if(flag){
            System.out.println(gxc);
        }
        else{
            System.out.println("0.0");
        }
    }

    static boolean cgreqCleared(String ID, String cgreq) throws SQLException{
        boolean flag = false;
        double gxc = 0.0;
        double c = 0.0;
        String query = "select * from st" + ID +" where grade NOT IN ('E', 'F', 'NA');";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()){
            flag = true;
            double grade = Double.parseDouble(gradeConvert(rs.getString("grade")));
            double credits = Double.parseDouble(rs.getString("credits"));
            gxc += (grade*credits);
            c += credits;
        }
        gxc = gxc/c;
        double cr = Double.parseDouble(cgreq);
        if(flag){
            return gxc >= cr;
        }
        else{
            return true;
        }
    }

    static boolean prereqCleared(String ID, String prereq) throws SQLException{
        if((prereq.equals(null))|| (prereq.equals(""))) {
            // System.out.println("---");
            return true;
        }
        String query = "select * from st" + ID +" where course_id = '"+prereq+"' AND grade NOT IN ('E', 'F', 'NA');";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()){
//            String year = rs.getString("currYear");
//            String sem = rs.getString("sem");
//            boolean flag = getStatus(year, sem);
//            return !flag;
            // System.out.println("+++");
            return true;
        }
        else{
            // System.out.println("ooo");
            return false;
        }
    }

    static boolean creditCleared(String ID, String credits) throws SQLException{
        String query = "select * from student where student_id = '"+ID+"';";
        ResultSet rs = st.executeQuery(query);
        rs.next();
        String batch = rs.getString("batch");
        double creditLimit;

        if(batch.equals(currYear)){
            query = "select * from batch where year = '"+batch+"';";
            rs = st.executeQuery(query);
            rs.next();
            String temp = null;
            if(currSem.equals("1")){
                temp = "semI";
            }
            else{
                temp = "semII";
            }
            creditLimit = Double.parseDouble(rs.getString(temp));
        }
        else{
            String prevYear=null, prevSem = null, prevprevYear = null, prevprevSem = null;
            if(currSem.equals("1")){
                int temp = Integer.parseInt(currYear);
                temp -= 1;
                prevYear = Integer.toString(temp);
                prevprevYear = Integer.toString(temp);
                prevSem = "2";
                prevprevSem = "1";
            }
            else{
                prevSem = "1";
                prevYear = currYear;
                int temp = Integer.parseInt(currYear);
                temp -= 1;
                prevprevYear = Integer.toString(temp);
                prevprevSem = "2";
            }
            double creditCount = 0;
            query = "select * from st"+ID+" where currYear = '"+prevYear+"' AND sem = '"+prevSem+"' AND grade NOT IN ('E', 'F'));";
            rs = st.executeQuery(query);
            while(rs.next()){
                String totCredits = rs.getString("credits");
                creditCount += Double.parseDouble(totCredits);
            }
            query = "select * from st"+ID+" where currYear = '"+prevprevYear+"' AND sem = '"+prevprevSem+"' AND grade NOT IN ('E', 'F');";
            rs = st.executeQuery(query);
            while(rs.next()){
                String totCredits = rs.getString("credits");
                creditCount += Double.parseDouble(totCredits);
            }
            creditLimit = (1.25 * creditCount)/2;
        }

        double tc = Double.parseDouble(credits);
        query = "select * from st" + ID + " where currYear = '"+currYear+"' AND sem = '"+currSem+"';";
        rs = st.executeQuery(query);
        while(rs.next()){
            String totCredits = rs.getString("credits");
            tc += Double.parseDouble(totCredits);
        }
        return !(tc > creditLimit);
    }

    static boolean courseCleared(String ID, String courseID) throws SQLException{
        String query = "select * from st" + ID +" where course_id = '"+courseID+"' AND grade NOT IN ('E', 'F', 'NA');";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()){
            return false;
        }
        else{
            return true;
        }
    }

    static void addCourse(String ID) throws SQLException{
        while (true){
            showFloated();
            System.out.print("\n\n");
            System.out.println("Enter the course you want to add-");
            System.out.println("(Enter -1 to go back)");
            String choice = sc.nextLine();
            if(choice.equals("-1"))return;
            choice = choice.toLowerCase();
            String query = "select * from courseFloat where currYear = '"+currYear+"' AND sem = '"+currSem+"' AND course_id = '" +choice+ "';";
//            query = "select * from courseFloat where stat = 'Running' AND course_id = '" +choice+ "';";
            boolean flag = false;
            ResultSet rs = st.executeQuery(query);
            if(rs.next())flag = true;
            if(flag){
                String prereq = rs.getString("prereq");
                String cgreq = rs.getString("cgreq");
                String cred = rs.getString("credits");
                String course_id = rs.getString("course_id");
                String course_name = rs.getString("course_name");
                String course_type = null;
                String ltp = rs.getString("ltp");
//                String grade = rs.getString("grade");
                String year = rs.getString("currYear");
                String sem = rs.getString("sem");
                boolean pr = prereqCleared(ID, prereq);
                // if(Objects.isNull(prereq)){
                //     pr = true;
                // }
                boolean cr = cgreqCleared(ID, cgreq);
                boolean cl = creditCleared(ID, cred);
                boolean cc = courseCleared(ID, choice);
                // System.out.print(prereq+"---");
                System.out.println(pr+"pr");
                System.out.println(cr+"cr");
                System.out.println(cl+"cl");
                System.out.println(cc+"cc");
                if(pr && cr && cl && cc){
                    query = "insert into " +course_id+year+sem+" values ('"+ID+"','NA');";
                    try{
                        //--------
                        st.executeUpdate(query);
                        System.out.println("Added into course table");
                        query = "select * from student where student_id = '"+ID+"';";
                        rs = st.executeQuery(query);
                        rs.next();
                        String batch = rs.getString("batch");
                        query = "select * from ug_cirriculum where batch = '"+batch+"' AND course_id = '"+course_id+"';";
                        rs = st.executeQuery(query);
                        if(!rs.next()){
                            course_type = "pe";
                        }
                        else {
                            course_type = rs.getString("course_type");
                        }
                    }catch (Exception e)
                    {
                        System.out.println("Error in adding data to table/ Data already in table!");
                    }
                    query = "insert into st"+ID+" values ('"+course_id+"', '"+course_name+"', '"+course_type +"', '"+cred+"', '"+ltp+"', 'NA', '"+year+"', '"+sem+"');";
                    try{
                        //--------
                        st.executeUpdate(query);
                        System.out.println("Added into student table");
                    }catch (Exception e)
                    {
                        System.out.println("Error in adding data to table/ Data already in table!");
                    }


                }
                else{
                    System.out.println("You aren't eligible for this course");
                }
            }
            else{
                System.out.println("No such course exists!!!");
            }
        }
    }
}