package lang.tools;

import junit.framework.TestCase;

import java.io.*;

/**
 * @author apleshkov
 */
public abstract class TDirectoryTestCase extends TestCase {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    protected void startTest(String root) throws IOException, InvalidTestDirectoryException {
        testDirectory(new File("testData/" + root));
    }

    private void testDirectory(File directory) throws InvalidTestDirectoryException, IOException {
        if (!directory.isDirectory()) {
            throw new InvalidTestDirectoryException(directory);
        }

        System.out.println("[" + directory.getPath() + "]");
        System.out.println();

        final File[] children = directory.listFiles(new FilenameFilter() {
            public boolean accept(File file, String s) {
                return s.indexOf('_') != 0;
            }
        });

        if (children != null && children.length > 0) {
            for (File child : children) {
                if (child.isDirectory()) {
                    testDirectory(child);
                } else if (child.isFile() && child.canRead()) {
                    testFile(child);
                }
            }
            System.out.println();
        }
    }

    private void testFile(File file) throws IOException {
        final StringBuffer expected = new StringBuffer();
        final StringBuffer input = new StringBuffer();

        final BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        Boolean afterSeparator = false;

        while ((line = reader.readLine()) != null) {
            if ("---[EXPECTED]---".equals(line)) {
                afterSeparator = true;
            } else {
                if (afterSeparator) {
                    expected.append(line);
                    expected.append(LINE_SEPARATOR);
                } else {
                    if (input.length() > 0) {
                        input.append(LINE_SEPARATOR);
                    }
                    input.append(line);
                }
            }
        }

        final String fileName = file.getName();

        System.out.println(fileName);

        final String actual = transformInput(input.toString());

        assertEquals("Test failed: " + fileName, expected.toString(), actual);
    }

    protected abstract String transformInput(final String text);

    public static class InvalidTestDirectoryException extends Exception {

        public InvalidTestDirectoryException(File dir) {
            super("Invalid TestSuite directory: " + dir.getAbsolutePath());
        }
    }

}
