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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class PrefWindow {

    public JFrame _prefFrame;
    public JTabbedPane _tabbedPane;
    private JCheckBox _useLastStatus;
    private JCheckBox _muteSounds;
    private JCheckBox _closeWithESC;
    private JComboBox _fontsize;
    private JCheckBox _activateLogging;
    private JTextField _txtExportPath;
    private JTextField _csvExportPath;

    public PrefWindow() {
        initializePrefWindow();
    }

    private void initializePrefWindow() {
        _prefFrame = new JFrame(Language.translate("app.name") + " - " + Language.translate("preferences"));
        _prefFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _prefFrame.setVisible(false);
        _prefFrame.setSize(500, 200);
        _prefFrame.setLocationRelativeTo(null);
        _prefFrame.setLayout(new BorderLayout());
        initializeButtons();
        initializeTabbedPane();
        showPrefWindow(true);
    }

    private void initializeTabbedPane() {
        _tabbedPane = new JTabbedPane();
        initializeGeneralTab();
        initializeConversationTab();
        initializeLoggingTab();
        restorePrefStates();

        _tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 50, 0));
        _tabbedPane.setVisible(true);
        _prefFrame.add(_tabbedPane, BorderLayout.NORTH);
    }

    private void initializeGeneralTab() {
        JPanel generalPanel = new JPanel();
        generalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        generalPanel.setLayout(new GridLayout(2, 2));

        JLabel useLastStatusLabel = new JLabel(Language.translate("prefs.uselaststatus"));
        generalPanel.add(useLastStatusLabel);
        _useLastStatus = new JCheckBox();
        generalPanel.add(_useLastStatus);

        JLabel muteSoundsLabel = new JLabel(Language.translate("prefs.mutesounds"));
        generalPanel.add(muteSoundsLabel);
        _muteSounds = new JCheckBox();
        generalPanel.add(_muteSounds);

        generalPanel.setVisible(true);
        _tabbedPane.addTab(Language.translate("prefs.general"), generalPanel);
    }

    private void initializeConversationTab() {
        JPanel conversationPanel = new JPanel();
        conversationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        conversationPanel.setLayout(new GridLayout(3, 3));

        JLabel fontSizeLabel = new JLabel(Language.translate("prefs.fontsize"));
        conversationPanel.add(fontSizeLabel);
        String[] possibleFontsizes = { "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"};
        _fontsize = new JComboBox(possibleFontsizes);
        conversationPanel.add(_fontsize);

        JLabel closeWithESClabel = new JLabel(Language.translate("prefs.closewithesc"));
        conversationPanel.add(closeWithESClabel);
        _closeWithESC = new JCheckBox();
        conversationPanel.add(_closeWithESC);

        conversationPanel.setVisible(true);
        _tabbedPane.addTab(Language.translate("prefs.conversations"), conversationPanel);
    }

    private void initializeLoggingTab() {
        JPanel loggingPanel = new JPanel();
        loggingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loggingPanel.setLayout(new GridLayout(3, 3));

        JLabel activateLabel = new JLabel(Language.translate("prefs.activateLogging"));
        loggingPanel.add(activateLabel);
        _activateLogging = new JCheckBox();
        loggingPanel.add(_activateLogging);

        _activateLogging.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!_activateLogging.isSelected()) {
                    _txtExportPath.setEnabled(true);
                    _csvExportPath.setEnabled(true);
                }
                else {
                    _txtExportPath.setEnabled(false);
                    _csvExportPath.setEnabled(false);
                }
            }
        });

        JLabel txtExport = new JLabel(Language.translate("prefs.txtexport"));
        loggingPanel.add(txtExport);
        _txtExportPath = new JTextField();
        loggingPanel.add(_txtExportPath);

        JLabel csvExport = new JLabel(Language.translate("prefs.csvexport"));
        loggingPanel.add(csvExport);
        _csvExportPath = new JTextField();
        loggingPanel.add(_csvExportPath);

        loggingPanel.setVisible(true);
        _tabbedPane.addTab(Language.translate("prefs.logging"), loggingPanel);
    }

    private void restorePrefStates() {
        boolean uselaststatusState = Boolean.parseBoolean(PropertiesHelper.getProperty("uselaststatus"));
        _useLastStatus.setSelected(uselaststatusState);

        boolean mutesoundsState = Boolean.parseBoolean(PropertiesHelper.getProperty("mutesounds"));
        _muteSounds.setSelected(mutesoundsState);

        boolean closewithescState = Boolean.parseBoolean(PropertiesHelper.getProperty("closewithesc"));
        _closeWithESC.setSelected(closewithescState);

        String fontsize = PropertiesHelper.getProperty("fontsize");
        _fontsize.setSelectedItem(fontsize);

        boolean activateLogging = Boolean.parseBoolean(PropertiesHelper.getProperty("activateLogging"));
        _activateLogging.setSelected(activateLogging);
        if (!activateLogging) {
            _txtExportPath.setEnabled(false);
            _csvExportPath.setEnabled(false);
        }

        String txtExportPath = PropertiesHelper.getProperty("txtexportpath");
        _txtExportPath.setText(txtExportPath);

        String csvExportPath = PropertiesHelper.getProperty("csvexportpath");
        _csvExportPath.setText(csvExportPath);

    }

    private void initializeButtons() {
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton(Language.translate("ok"));
        okButton.setPreferredSize(new Dimension(100, 30));
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                savePropertiesToFile();
                closePrefWindow();
            }
        });
        buttonPanel.add(okButton);

        JButton cancelButton = new JButton(Language.translate("cancel"));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                closePrefWindow();
            }
        });
        buttonPanel.add(cancelButton);
        _prefFrame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void showPrefWindow(boolean aState) {
        _prefFrame.setVisible(aState);
    }

    private void savePropertiesToFile() {
        String useLastStatus = String.valueOf(_useLastStatus.isSelected());
        PropertiesHelper.setProperty("uselaststatus", useLastStatus);

        String muteSounds = String.valueOf(_muteSounds.isSelected());
        PropertiesHelper.setProperty("mutesounds", muteSounds);

        String closeWithESC = String.valueOf(_closeWithESC.isSelected());
        PropertiesHelper.setProperty("closewithesc", closeWithESC);

        String fontsize = _fontsize.getSelectedItem().toString();
        PropertiesHelper.setProperty("fontsize", fontsize);

        String activateLogging = String.valueOf(_activateLogging.isSelected());
        PropertiesHelper.setProperty("activateLogging", activateLogging);

        String exportPath = _txtExportPath.getText();
        String txtExportPath = "";
        if (exportPath.endsWith("/"))
            txtExportPath = exportPath.substring(0, exportPath.length() - 1);
        else
            txtExportPath = exportPath;
        PropertiesHelper.setProperty("txtexportpath", txtExportPath);

        exportPath = _csvExportPath.getText();
        String csvExportPath = "";
        if (exportPath.endsWith("/"))
            csvExportPath = exportPath.substring(0, exportPath.length() -1);
        else
            csvExportPath = _csvExportPath.getText();
        PropertiesHelper.setProperty("csvexportpath", csvExportPath);
    }

    private void closePrefWindow() {
        _prefFrame.dispose();
    }
}
