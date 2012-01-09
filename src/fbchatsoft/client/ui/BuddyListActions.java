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

package fbchatsoft.client.ui;

import fbchatsoft.helper.PropertiesHelper;
import fbchatsoft.facebook.FBConnection;
import javax.swing.JFrame;

public class BuddyListActions {

    public BuddyListActions() {
     }

    public static void openPrefWindow() {
        PrefWindow prefWindow = new PrefWindow();
    }

    public static void logout(JFrame aBuddyList) {
        if (FBConnection.startFacebookLogoutProcess()) {
            aBuddyList.dispose();
            clearUserCredentials();
            LoginWindow loginWindow = new LoginWindow();
        }
    }

    public static void exitApp() {
        System.exit(0);
    }

    public static void openLogWindow() {
        LogFileWindow logWindow = new LogFileWindow();
    }

    public static void openAboutWindow() {
        AboutWindow aboutWindow = new AboutWindow();
    }

    public static void openChatWindow(String aUsername) {
        ChatWindow chatWindow = new ChatWindow(aUsername);
    }

    private static void clearUserCredentials() {
        PropertiesHelper.setProperty("username", "");
        PropertiesHelper.setProperty("password", "");
    }
}
