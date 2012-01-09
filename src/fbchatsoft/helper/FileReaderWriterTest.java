package fbchatsoft.helper;

import java.io.File;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FileReaderWriterTest  {
    public static String _path;
    public static boolean _append;
    public static String _message;

    public FileReaderWriterTest() {
    }

    @BeforeClass
    public static void initTest() {
        _path = "junittest.txt";
        _append = true;
        _message = "";
    }

    @Test
    public void testReaderWriterNormal() {
        _append = false;
        _message = "blabla";
        FileWriter writer = new FileWriter(_path, _append, _message);

        FileReader reader = new FileReader(_path);
        String readText = reader.readFile();

        assertEquals("read text was not the written text..", _message + "\n", readText);
    }

    @Test
    public void testReaderWriterAppend() {
        _append = true;
        _message = "blabla";
        FileWriter writer = new FileWriter(_path, _append, _message);
        String secondLine = "secondline";
        FileWriter secondWriter = new FileWriter(_path, _append, secondLine);

        FileReader reader = new FileReader(_path);
        String readText = reader.readFile();

        _message += secondLine + "\n";
        assertEquals("read text was not the written text..", _message, readText);
    }

    @After
    public void cleanup() {
        File file = new File(_path);
        file.delete();
    }

}