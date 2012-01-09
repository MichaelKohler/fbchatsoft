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

import fbchatsoft.client.Language;
import fbchatsoft.helper.PropertiesHelper;
import fbchatsoft.facebook.CurrentUserInformation;
import fbchatsoft.facebook.FBBuddies;
import fbchatsoft.helper.Debugger;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BuddyListWindow {

    public static JFrame _buddiesListFrame;

    private JLabel _totalUsers;

    public BuddyListWindow() {
        initializeBuddyWindow();
    }

    private void initializeBuddyWindow() {
        _buddiesListFrame = new JFrame(Language.translate("app.name") + " - " + Language.translate("buddylist.windowname"));
        _buddiesListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _buddiesListFrame.setVisible(false);

        int[] heightAndWidth = getPreferredSize();
        _buddiesListFrame.setSize(heightAndWidth[0], heightAndWidth[1]);
        addCloseListener();

        initializeMenu();
        initializeUserAndStatusDisplay();
        _buddiesListFrame.setLocationRelativeTo(null);
        String[] userlist = getUserlist();
        initializeBuddyListView(userlist);
        showBuddyListFrame(true);
    }

    private int[] getPreferredSize() {
        int size[] = { 350, 450 }; // standard value if not saved in properties
        try {
            size[0] = Integer.parseInt(PropertiesHelper.getProperty("buddylist.currentWidth"));
            size[1] = Integer.parseInt(PropertiesHelper.getProperty("buddylist.currentHeight"));
        } catch (Exception ex) {
            Debugger.logMessage(ex);
        }

        return size;
    }

    private void addCloseListener() {
        _buddiesListFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                saveNewWindowSize();
            }
        });
    }

    private void saveNewWindowSize() {
        String currentHeight = _buddiesListFrame.getHeight() + "";
        String currentWidth = _buddiesListFrame.getWidth() + "";
        PropertiesHelper.setProperty("buddylist.currentHeight", currentHeight);
        PropertiesHelper.setProperty("buddylist.currentWidth", currentWidth);
    }

    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu stdMenu = new JMenu(Language.translate("app.name"));
        JMenuItem prefItem = new JMenuItem(Language.translate("menu.preferences"));
        prefItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                BuddyListActions.openPrefWindow();
            }
        });
        stdMenu.add(prefItem);
        JMenuItem logoutItem = new JMenuItem(Language.translate("menu.logout"));
        logoutItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                BuddyListActions.logout(_buddiesListFrame);
            }
        });
        stdMenu.add(logoutItem);
        JMenuItem exitItem = new JMenuItem(Language.translate("menu.exit"));
        exitItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                BuddyListActions.exitApp();
            }
        });
        stdMenu.add(exitItem);

        JMenu conversationMenu = new JMenu(Language.translate("menu.conversation"));
        JMenuItem logItem = new JMenuItem(Language.translate("menu.showlogs"));
        logItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                BuddyListActions.openLogWindow();
            }
        });
        conversationMenu.add(logItem);

        JMenu helpMenu = new JMenu(Language.translate("menu.help"));
        JMenuItem aboutItem = new JMenuItem(Language.translate("menu.about") + " " + Language.translate("app.name"));
        aboutItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                BuddyListActions.openAboutWindow();
            }
        });
        helpMenu.add(aboutItem);

        menuBar.add(stdMenu);
        menuBar.add(conversationMenu);
        menuBar.add(helpMenu);
        menuBar.setVisible(true);

        _buddiesListFrame.add(menuBar, BorderLayout.NORTH);
    }

    private void initializeUserAndStatusDisplay() {
        JPanel userDisplay = new JPanel();
        userDisplay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        userDisplay.setLayout(new GridLayout(1, 3));

        String[] states = {
            Language.translate("buddylist.online"),
            Language.translate("buddylist.busy"),
            Language.translate("buddylist.away"),
            Language.translate("buddylist.offline")
        };
        JComboBox status = new JComboBox(states);
        status.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        status.setVisible(true);
        userDisplay.add(status);

        JLabel username = new JLabel(CurrentUserInformation.getCurrentUsername());
        username.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        username.setVisible(true);
        userDisplay.add(username);

        _totalUsers = new JLabel(Language.translate("buddylist.totalusers"));
        _totalUsers.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        _totalUsers.setVisible(false);
        userDisplay.add(_totalUsers);

        _buddiesListFrame.add(userDisplay, BorderLayout.SOUTH);
    }

    private class CellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            ImageIcon icon = getAppropriateIcon(value.toString());
            label.setIcon(icon);

            return label;
        }

        public ImageIcon getAppropriateIcon(String aUser) {
            HashMap<String, String> defaultList = FBBuddies.getDefaultUserlist();
            HashMap<String, String> detailedList = FBBuddies.getDetailedUserlist();

            String userID = defaultList.get(aUser);
            String presenceType = detailedList.get(userID);
            ImageIcon icon = new ImageIcon();
            if (presenceType.equals(FBBuddies.AWAY))
                icon = new ImageIcon("icons/away.png");
            else if (presenceType.equals(FBBuddies.AVAILABLE))
                icon = new ImageIcon("icons/available.png");
            return icon;
        }
    }

    public void initializeBuddyListView(String[] aInitialRoster) {
        final JList buddyList = new JList(aInitialRoster);
        buddyList.setVisible(true);
        buddyList.setCellRenderer(new CellRenderer());
        buddyList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)
                    BuddyListActions.openChatWindow(buddyList.getModel().getElementAt(buddyList.getSelectedIndex()).toString());
            }
        });
        JScrollPane scrollPane = new JScrollPane(buddyList);
        _buddiesListFrame.add(scrollPane, BorderLayout.CENTER);

        _totalUsers.setText(aInitialRoster.length + " " + _totalUsers.getText());
        _totalUsers.setVisible(true);

        _buddiesListFrame.validate();
    }

    private String[] getUserlist() {
        ArrayList usertemp = new ArrayList();

        HashMap<String, String> defaultUserlist  = FBBuddies.getDefaultUserlist();
        for (String username : defaultUserlist.keySet())
            usertemp.add(username);

        Collections.sort(usertemp);
        String[] userlist = (String[]) usertemp.toArray(new String[0]);
        return userlist;
    }

    private void showBuddyListFrame(boolean aState) {
        _buddiesListFrame.setVisible(aState);
    }

}
