import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
class studentTest {
    static private final InputStream systemIn = System.in;
    static private final PrintStream systemOut = System.out;
    static private ByteArrayInputStream testIn;
    static private ByteArrayOutputStream testOut;

    void provideInput(String inputString) {
        testIn = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testIn);
    }

    void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    static private void restoreStream() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void xyz() {
        setUpOutput();
        test1.test1Test();
        assertTrue(testOut.toString().contains("Hello World"));
        restoreStream();
    }

    @Test
    public void studentLoginFail() throws SQLException {
        String in = "\nrandom\nrandon\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Username not found"));
        restoreStream();
    }

    @Test
    public void studentAddCourse01() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n2\ncs202\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse02() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n2\ncs201\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourseAlreadyAddedError() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n2\ncs202\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Error in adding data to table/ Data already in table!"));
        restoreStream();
    }

    @Test
    public void studentAddCourseInvalidCourse() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n2\ncs101\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("No such course exists!!!"));
        restoreStream();
    }

    @Test
    public void studentAddCourse11() throws SQLException {
        String in = "\n2020chb1057\n2020chb1057\n2\ncs202\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse12() throws SQLException {
        String in = "\n2020chb1057\n2020chb1057\n2\ncs201\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse21() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n2\ncs202\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse22() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n2\ncs201\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentDropCourse1() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n3\ncs202\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Student dropped from course table"));
        restoreStream();
    }

    @Test
    public void studentDropCourse2() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n3\ncs201\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Student dropped from course table"));
        restoreStream();
    }

    @Test
    public void studentDropCourseErrorNotFound() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n3\ncs101\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("No such course found to drop"));
        restoreStream();
    }

    @Test
    public void studentViewCourse() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("In Course Viewer Now!"));
        restoreStream();
    }

    @Test
    public void viewCG1() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n4\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("CGPA is-!"));
        restoreStream();
    }

    @Test
    public void viewCG2() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n4\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("CGPA is-!"));
        restoreStream();
    }

    @Test
    public void viewCG3() throws SQLException {
        String in = "\n2020chb1057\n2020chb1057\n4\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("CGPA is-!"));
        restoreStream();
    }

    @Test
    public void serialAdminReach() throws SQLException {
        String in = "\n2020chb1057\n2020chb1057\na\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Invalid Input!"));
        restoreStream();
    }

    //----------Semester2

    @Test
    public void studentAddCourse100() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n2\ncs301\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("You aren't eligible for this course"));
        restoreStream();
    }

    @Test
    public void studentAddCourse101() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n2\ncs302\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse102() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n2\nhs102\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }


    @Test
    public void studentAddCourse200() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n2\ncs301\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("You aren't eligible for this course"));
        restoreStream();
    }

    @Test
    public void studentAddCourse201() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n2\ncs302\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("You aren't eligible for this course"));
        restoreStream();
    }

    @Test
    public void studentAddCourse202() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n2\nhs102\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }


    @Test
    public void studentAddCourse300() throws SQLException {
        String in = "\n2020chb1057\n2020chb1057\n2\ncs301\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse301() throws SQLException {
        String in = "\n2020chb1057\n2020chb1057\n2\ncs302\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse302() throws SQLException {
        String in = "\n2020chb1057\n2020chb1057\n2\nhs102\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    //-------------Semester 3

    @Test
    public void studentAddCourse311() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n2\nhs103\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse321() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n2\nhs103\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse331() throws SQLException {
        String in = "\n2020chb1057\n2020chb1057\n2\nhs103\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    //-------------semester4

    @Test
    public void studentAddCourse411() throws SQLException {
        String in = "\n2020chb1039\n2020chb1039\n2\nhs104\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse421() throws SQLException {
        String in = "\n2020chb1041\n2020chb1041\n2\nhs104\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }

    @Test
    public void studentAddCourse431() throws SQLException {
        String in = "\n2020chb1057\n2020chb1057\n2\nhs104\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        student.studentLogin();
        assertTrue(testOut.toString().contains("Added into student table"));
        restoreStream();
    }
}
