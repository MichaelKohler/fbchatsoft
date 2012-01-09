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
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AboutWindow {

    private JFrame _aboutFrame;

    public AboutWindow() {
        initializeAboutWindow();
    }

    private void initializeAboutWindow() {
        _aboutFrame = new JFrame(Language.translate("about") + " " + Language.translate("app.name"));
        _aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _aboutFrame.setSize(550, 300);
        _aboutFrame.setLocationRelativeTo(null);
        _aboutFrame.setLayout(new BorderLayout());
        createVersionAndAuthorInformation();
        createLicenseBox();
        showAboutWindow(true);
    }

    private void createVersionAndAuthorInformation() {
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BorderLayout());
        informationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel version = new JLabel(Language.translate("about.version") + " " + Language.translate("version"));
        informationPanel.add(version, BorderLayout.NORTH);

        JLabel author = new JLabel(Language.translate("about.author") + " " + Language.translate("author"));
        informationPanel.add(author, BorderLayout.CENTER);

        _aboutFrame.add(informationPanel, BorderLayout.CENTER);
    }

    private void createLicenseBox() {
        JTextArea licenseInfo = new JTextArea();
        licenseInfo.setText(Language.translate("about.license"));
        licenseInfo.setColumns(30);
        licenseInfo.setRows(12);

        JScrollPane scrollPane = new JScrollPane(licenseInfo);
        _aboutFrame.add(scrollPane, BorderLayout.SOUTH);
    }

    private void showAboutWindow(boolean aState) {
        _aboutFrame.setVisible(aState);
    }
}