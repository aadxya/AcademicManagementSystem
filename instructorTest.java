import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class instructorTest {
    static private final InputStream systemIn = System.in;
    static private final PrintStream systemOut = System.out;
    static private ByteArrayInputStream testIn;
    static private ByteArrayOutputStream testOut;

    void provideInput(String inputString){
        testIn = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(testIn);
    }
    void setUpOutput(){
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    static  private void restoreStream(){
        System.setIn(systemIn);
        System.setOut(systemOut);
    }
    @Test
    public void xyz(){
        setUpOutput();
        test1.test1Test();
        assertTrue(testOut.toString().contains("Hello World"));
        restoreStream();
    }

    @Test
    public void loginFail() throws SQLException {
        String in = "random\nrandom\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Username not found"));
        restoreStream();
    }

    @Test
    public void floatSuccess1() throws SQLException {
        String in = "in001\nin001\n1\n\ncs202\n0.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course floated!!!"));
        restoreStream();
    }

    @Test
    public void floatSuccess2() throws SQLException {
        String in = "in002\nin002\n1\n\ncs201\n0.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course floated!!!"));
        restoreStream();
    }

    @Test
    public void floatFailCGInvalid() throws SQLException {
        String in = "in001\nin001\n1\n\ncs101\nas\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Invalid Input!"));
        restoreStream();
    }

    @Test
    public void floatFailDuplicate() throws SQLException {
        String in = "in001\nin001\n1\n\ncs202\n0.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Error in floating course!!!/ Course has already been floated!!!"));
        restoreStream();
    }

    @Test
    public void floatFailInvalid() throws SQLException {
        String in = "in001\nin001\n1\n\ncs101\n0.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Invalid input, try again!!!"));
        restoreStream();
    }

    @Test
    public void dropSuccess() throws SQLException {
        String in = "in002\nin002\n2\ncs201\n\n\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course dropped successfully!!!"));
        restoreStream();
    }

    @Test
    public void dropFailUnauthorised() throws SQLException {
        String in = "in001\nin001\n2\ncs201\n\n\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Unauthorised action!!!"));
        restoreStream();
    }

    @Test
    public void dropFailNoCourse() throws SQLException {
        String in = "in002\nin002\n2\ncs101\n\n\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Unauthorised action!!!"));
        restoreStream();
    }

    @Test
    public void viewEnrolmentSuccess() throws SQLException {
        String in = "in001\nin001\n3\ncs202\n2020\n1\n\n\n\n\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("student id"));
        restoreStream();
    }

    @Test
    public void viewEnrolmentFail() throws SQLException {
        String in = "in001\nin001\n3\ncs132\n2021\n2\n\n\n\n\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Input is invalid"));
        restoreStream();
    }

    @Test
    public void upGradeSuccess1() throws SQLException {
        String in = "in001\nin001\n4\ncs202\n\nC:/Users/Armaan/Desktop/ghi.csv\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Updation completed!!!"));
        restoreStream();
    }

    @Test
    public void upGradeSuccess2() throws SQLException {
        String in = "in002\nin002\n4\ncs201\n\nC:/Users/Armaan/Desktop/abc.csv\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Updation completed!!!"));
        restoreStream();
    }

    @Test
    public void upGradeFailNoCourse() throws SQLException {
        String in = "in001\nin001\n4\ncs132\n\n\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Unauthorised action!!!"));
        restoreStream();
    }

    @Test
    public void upGradeFailInvalidPath() throws SQLException {
        String in = "in001\nin001\n4\ncs202\n\nC:/Users/Armaan/Desktop/klm.csv\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Please enter file path in a valid format"));
        restoreStream();
    }

    @Test
    public void serialAdminReach() throws SQLException {
        String in = "in001\nin001\na\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Invalid Input!"));
        restoreStream();
    }

    @Test
    public void viewStudentInfo() throws SQLException {
        String in = "in001\nin001\n5\n2020chb1039\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("---"));
        restoreStream();
    }

    //------------------sem2

    @Test
    public void floatSuccess10() throws SQLException {
        String in = "in001\nin001\n1\n\nhs102\n0.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course floated!!!"));
        restoreStream();
    }

    @Test
    public void floatSuccess11() throws SQLException {
        String in = "in001\nin001\n1\n\ncs302\n7.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course floated!!!"));
        restoreStream();
    }

    @Test
    public void floatSuccess12() throws SQLException {
        String in = "in002\nin002\n1\n\ncs301\n7.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course floated!!!"));
        restoreStream();
    }

    @Test
    public void floatSuccess13() throws SQLException {
        String in = "in002\nin002\n1\n\nhs103\n0.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course floated!!!"));
        restoreStream();
    }

    @Test
    public void dropSuccess13() throws SQLException {
        String in = "in002\nin002\n2\nhs103\n\n\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course dropped successfully!!!"));
        restoreStream();
    }

    @Test
    public void upGradeSuccess21() throws SQLException {
        String in = "in002\nin002\n4\ncs301\n\nC:/Users/Armaan/Desktop/sem2cs301.csv\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Updation completed!!!"));
        restoreStream();
    }

    @Test
    public void upGradeSuccess22() throws SQLException {
        String in = "in001\nin001\n4\ncs302\n\nC:/Users/Armaan/Desktop/sem2cs302.csv\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Updation completed!!!"));
        restoreStream();
    }

    @Test
    public void upGradeSuccess23() throws SQLException {
        String in = "in001\nin001\n4\nhs102\n\nC:/Users/Armaan/Desktop/sem2hs102.csv\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Updation completed!!!"));
        restoreStream();
    }


    //------------------sem3
    @Test
    public void floatSuccess30() throws SQLException {
        String in = "in001\nin001\n1\n\nhs103\n0.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course floated!!!"));
        restoreStream();
    }

    @Test
    public void upGradeSuccess30() throws SQLException {
        String in = "in001\nin001\n4\nhs103\n\nC:/Users/Armaan/Desktop/sem3hs103.csv\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Updation completed!!!"));
        restoreStream();
    }

    //----------------sem4

    @Test
    public void floatSuccess41() throws SQLException {
        String in = "in001\nin001\n1\n\nhs104\n0.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course floated!!!"));
        restoreStream();
    }

    @Test
    public void floatSuccess42() throws SQLException {
        String in = "in002\nin002\n1\n\nhs105\n0.0\n-1\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        instructor.instructorLogin();
        assertTrue(testOut.toString().contains("Course floated!!!"));
        restoreStream();
    }

}