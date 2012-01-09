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

package fbchatsoft.facebook;

import fbchatsoft.helper.Debugger;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class FBConnection {

    public static XMPPConnection _connection;
    private static ConnectionConfiguration _config;;

    static {
        _config = new ConnectionConfiguration("chat.facebook.com", 5222);
        _config.setSASLAuthenticationEnabled(true);
        _connection = new XMPPConnection(_config);
        try {
            _connection.connect();
        } catch (XMPPException ex) {
            Debugger.logMessage(ex);
        }
    }

    public static boolean startFacebookLoginProcess(String aUsername, String aPassword) {
        if (_connection == null || !_connection.isConnected())
            return false;

        try {
             _connection.login(aUsername, aPassword);
        } catch (XMPPException ex) {
             Debugger.logMessage(ex);
             return false;
       }

        return true;
    }

    public static boolean startFacebookLogoutProcess() {
        if (_connection != null && _connection.isConnected()) {
            _connection.disconnect();
            return true;
        }
        return false;
    }

    public static XMPPConnection getConnection() {
        return _connection;
    }
}
