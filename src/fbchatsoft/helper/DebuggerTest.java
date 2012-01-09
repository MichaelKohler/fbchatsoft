package fbchatsoft.helper;

import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DebuggerTest  {
    public static Object _nullObject;;

    public DebuggerTest() {
    }

    @BeforeClass
    public static void initTest() {
        _nullObject = null;
    }

    @Test (expected=NullPointerException.class)
    public void testDebugger() {
        assertNull("object was not null..", _nullObject);
        try {
            _nullObject.toString();
        }
        catch (Exception ex) {
             Debugger.logMessage(ex);

            String message = ex.getMessage();
            assertTrue("it's not a nullpointer exception", message.contains("null"));
        }        
    }

    @Test
    public void testErrorFile()  {
        // read from error file
        String loggedMessage = "";

        FileReader reader = new FileReader("error.log");
        loggedMessage = reader.readFile();

        boolean containsExceptionInfo = loggedMessage.contains("null");
        assertTrue("the logged message did not contain \"null\"..", containsExceptionInfo);
    }

    
}