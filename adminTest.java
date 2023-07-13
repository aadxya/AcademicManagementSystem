import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
class adminTest {
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
    public void loginSuccess() throws SQLException {
        String in = "w\nadmin\nadmin\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminLogin();
        assertTrue(testOut.toString().contains("Correct Password"));
        restoreStream();
    }

    @Test
    public void loginFailed() throws SQLException {
        String in = "\nkodmin\nkodmin\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminLogin();
        assertTrue(testOut.toString().contains("Username not found"));
        restoreStream();
    }

    @Test public void adminMenuFail() throws SQLException{
        String in = "w\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Invalid Input!"));

    }

    @Test
    public void adminMenuBatch() throws SQLException{
        String in = "1\n\n2020\n5\n5\n15\n15\nC:/Users/Armaan/Desktop/bcd.csv\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("records filled"));
    }

    @Test
    public void adminMenuBatchFail1() throws SQLException{
        String in = "1\n\n2021\n5\n5\n15\n15\nC:/Users/Armaan/Desktop/bcd.csv\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Already Added"));
    }

    //Adding Students into DB
    @Test
    public void adminMenuAddStudent1() throws SQLException{
        String in = "2\narm\n2020chb1039\n2020chb1039\n3456\ncse\n2020\n\n\n\n\n\n\n\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Table st2020chb1039 created successfully."));
    }

    @Test
    public void adminMenuAddStudent2() throws SQLException{
        String in = "2\nutm\n2020chb1057\n2020chb1057\n3456\nche\n2020\n\n\n\n\n\n\n\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Table st2020chb1057 created successfully."));
    }

    @Test
    public void adminMenuAddStudent3() throws SQLException{
        String in = "2\nmp\n2020chb1041\n2020chb1041\n3456\nche\n2020\n\n\n\n\n\n\n\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Table st2020chb1041 created successfully."));
    }

    @Test
    public void adminMenuAddStudent3Fail() throws SQLException{
        String in = "2\nmp\n2020chb1041\n2020chb1041\n3456\nche\n2020\n\n\n\n\n\n\n\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Record already present"));
    }

    @Test
    public void adminMenuAddInstructor1() throws SQLException{
        String in = "3\napurv\nin001\nin001\n123455\ncse\nyes\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Instructor Added"));
    }


    @Test
    public void adminMenuAddInstructor2() throws SQLException{
        String in = "3\nbalw\nin002\nin002\n123455\ncse\nyes\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Instructor Added"));
    }

    @Test
    public void adminMenuAddInstructor2Fail() throws SQLException{
        String in = "3\napurv\nin001\nin001\n123455\ncse\nyes\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Record already present"));
    }

//    Add to Course Catalog tc

    @Test
    public void adminMenuaddToCC1() throws SQLException{
        String in = "7\ncs201\nDSA\n3\n6-4-2\n\n\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course added!!!"));
    }

    @Test
    public void adminMenuaddToCC2() throws SQLException{
        String in = "7\ncs202\nPPaP\n3\n6-4-2\n\n\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course added!!!"));
    }

    @Test
    public void adminMenuaddToCC3() throws SQLException{
        String in = "7\ncs301\nDB\n3\n6-4-2\ncs202\n\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course added!!!"));
    }

    @Test
    public void adminMenuaddToCC4() throws SQLException{
        String in = "7\ncs302\nAlgo\n3\n6-4-2\ncs201\n\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course added!!!"));
    }

    @Test
    public void adminMenuaddToCC5() throws SQLException{
        String in = "7\nhs102\nhumanity2\n3\n6-4-2\n\n\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course added!!!"));
    }

    @Test
    public void adminMenuaddToCC6() throws SQLException{
        String in = "7\nhs103\nhumanity3\n3\n6-4-2\n\n\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course added!!!"));
    }

    @Test
    public void adminMenuaddToCC7() throws SQLException{
        String in = "7\nhs104\nhumanity4\n3\n6-4-2\n\n\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course added!!!"));
    }

    @Test
    public void adminMenuaddToCC8() throws SQLException{
        String in = "7\nhs105\nhumanity5\n3\n6-4-2\n\n\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course added!!!"));
    }

    @Test
    public void adminMenuaddToCC4Fail() throws SQLException{
        String in = "7\ncs302\nAlgo\n3\n6-4-2\ncs201\n\n1\n2\n3\n4\n5\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Course not added!!!/Course already present!!!"));
    }
//------

    @Test
    public void adminMenuviewStudentInfo() throws SQLException{
        String in = "5\n2020csb1039\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("---"));
    }

    @Test
    public void adminMenugenerateTranscript() throws SQLException{
        String in = "6\n2020csb1039\nC:/Users/Armaan/Desktop/efg.txt\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully wrote to the file."));
    }

    @Test
    public void adminMenugenerateTranscript1() throws SQLException{
        String in = "6\n2020csb1039\nC:/Users/Armaan/Desktop/efg.txt\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Successfully wrote to the file."));
    }

    @Test
    public void adminMenugenerateTranscript2() throws SQLException{
        String in = "6\n2020csb1039\nC:/Users/Gendu/Desktop/efg.txt\n-1\n";
        provideInput(in);
        setUpOutput();
//        test1.test1Test();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("An error occurred."));
    }

    @Test
    public void endSemester1() throws SQLException{
        String in = "4\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Ending semester now"));
    }

    @Test
    public void endSemester2() throws SQLException{
        String in = "4\n-1\n-1\n";
        provideInput(in);
        setUpOutput();
        admin.adminMenu();
        restoreStream();
        System.out.println(testOut.toString());
        assertTrue(testOut.toString().contains("Ending semester now"));
    }
}
