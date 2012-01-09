package fbchatsoft.client;

import fbchatsoft.helper.PropertiesHelper;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class UserTest  {
    public static String _username;;
    public static String _password;

    public UserTest() {
    }

    @BeforeClass
    public static void initTest() {
        _username = "testusername";
        _password = "testpassword";
    }

    @Test
    public void testUserData() {
        User testuser = new User(_username, _password);
        testuser.saveUserData();

        boolean rememberedUserAvailable = testuser.isRememberedUserAvailable();
        assertTrue("there was no remembered user..", rememberedUserAvailable);

        String returnUsername = testuser.getRememberedUser();
        String returnPassword = testuser.getRememberedPassword();

        assertEquals("The saved username doesn't equal the restored username..", _username, returnUsername);
        assertEquals("The saved password doesn't equal the restored password..", _password, returnPassword);
    }

    @After
    public void cleanupTest() {
        PropertiesHelper.setProperty("username", "");
        PropertiesHelper.setProperty("password", "");
    }
}