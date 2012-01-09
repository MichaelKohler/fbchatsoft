package fbchatsoft.helper;

import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PropertiesHelperTest  {
    public static String _testString;
    public static String _key;

    public PropertiesHelperTest() {
    }

    @BeforeClass
    public static void initTest() {
        _testString = "TestString";
        _key = "testKey";
    }

    @Test
    public void readAndWriteProperties() {
        PropertiesHelper.setProperty(_key, _testString);
        String readString = PropertiesHelper.getProperty(_key);
        assertEquals("Failure: original string was not the restored string...", _testString, readString);
    }

    @Test
    public void removeProperties() {
        PropertiesHelper.removeProperty(_key);
        boolean keyExists = PropertiesHelper.propertyExists(_key);
        assertNotNull("property was null", keyExists);
        assertFalse("property was still there", keyExists);
    }
}
