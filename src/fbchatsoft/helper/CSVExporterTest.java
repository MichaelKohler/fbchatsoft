package fbchatsoft.helper;

import java.io.File;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class CSVExporterTest  {

    public static String _message;
    public static String _path;

    public CSVExporterTest() {
    }

    @BeforeClass
    public static void initTest() {
        _message = "[Date] username: textfoobar\n[Date2] username2: text2";
        _path = "test.csv";
    }

    @Test
    public void testFileWriteCapability()  {
        CSVExporter exp = new CSVExporter(_path);
        exp.writeLogFile(_message);
        assertNotNull("exporter could not been initialized..", exp);
    }

    @Test
    public void testFileContentFormat() {
        FileReader reader = new FileReader(_path);
        String readCSV = reader.readFile();
        String expected = "\"Date\";\"username\"; \"textfoobar\"\n\"Date2\";\"username2\"; \"text2\"\n";
        assertEquals("the read string was not as expected..", expected, readCSV);
    }

    @After
    public void cleanup() {
        File testCSV = new File(_path);
        testCSV.delete();
    }


}