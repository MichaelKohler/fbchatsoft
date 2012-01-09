/* ============== FBChatSoft ==============
 * Copyright 2011 by Michael Kohler
 *
 * Initial developer: Michael Kohler <michaelkohler@linux.com>
 *
 * Contributors:
 *
 * ============== MIT License ==============
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package fbchatsoft.client;

import fbchatsoft.helper.PropertiesHelper;

public class User {

    public String _username;
    public String _password;

    public User() {

    }

    public User(String aUsername, String aPassword) {
        _username = aUsername;
        _password = aPassword;
    }

    public void saveUserData() {
       PropertiesHelper.setProperty("username", _username);
       PropertiesHelper.setProperty("password", _password);
    }

    public boolean isRememberedUserAvailable() {
        String savedUsername = PropertiesHelper.getProperty("username");
        String savedPassword = PropertiesHelper.getProperty("password");
        if (!savedUsername.equals("") && !savedPassword.equals("")) {
            return true;
        }
        return false;
    }

    public static String getRememberedUser() {
        return PropertiesHelper.getProperty("username");
    }

    public static String getRememberedPassword() {
        return PropertiesHelper.getProperty("password");
    }

}
