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
import fbchatsoft.helper.FileWriter;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatWindow {

    private class MyEventDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_ESCAPE && PropertiesHelper.getProperty("closewithesc").equals("true")) {
                _chatFrame.dispose();
            }
            return false;
        }
    }

    public JFrame _chatFrame;
    private String _partnerName;
    private JTextArea _messagePane;
    private JTextField _inputField;

    public ChatWindow(String aUsername) {
        _partnerName = aUsername;
        initChatWindow();
    }

    private void initChatWindow() {
        _chatFrame = new JFrame();
        _chatFrame.setSize(400, 500);
        _chatFrame.setLocationRelativeTo(null);
        _chatFrame.setLayout(new BorderLayout());
        _chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        _chatFrame.setTitle(Language.translate("chatwindow") + " " + _partnerName);

        displayMessagePane();
        displayTextInputPane();
        displayChatPartnerInformation();
        showChatFrame(true);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyEventDispatcher());
    }

    private void displayMessagePane() {
        _messagePane = new JTextArea();
        _messagePane.setEditable(false);
        _messagePane.setVisible(true);
        float fontsize = Float.parseFloat(PropertiesHelper.getProperty("fontsize"));
        _messagePane.setFont(_messagePane.getFont().deriveFont(fontsize));
        _chatFrame.add(_messagePane, BorderLayout.CENTER);
    }

    private void displayTextInputPane() {
        JPanel textInputPane = new JPanel();
        textInputPane.setLayout(new BorderLayout());
        textInputPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        _inputField = new JTextField();
        _inputField.setColumns(28);
        _inputField.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER)
                    doSendActions(_inputField.getText());
            }

            public void keyTyped(KeyEvent ke) { }
            public void keyReleased(KeyEvent ke) { }
        });
        textInputPane.add(_inputField, BorderLayout.WEST);

        JButton sendButton = new JButton(Language.translate("chat.sendbutton"));
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                doSendActions(_inputField.getText());
            }
        });
        textInputPane.add(sendButton, BorderLayout.EAST);

        _chatFrame.add(textInputPane, BorderLayout.SOUTH);
    }

    private void displayChatPartnerInformation() {
        JPanel partnerInfoPane = new JPanel();
        partnerInfoPane.setLayout(new BorderLayout());
        partnerInfoPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel username = new JLabel(_partnerName);
        username.setFont(username.getFont().deriveFont(Font.BOLD));
        partnerInfoPane.add(username, BorderLayout.WEST);

        partnerInfoPane.setVisible(true);
        _chatFrame.add(partnerInfoPane, BorderLayout.NORTH);
    }

    private void doSendActions(String aMessage) {
        if (!aMessage.equals("")) {
            // Send message: TODO
            String sendingUser = CurrentUserInformation.getCurrentUsername();
            showMessage(aMessage, sendingUser);
            if (Boolean.parseBoolean(PropertiesHelper.getProperty("activateLogging")))
                logMessage(aMessage, sendingUser);
        }
    }

    private void logMessage(String aMessage, String aSendingUser) {
        String path = new File("").getAbsolutePath() + "/logs/";
        String formattedPartnerName = _partnerName.replace(" ", "");
        String logMessage = createMessage(aMessage, aSendingUser);
        FileWriter logWriter = new FileWriter(path + formattedPartnerName + "_log.txt", true, logMessage);
    }

    private void showMessage(String aMessage, String aSendingUser) {
        String currentTextFlow = _messagePane.getText() + "\n";
        String formattedMessage = createMessage(aMessage, aSendingUser);
        _messagePane.setText(currentTextFlow + formattedMessage);
        _inputField.setText("");
        _inputField.requestFocus();
    }

    private String createMessage(String aMessage, String aSendingUser) {
        Locale locale = new Locale(Language.getUserLanguage());
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        Date now = new Date();
        String currentDateTime = dateFormat.format(now);
        String logMessage = "[" +  currentDateTime + "] " + aSendingUser + ": " + aMessage + "\n";
        return logMessage;
    }

    private void showChatFrame(boolean aState) {
        _chatFrame.setVisible(aState);
        if (aState)
            _inputField.requestFocus();
    }
}
