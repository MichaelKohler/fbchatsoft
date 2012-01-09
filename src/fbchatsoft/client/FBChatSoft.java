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
import fbchatsoft.client.ui.LoginWindow;
import fbchatsoft.helper.Debugger;
import javax.swing.UIManager;

public class FBChatSoft {

    public static void main(String args[])  {
        setGUIAppearance();
        createSettingsFile();
        LoginWindow loginWindow = new LoginWindow();
    }

    private static void setGUIAppearance() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Debugger.logMessage(ex);
        }
    }

    private static void createSettingsFile() {
        String[] mandatoryPrefs = { "username", "password" };
        for (int i = 0; i < mandatoryPrefs.length; i++) {
            if (PropertiesHelper.getProperty(mandatoryPrefs[i]) == null) {
                PropertiesHelper.setProperty(mandatoryPrefs[i], "");
            }
        }
    }

}