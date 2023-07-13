import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class mainTest {
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
        public void studentloginSuccess() throws SQLException {
            String in = "1\n4\n";
            provideInput(in);
            setUpOutput();
            main.main(null);
            assertTrue(testOut.toString().contains("Inside case1"));
            restoreStream();
        }

    @Test
    public void instructorloginSuccess() throws SQLException {
        String in = "2\n4\n";
        provideInput(in);
        setUpOutput();
        main.main(null);
        assertTrue(testOut.toString().contains("Inside case2"));
        restoreStream();
    }

    @Test
    public void adminloginSuccess() throws SQLException {
        String in = "3\n4\n";
        provideInput(in);
        setUpOutput();
        main.main(null);
        assertTrue(testOut.toString().contains("Inside case3"));
        restoreStream();
    }

    @Test
    public void mainloginFail() throws SQLException {
        String in = "a\n4\n";
        provideInput(in);
        setUpOutput();
        main.main(null);
        assertTrue(testOut.toString().contains("Invalid Input!"));
        restoreStream();
    }
}