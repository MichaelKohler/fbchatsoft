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

package fbchatsoft.helper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileReader {

    private String _path;

    public FileReader(String aPath) {
        _path = aPath;
    }

    public String readFile() {
        String readText = "";

        try {
            FileInputStream fstream = new FileInputStream(_path);
            DataInputStream dataStream = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(dataStream));
            String strLine = "";
            while ((strLine = br.readLine()) != null)
                readText += strLine + "\n";
        } catch (Exception ex) {
        }

        return readText;
    }

}