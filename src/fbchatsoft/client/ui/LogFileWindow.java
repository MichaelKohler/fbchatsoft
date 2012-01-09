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

import fbchatsoft.helper.CSVExporter;
import fbchatsoft.client.Language;
import fbchatsoft.helper.PropertiesHelper;
import fbchatsoft.helper.Debugger;
import fbchatsoft.helper.FileReader;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class LogFileWindow {

    public JFrame _logFrame;
    private JSplitPane _splitPane;
    private JPanel _userPanel;
    private JPanel _contentPanel;
    private JTextArea _logArea;
    private JList _list;

    private JScrollPane _scrollPaneLogs;
    private JScrollPane _scrollPaneUsers;

    private HashMap<String, File> _fileMap;

    public LogFileWindow() {
        initializeLogWindow();
    }

    private void initializeLogWindow() {
        _logFrame = new JFrame(Language.translate("app.name") + " - " + Language.translate("logs.windowname"));
        _logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        _logFrame.setVisible(false);
        _logFrame.setSize(500, 400);
        _logFrame.setLocationRelativeTo(null);

        initializeMenu();
        initializeContent();
        initializeList();
        initializePane();
        showLogFrame(true);
    }

    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu stdMenu = new JMenu(Language.translate("menu.file"));

        JMenuItem exportItem = new JMenuItem(Language.translate("logs.export"));
        exportItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                exportTextfile();
            }
        });
        stdMenu.add(exportItem);

        JMenuItem csvItem = new JMenuItem(Language.translate("logs.exportCSV"));
        csvItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                exportCSVFile();
            }
        });
        stdMenu.add(csvItem);

        JMenuItem closeItem = new JMenuItem(Language.translate("menu.close"));
        closeItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                _logFrame.dispose();
            }
        });
        stdMenu.add(closeItem);

        menuBar.add(stdMenu);
        menuBar.setVisible(true);

        _logFrame.add(menuBar, BorderLayout.NORTH);
    }

    private void initializeList() {
        _userPanel = new JPanel();
        _userPanel.setLayout(new BorderLayout());

        File dir = new File("logs");
        File[] files = dir.listFiles();
        if (files == null) {
            _logArea.setText(Language.translate("logs.nofiles"));
        }
        else {
            _fileMap = new HashMap<String, File>();
            for (File file : files) {
                String filename = file.getName();
                String name = filename.substring(0, filename.lastIndexOf("_log"));
                _fileMap.put(name, file);
            }

            ArrayList logFilesList = new ArrayList();
            for (String key : _fileMap.keySet()) {
                logFilesList.add(key);
            }

            String[] logfiles = (String[]) logFilesList.toArray(new String[0]);
            _list = new JList(logfiles);
            _list.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    readLogFile(_list.getModel().getElementAt(_list.getSelectedIndex()).toString());
                }
            });
            _userPanel.add(_list, BorderLayout.CENTER);
        }

        _scrollPaneUsers = new JScrollPane(_userPanel);
    }

    private void initializeContent() {
        _contentPanel  = new JPanel();
        _contentPanel.setLayout(new BorderLayout());

        _logArea = new JTextArea();
        _logArea.setEditable(false);
        _contentPanel.add(_logArea, BorderLayout.CENTER);

        _scrollPaneLogs = new JScrollPane(_contentPanel);
    }

    private void initializePane() {
        _splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _scrollPaneUsers, _scrollPaneLogs);
        _splitPane.setContinuousLayout(true);
        _splitPane.setVisible(true);
        _splitPane.setDividerLocation(120);

        _logFrame.add(_splitPane);
    }

    private void readLogFile(String aUser) {
        FileReader reader = new FileReader(_fileMap.get(aUser).getAbsolutePath());
        String log = reader.readFile();
        _logArea.setText(log);
    }

    private void showLogFrame(boolean aState) {
        _logFrame.setVisible(aState);
    }

    public void exportTextfile() {
        String selectedName = _list.getModel().getElementAt(_list.getSelectedIndex()).toString();
        String outputPath = PropertiesHelper.getProperty("txtexportpath") + "/" + selectedName + ".txt";
        if (_fileMap.containsKey(selectedName)) {
            File logFileToCopy = _fileMap.get(selectedName);
            File outputFile = new File(outputPath);
            copyTextFile(logFileToCopy, outputFile);
        }
    }

    private void copyTextFile(File aInput, File aOutput) {
        try {
        FileChannel inChannel = new FileInputStream(aInput).getChannel();
        FileChannel outChannel = new FileOutputStream(aOutput).getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception ex) {
            Debugger.logMessage(ex);
            // TODO Alert Window
        }
    }

    public void exportCSVFile() {
        String selectedName = _list.getModel().getElementAt(_list.getSelectedIndex()).toString();
        String outputPath = PropertiesHelper.getProperty("csvexportpath") + "/" + selectedName + ".csv";
        String log = _logArea.getText();
        CSVExporter exporter = new CSVExporter(outputPath);
        exporter.writeLogFile(log);
    }

}