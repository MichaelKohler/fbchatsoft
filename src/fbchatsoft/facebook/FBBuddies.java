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

import java.util.Collection;
import java.util.HashMap;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

public class FBBuddies {

    public static Roster _roster;
    public static HashMap<String, String> _userlist;
    public static HashMap<String, String> _detailedUserlist; // userlist with presence info

    public static final String AVAILABLE = "available";
    public static final String AWAY = "away";

    public FBBuddies() {
    }

    static {
        _roster = FBConnection.getConnection().getRoster();
        _userlist = new HashMap<String, String>();
        _detailedUserlist = new HashMap<String, String>();
    }

    public static HashMap getDefaultUserlist() {
        Collection<RosterEntry> rosterEntries = _roster.getEntries();
        for (RosterEntry user : rosterEntries) {
            Presence presence = _roster.getPresence(user.getUser());
            if (presence.isAvailable() || presence.isAway())
                _userlist.put(user.getName(), user.getUser());
        }
        return _userlist;
    }

    public static HashMap getDetailedUserlist() {
        Collection<RosterEntry> rosterEntries = _roster.getEntries();
        for (RosterEntry user : rosterEntries) {
            Presence presence = _roster.getPresence(user.getUser());
            if (presence.isAway())
                _detailedUserlist.put(user.getUser(), AWAY);
            else if (presence.isAvailable())
                _detailedUserlist.put(user.getUser(), AVAILABLE);
        }
        return _detailedUserlist;
    }
    
    public static Roster getRawRoster() {
        return _roster;
    }

}