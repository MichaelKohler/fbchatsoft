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

import fbchatsoft.client.User;
import fbchatsoft.client.Language;
import fbchatsoft.facebook.FBConnection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow {

    // HACK!
    public int _entercount = 0;
    public int _esccount = 0;

    private class MyEventDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_ENTER && _entercount == 0) {
                doLoginActions();
                _entercount++;
            }
            else if (code == KeyEvent.VK_ESCAPE && _esccount == 0) {
                _loginFrame.dispose();
                _esccount++;
            }
            return false;
        }
    }

    private JFrame _loginFrame;
    private JTextField _usernameField;
    private JPasswordField _passwordField;
    private JLabel _credentialsWrongLabel;
    private JLabel _serverUnavailableLabel;
    private JCheckBox _keepLoggedInCheckbox;
    private JButton _loginButton;

    public LoginWindow() {
        User currentUser = new User();
        if (currentUser.isRememberedUserAvailable()) {
          if (FBConnection.startFacebookLoginProcess(User.getRememberedUser(), User.getRememberedPassword())) {
              BuddyListWindow buddyList = new BuddyListWindow();
          }
        }
        else {
             initializeWindow();
        }
    }

    private void initializeWindow() {
        _loginFrame = new JFrame(Language.translate("app.name") + " - " + Language.translate("login"));
        _loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _loginFrame.setSize(400, 200);
        _loginFrame.setLocationRelativeTo(null);
        createLoginForm();
        createLoginButton();
        showLoginWindow(true);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyEventDispatcher());
    }

    private void createLoginForm() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4,4));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel(Language.translate("login.username.label"));
        loginPanel.add(usernameLabel);
        _usernameField = new JTextField();
        loginPanel.add(_usernameField);
        JLabel passwordLabel = new JLabel(Language.translate("login.password.label"));
        loginPanel.add(passwordLabel);
        _passwordField = new JPasswordField();
        loginPanel.add(_passwordField);

        _credentialsWrongLabel = new JLabel(Language.translate("login.credentialsWrong"));
        _credentialsWrongLabel.setVisible(false);
        _credentialsWrongLabel.setForeground(Color.RED);
        loginPanel.add(_credentialsWrongLabel);
        _serverUnavailableLabel = new JLabel(Language.translate("login.serverUnavailable"));
        _serverUnavailableLabel.setVisible(false);
        _serverUnavailableLabel.setForeground(Color.RED);
        loginPanel.add(_serverUnavailableLabel);

        JLabel keepLoggedInLabel = new JLabel(Language.translate("login.keepLoggedIn"));
        loginPanel.add(keepLoggedInLabel);
        _keepLoggedInCheckbox = new JCheckBox();
        loginPanel.add(_keepLoggedInCheckbox);

        _loginFrame.add(loginPanel, BorderLayout.NORTH);
    }

    private void createLoginButton() {
        JPanel buttonPanel = new JPanel();

        _loginButton = new JButton(Language.translate("login"));
        _loginButton.setPreferredSize(new Dimension(100, 30));
        _loginButton.setVisible(true);
        _loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
               doLoginActions();
			}
		});

        buttonPanel.add(_loginButton);
        _loginFrame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void doLoginActions() {
        if (!loginToFacebook()) {
            showCredentialsWrong();
            _passwordField.setText("");
            _entercount = 0;
            _esccount = 0;
        }
        else {
            BuddyListWindow buddies = new BuddyListWindow();
            if (_keepLoggedInCheckbox.isSelected()) {
                saveCredentials();
            }
            _loginFrame.dispose();
        }
    }

    private boolean loginToFacebook() {
        String username = _usernameField.getText();
        String password = String.valueOf(_passwordField.getPassword());
        boolean loginSuccess = FBConnection.startFacebookLoginProcess(username, password);
        return loginSuccess;
    }

    private void showCredentialsWrong() {
        _credentialsWrongLabel.setVisible(true);
        _serverUnavailableLabel.setVisible(true);
    }

    private void saveCredentials() {
        String username = _usernameField.getText();
        String password = String.valueOf(_passwordField.getPassword());
        User currentUser = new User(username, password);
        currentUser.saveUserData();
    }

    private void showLoginWindow(boolean aState) {
        _loginFrame.setVisible(aState);
    }
}